# Artifact Sync Engine — Rate Limiter

A production-grade simulation of the rate limiting stack used in an ALM connector SDK.
Demonstrates proactive throttling, reactive 429 handling, and exponential backoff retry
across multiple concurrent integrations.

---

## Problem Statement

An artifact sync engine synchronizes test cases with **Tosca Cloud**, an external ALM tool
that enforces a hard rate limit per integration (e.g. 200 req/min). When exceeded, the API
returns **HTTP 429 with a `Retry-After` header**. Multiple integrations (tenants) run
simultaneously and each must have its own isolated rate limiter. User cancellation must
work even while a thread is blocked waiting for a permit.

---

## HLD — Three-Layer Rate Limiting Stack

```
Operation triggered (retrieve / update artifact)
         │
         ▼
┌────────────────────────────────────────────────────┐
│  LAYER 1 — DefaultRequestThrottle (per integration)│  PROACTIVE
│                                                    │
│  acquirePermit()                                   │
│    waitMs = NonBurstingTokenBucket.reserveToken()  │
│    ├── 0ms   → token available, proceed            │
│    └── >0ms  → executor.submit(sleep(waitMs))      │
│               future.get()  ← interruptible wait   │
│                                                    │
│  NonBurstingTokenBucket:                           │
│    nextAllowedTimeMs resets to NOW on idle         │
│    → no token accumulation, no burst possible      │
│    → uses System.nanoTime() (monotonic, no skew)   │
│    → throws if queue wait > MAX_QUEUE_WAIT_MS      │
└─────────────────────┬──────────────────────────────┘
                      │ permit granted
                      ▼
┌────────────────────────────────────────────────────┐
│  LAYER 2 — MockRemoteApi + ApiResponse             │  HTTP CALL
│                                                    │
│  Enforces: max 3 req / 3-second window per url     │
│  200 OK  → artifact body returned                  │
│  429     → Retry-After: 2                          │
│             → ApiLimitExceededException(2000ms)    │
└─────────────────────┬──────────────────────────────┘
                      │ exception thrown
                      ▼
┌────────────────────────────────────────────────────┐
│  LAYER 3 — RetryRule (cubic backoff)               │  REACTIVE
│                                                    │
│  isRetryable?                                      │
│    ApiLimitExceededException  → YES                │
│    ConnectorInterruptedException → NO (cancel)     │
│                                                    │
│  computeDelay(attempt, exception):                 │
│    server Retry-After present?                     │
│      → use it, capped at MAX_SERVER_RETRY_AFTER_MS │
│    no header?                                      │
│      → attempt³ × 100ms, capped at 120,000ms      │
│        attempt 1:    100ms                         │
│        attempt 2:    800ms                         │
│        attempt 3:  2,700ms                         │
│        attempt 5: 12,500ms                         │
│                                                    │
│  attempt > 12 → give up, surface to caller        │
└────────────────────────────────────────────────────┘
```

---

## Component Diagram

```
┌─────────────────────────────────────────────────────────┐
│                        Main                             │
│         (creates services, triggers sync)               │
└────────────────────────┬────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│                 ArtifactSyncService                     │
│         syncArtifacts(List<artifactIds>)                │
│                                                         │
│   for each artifact:                                    │
│       RetryRule.execute(() -> fetchArtifact(id))        │
└────────────┬──────────────────────────┬─────────────────┘
             │                          │
             ▼                          ▼
┌────────────────────────┐   ┌──────────────────────────┐
│  DefaultRequestThrottle│   │        RetryRule         │
│      (LAYER 1)         │   │        (LAYER 2)         │
│                        │   │                          │
│  acquirePermit()       │   │  isRetryable(exception)  │
│  → submit to executor  │   │  computeDelay(attempt)   │
│  → future.get()        │   │  → Retry-After first     │
│    (interruptible)     │   │  → cubic backoff fallback│
│                        │   │  → max 12 attempts       │
└────────────┬───────────┘   └──────────────────────────┘
             │
             ▼
┌─────────────────────────┐
│  NonBurstingTokenBucket │
│                         │
│  reserve()              │
│  nextAllowedMs = now    │  ← non-bursting key line
│         + intervalMs    │
└─────────────┬───────────┘
              │ permit granted
              ▼
┌─────────────────────────┐
│      MockRemoteApi      │
│                         │
│  per-integration window │
│  MAX 3 req / 3s         │
│  → 200 OK               │
│  → 429 + Retry-After    │
└─────────────────────────┘
```

---

## Project Structure

