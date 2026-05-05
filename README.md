# LLD-System-Design-codes

A collection of Low-Level Design implementations in Java covering design patterns, system design problems, and production-grade engineering concepts.

---

## Artifact Sync Engine — Rate Limiter

**Package:** `org.designpattern.practiceQuestions.ArtifactSyncClient`
**Entry point:** `ArtifactSyncClient/Main.java`

### Problem

An artifact sync engine synchronizes test cases with Tosca Cloud, an external ALM tool that enforces a hard rate limit per integration (e.g. 200 requests/minute). When the limit is exceeded the API returns **HTTP 429 with a `Retry-After` header**. Multiple integrations (tenants) run simultaneously and each must have its own isolated rate limiter. User cancellation must also work even while a thread is blocked waiting for a permit.

### Architecture — Three-Layer Rate Limiting Stack

```
Operation triggered
       │
       ▼
┌──────────────────────────────────────────┐
│  LAYER 1 — DefaultRequestThrottle        │  Proactive: block before sending
│  NonBurstingTokenBucket per integration  │
│  Executor-wrapped acquire (interruptible)│
└─────────────────┬────────────────────────┘
                  │ permit granted
                  ▼
┌──────────────────────────────────────────┐
│  LAYER 2 — MockRemoteApi                 │  HTTP call + 429 detection
│  Parses Retry-After → ApiLimitExceeded   │
└─────────────────┬────────────────────────┘
                  │ exception
                  ▼
┌──────────────────────────────────────────┐
│  LAYER 3 — RetryRule                     │  Reactive: backoff + retry
│  Server Retry-After → use it             │
│  No header → count³ × 100ms (cubic)      │
│  Max 12 attempts, cap at 120s            │
└──────────────────────────────────────────┘
```

### Key Design Decisions

- **Non-bursting token bucket** — `nextAllowedTimeMs` resets to `now` on idle — no token accumulation. A bursting bucket stores unused tokens during idle and dumps them as a burst, hitting the API limit immediately.
- **Executor wrapping** — `acquire()` on Guava's RateLimiter ignores `Thread.interrupt()`. Submitting it to a `CachedThreadPool` and blocking on `future.get()` instead makes the wait interruptible — cancellation works.
- **Per-integration limiter** — one `DefaultRequestThrottle` per integration URL. Integration A's burst does not consume Integration B's token budget.
- **Cubic backoff (`count³ × 100ms`)** — more granular than `2^n` at low attempt counts (100ms at attempt 1) and aggressive enough by attempt 5 (12.5s) to cover server restarts.
- **Server `Retry-After` takes priority** — the server knows exactly when quota resets. Own formula is the fallback when no header is present.

### Project Structure

```
ArtifactSyncClient/
├── config/ThrottlingSettings.java          — rate config value object
├── exception/
│   ├── ConnectorException.java             — base exception
│   ├── ConnectorInterruptedException.java  — cancellation, never retried
│   └── ApiLimitExceededException.java      — carries Retry-After delay (ms)
├── http/ApiResponse.java                   — HTTP response with header access
├── throttle/
│   ├── NonBurstingTokenBucket.java         — strict per-token timing, no burst
│   └── DefaultRequestThrottle.java         — executor wrapping + interruptible acquire
├── retry/RetryRule.java                    — cubic backoff + Retry-After priority
├── api/MockRemoteApi.java                  — simulates 429 per integration (3 req / 3s)
├── service/ArtifactSyncService.java        — orchestrates all three layers
└── Main.java                               — runnable demo, two integrations concurrently
```

### How to Run

Open `ArtifactSyncClient/Main.java` in IntelliJ and run it directly.

**What the demo shows:**
- **Integration A** (120 req/min): fires fast enough to hit the API's 3-req window → 429 received → `RetryRule` waits server's `Retry-After: 2s` → retries → succeeds. All 5 artifacts synced.
- **Integration B** (30 req/min): slow enough that the API window always resets between calls → 429 never fires → proactive throttle alone is sufficient. All 3 artifacts synced.

---
