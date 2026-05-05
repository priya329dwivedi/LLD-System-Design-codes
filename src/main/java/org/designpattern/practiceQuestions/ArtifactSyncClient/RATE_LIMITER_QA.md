# Rate Limiter — Questions & Answers

Everything I asked while building and understanding this project.

---

## Q1. What is rate limiting, why was it needed, and what problem does it solve?

**Why needed:**
The sync engine fires API calls for every artifact — potentially hundreds simultaneously.
Tosca Cloud enforces a hard limit (e.g. 200 req/min). Exceed it and it returns HTTP 429.

**What happens without rate limiting:**
```
20 threads fire simultaneously
  → 20 requests hit API in first 100ms
  → API limit: ~3 req/sec
  → API returns 429 for most
  → Retry fires → more requests → more 429s
  → Spiral: integration blacklisted
```

**What it solves:**
Two separate problems that look like one:

| Problem | Type | Layer |
|---|---|---|
| Sending too many requests too fast | Proactive | Token bucket (Layer 1) |
| 429s arriving despite slowing down | Reactive | Retry with backoff (Layer 3) |

Both layers are needed because the rate limiter only controls *your* traffic —
other clients sharing the same API quota can still push you into 429 territory.

---

## Q2. What is the "API quota" — what do we actually call it?

The quota is a rule set by **Tosca Cloud (the server)**, not by you.

Tosca Cloud says: *"I will serve a maximum of 200 requests per minute from this API key."*

That limit lives on their server. You don't control it.

**Why "shared" matters:**
Your company has one Tosca Cloud account → one API key → multiple teams using it.

```
Team A's sync engine  ──┐
                         ├──► same API key ──► Tosca Cloud (200 req/min total)
Team B's sync engine  ──┘
```

Team A sends 120 req/min. Team B sends 100 req/min.
Combined: 220 req/min → Tosca returns 429 to you, even though *your* engine alone
was within its configured limit.

Your rate limiter has no visibility into Team B. That's why the retry layer exists —
for 429s that arrive through no fault of your own.

---

## Q3. How is the rate limiter implemented in code?

Three classes work together:

**`ThrottlingSettings`** — the config:
```java
ThrottlingSettings.create(120)
// rate = 120, unit = 1 minute
// means: allow 120 requests per minute
```

**`NonBurstingTokenBucket`** — the core logic:
```java
public long reserveToken(long maxWaitMs) {
    synchronized (lock) {
        long nowMs = currentTimeMs();

        if (nowMs >= nextAllowedTimeMs) {
            nextAllowedTimeMs = nowMs + intervalMs; // reset from NOW — non-bursting key
            return 0;                               // go immediately
        }

        long waitMs = nextAllowedTimeMs - nowMs;    // how long to wait
        nextAllowedTimeMs += intervalMs;            // reserve slot for next caller
        return waitMs;
    }
}
```

**`DefaultRequestThrottle`** — calls the bucket, does the actual sleeping:
```java
public void acquirePermit() {
    long waitMs = tokenBucket.reserveToken(MAX_QUEUE_WAIT_MS);

    Future<?> future = executorService.submit(() -> Thread.sleep(waitMs));
    future.get(); // calling thread blocks here — interruptible
}
```

---

## Q4. What does `Duration unit` mean?

`Duration` is the **type**, `unit` is the **variable name**.

```java
private final Duration unit; // stores a duration of time e.g. "1 minute"
```

Example:
```java
Duration unit = Duration.ofMinutes(1); // unit = 1 minute
```

`ThrottlingSettings` stores two things together:
```java
private final int rate;      // the count  → 120
private final Duration unit; // the window → 1 minute
```

Together: allow `120` requests per `1 minute`.

---

## Q5. Why is `getPermitsPerSecond()` needed?

Because `NonBurstingTokenBucket` thinks in **requests per second**, not per minute.

`getPermitsPerSecond()` does the conversion:
```java
return rate / (double) unit.getSeconds();
// 120 / 60.0 = 2.0 requests per second
```

