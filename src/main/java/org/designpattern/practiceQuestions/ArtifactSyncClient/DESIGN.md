# Design Patterns & Decisions

## 1. Strategy — `RemoteTransport` interface

**What:** `SyncClient` depends on the `RemoteTransport` interface, not `MockApi` directly.  
**Why:** Decouples I/O from sync logic. In tests, inject `MockApi`. In production, inject a real
HTTP client. `SyncClient` never changes. This is exactly where most candidates put the mock
directly in the sync class and then can't test it without live network.

---

## 2. Non-Bursting Token Bucket

**What:** On idle, `nextMs` resets from `now` — not from the stored future position.  
**Why the distinction matters:** A standard token bucket accumulates permits during idle periods.
After 10 seconds of idle at 2 req/sec, it holds 20 tokens and fires all 20 instantly — a burst
that hits the API rate limit immediately and produces the exact 429 storm we're trying to prevent.
The non-bursting variant has no stored balance; idle time is simply discarded.

**Monotonic clock:** `System.nanoTime()` instead of `System.currentTimeMillis()` — the latter can
jump backward on NTP correction, making `nextMs - now` suddenly huge and parking all threads.

---

## 3. Interruptible Acquire via `Future.get()`

**What:** The throttle wait is submitted to an `ExecutorService`; the calling thread blocks on
`future.get()` instead of sleeping directly.  
**Why:** In the real SDK, Guava's `RateLimiter.acquire()` ignores `Thread.interrupt()` — you
cannot cancel a sync job mid-throttle. Submitting the blocking call to an executor and blocking on
`future.get()` makes the calling thread interruptible at the cost of one thread context switch.
Even with plain `Thread.sleep()` (which is interruptible), this pattern is worth knowing for
wrapping any non-interruptible blocking call.

---

## 4. Cubic Backoff (`n³ × 100ms`) over Exponential (`2^n`)

**What:** `delay = attempt³ × 100ms`, capped at 30s.  
**Why cubic over exponential:**

| Attempt | Cubic (n³×100ms) | Exponential (2^n × 100ms) |
|---------|-----------------|--------------------------|
| 1       | 100ms           | 200ms                    |
| 2       | 800ms           | 400ms                    |
| 3       | 2,700ms         | 800ms                    |
| 5       | 12,500ms        | 3,200ms                  |
| 8       | 51,200ms (→cap) | 25,600ms                 |

Cubic grows more gradually at low attempts — good for transient blips — then accelerates
in mid-range, giving the server time to recover without retrying forever.

**Server Retry-After takes priority.** The server knows exactly when its quota window resets.
Our formula is only a fallback for when that header is absent.

---

## 5. Circuit Breaker (CLOSED → OPEN → HALF_OPEN → CLOSED)

**What:** After 3 consecutive failures, stop sending traffic for 5 seconds (OPEN). Then probe
with one request (HALF_OPEN). On success, close; on failure, reopen.  
**Why:** Retry loops alone keep hammering a failing endpoint, consuming rate-limit quota and
thread time. The circuit breaker gives the downstream system breathing room and fails fast
for callers instead of making them wait through MAX_ATTEMPTS retries every time.

**HALF_OPEN is the critical state:** it limits recovery probes to one request. Without it,
OPEN → CLOSED would allow a burst of queued requests, potentially re-tripping immediately.

---

## 6. Partial Failure Isolation in `SyncClient.sync()`

**What:** Each artifact is caught independently; failures are logged and the loop continues.  
**Why:** A 429 on artifact #4 must not abort artifacts #5–#1000. The sync engine trades
completeness for resilience — partial sync with known gaps is better than total failure.