```
ArtifactSyncClient/
│
├── config/
│   └── ThrottlingSettings.java          Rate config value object (req/min → permits/sec)
│
├── exception/
│   ├── ConnectorException.java          Base exception
│   ├── ConnectorInterruptedException    Cancellation — never retried, restores interrupt flag
│   └── ApiLimitExceededException        Carries Retry-After delay in ms from the 429 response
│
├── http/
│   └── ApiResponse.java                 Simulated HTTP response with status and header access
│
├── throttle/
│   ├── NonBurstingTokenBucket.java      Core bucket — strict per-token timing, no burst on idle
│   └── DefaultRequestThrottle.java      Executor wrapping for interruptible acquire + max wait cap
│
├── retry/
│   └── RetryRule.java                   Cubic backoff, Retry-After priority, 5-min server cap
│
├── api/
│   └── MockRemoteApi.java               Simulates 429 per integration (max 3 req / 3s window)
│
├── service/
│   └── ArtifactSyncService.java         Orchestrates all three layers per artifact
│
└── Main.java                            Runnable demo — two integrations running concurrently
```

---

## Key Design Decisions

### 1. Non-Bursting Token Bucket
```java
// Bursting (dangerous — DON'T use):
RateLimiter.create(3.33)
// After 10s idle → 33 stored tokens → 33 requests fire instantly → 429 storm

// Non-bursting (what we implement):
// nextAllowedTimeMs resets to NOW on idle — zero accumulation
if (nowMs >= nextAllowedTimeMs) {
    nextAllowedTimeMs = nowMs + intervalMs; // reset from now, not from stored position
    return 0;
}
```

### 2. Executor Wrapping — Interruptible Acquire
```java
// Direct sleep is interruptible, but Guava's RateLimiter.acquire() is NOT.
// This pattern makes any blocking wait interruptible from the calling thread:

Future<?> future = executorService.submit(() -> Thread.sleep(waitMs));
future.get();  // calling thread blocks here — throws InterruptedException on cancel
               // executor thread finishes sleep and its thread gets recycled
```

### 3. Monotonic Time — `nanoTime()` over `currentTimeMillis()`
```java
// System.currentTimeMillis() can jump backward on NTP correction.
// A 1-second backward jump means threads wait 1s longer than expected.
// System.nanoTime() is monotonic — only moves forward.

private static final long NANO_ORIGIN = System.nanoTime();
private static long currentTimeMs() {
    return (System.nanoTime() - NANO_ORIGIN) / 1_000_000L;
}
```

### 4. Max Queue Wait — Fail Fast
```java
// Without this, 50 concurrent threads queue silently:
// thread 50 waits 24,500ms with no feedback, holding locks the whole time.
long waitMs = nextAllowedTimeMs - nowMs;
if (maxWaitMs > 0 && waitMs > maxWaitMs) {
    throw new IllegalStateException("Queue wait " + waitMs + "ms exceeds max " + maxWaitMs + "ms");
}
```

### 5. Retry-After Cap — Don't Trust External Systems Unconditionally
```java
// Server returning Retry-After: 86400 (1 day) must not park threads indefinitely.
long delay = Math.min(serverDelay, MAX_SERVER_RETRY_AFTER_MS); // 5-minute cap
```

### 6. Per-Integration Scope
One `DefaultRequestThrottle` instance per integration URL.
Integration A's burst does not consume Integration B's token budget.

---

## How to Run

Open `Main.java` and run it directly from IntelliJ.

**Expected output:**

- **Integration A** (120 req/min = 2/sec): fires fast enough to hit the API's 3-req/3s window
  → 429 received → RetryRule waits server's `Retry-After: 2s` → retries → succeeds
  → all 5 artifacts synced ✅

- **Integration B** (30 req/min = 0.5/sec): slow enough that the API window always resets
  between calls → 429 never fires → proactive throttle alone is sufficient
  → all 3 artifacts synced ✅

---

## Edge Cases Handled

| Edge Case | Symptom Without Fix | Fix |
|---|---|---|
| Unbounded wait queue | 50 threads queue silently for 25s, holding locks | `reserveToken(maxWaitMs)` — fail fast above threshold |
| Clock skew (NTP jump backward) | Threads wait unexpectedly long after time correction | `System.nanoTime()` — monotonic, never goes backward |
| Extreme server `Retry-After` | Thread sleeps 24h, holds connection + project lock | Cap at `MAX_SERVER_RETRY_AFTER_MS` (5 min) |
| User cancellation mid-wait | Cancel has no effect — thread stuck in `acquire()` | Executor pattern — `future.get()` is interruptible |
| Burst after idle period | Stored tokens fire 30 requests at once after 10s idle | Non-bursting bucket — `nextAllowedTimeMs` resets to `now` |