**The full chain:**
```
"120 per minute"
      ↓  getPermitsPerSecond()
"2.0 per second"
      ↓  NonBurstingTokenBucket constructor
1000.0 / 2.0 = 500ms between each token
```

`ThrottlingSettings` owns the conversion. `NonBurstingTokenBucket` just works
with per-second rate. Each class does one thing.

---

## Q6. Walk me through the conversion step by step

Config: `ThrottlingSettings.create(120)`
```
rate = 120
unit = Duration.ofMinutes(1) → 60 seconds
```

Step 1 — `getPermitsPerSecond()`:
```
120 / 60.0 = 2.0 req/sec
```

Step 2 — `NonBurstingTokenBucket` constructor:
```java
this.intervalMs = (long)(1000.0 / permitsPerSecond);
//                        1000.0 / 2.0
//                      = 500ms
```

Step 3 — what threads experience:
```
Thread 1 → wait   0ms → fires at t=0
Thread 2 → wait 500ms → fires at t=500
Thread 3 → wait 1000ms→ fires at t=1000
```

Two simple divisions: minutes → seconds → milliseconds between tokens.

---

## Q7. What does "non-bursting" mean and why does it matter?

**Bursting bucket (dangerous):**
Stores unused tokens during idle periods. After 10s idle on a 2/sec limiter
→ 20 stored tokens → 20 threads fire instantly → 429 storm.

**Non-bursting bucket (what we use):**
The key line in `reserveToken()`:
```java
if (nowMs >= nextAllowedTimeMs) {
    nextAllowedTimeMs = nowMs + intervalMs; // reset from NOW, not from stored position
    return 0;
}
```

When a token is available, we reset from **current time**, not from the last
scheduled slot. Idle time is thrown away. No accumulation possible.

**Side by side — 10s idle, then 4 threads arrive:**
```
BURSTING:
  Thread 1: wait 0ms  → fires at t=10000  ← all fire at once → BURST → 429
  Thread 2: wait 0ms  → fires at t=10000
  Thread 3: wait 0ms  → fires at t=10000
  Thread 4: wait 0ms  → fires at t=10000

NON-BURSTING:
  Thread 1: wait 0ms   → fires at t=10000  ← spaced out, no burst
  Thread 2: wait 500ms → fires at t=10500
  Thread 3: wait 1000ms→ fires at t=11000
  Thread 4: wait 1500ms→ fires at t=11500
```

---

## Q8. "This shows both layers working: proactive prevention (B) and reactive recovery (A)" — what does this mean?

Two different things happen in the demo, one per integration.

**Integration B — Proactive Prevention:**
B is throttled to 30 req/min = 1 token every 2000ms.
The MockRemoteApi resets its window every 3 seconds.
Because B waits 2000ms between calls, the window resets before B can ever
hit the 3-request limit. **B never sees a 429.** The throttle alone kept it safe.
Layer 3 (RetryRule) never fires. That's proactive.

**Integration A — Reactive Recovery:**
A is throttled to 120 req/min = 1 token every 500ms.
It fires 3 requests in 1500ms — within the same 3s window. On the 4th request,
the API returns 429. The throttle didn't prevent this. So Layer 3 (RetryRule)
kicks in — reads Retry-After, waits 2s, retries, succeeds. That's reactive.

**Why run both:** shows that the two layers serve different roles.
The throttle handles the common case. Retry handles the edge case.

---

## Q9. Which of these layers is in the real SDK project?

Both.

**Layer 1 (Proactive — `DefaultRequestThrottle`)** is in the SDK.
Every operation acquires a permit from the rate limiter before making the HTTP call.

**Layer 3 (Reactive — `RetryRule`)** is also in the SDK.
When a 429 slips through, `ResponseProcessor` detects it, parses the Retry-After
header, throws `ApiLimitExceededException`, and `RetryRule` handles the backoff
and retry.

**Key insight:** the rate limiter is `@RepositoryScoped` — one per integration.
It only controls *your* integration's request rate. But the API quota is often
shared across all clients hitting the same server. So even if your integration
stays within its configured rate, another client can push the combined total
over the limit and you still get a 429. That's why both layers exist.
