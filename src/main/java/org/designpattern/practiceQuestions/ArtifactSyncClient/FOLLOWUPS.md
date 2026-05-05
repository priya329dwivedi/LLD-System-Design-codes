# Interviewer Follow-Up Questions

---

## 1. "Why non-bursting? What's wrong with a standard token bucket?"

**What they're probing:** Do you understand the burst-accumulation failure mode, or did you just
copy a pattern without knowing why?

**Strong answer:**
> A standard token bucket stores permits during idle periods. After 10 seconds of inactivity at
> 2 req/sec, it holds 20 tokens and fires all 20 in rapid succession — a burst that hits the
> remote API rate limit immediately and triggers the exact 429 storm we designed to prevent.
> The non-bursting variant discards idle time: `nextMs` resets from `now`, so there's no
> accumulated balance to release.

---

## 2. "Why cubic backoff instead of exponential (2^n)?"

**What they're probing:** Did you pick a number, or did you reason about backoff shape?

**Strong answer:**
> Exponential grows too fast at low attempt counts — attempt 1 waits 200ms, attempt 5 waits
> 3.2 seconds — which undershoots for transient blips that resolve in milliseconds.
> Cubic (`n³ × 100ms`) starts gentler: 100ms → 800ms → 2.7s → 12.5s, then caps.
> It gives the server more recovery time in the mid-range without being too aggressive early.
> More importantly, server Retry-After takes priority when present — my formula is a fallback
> for when the header is absent.

---

## 3. "How does the interruptible acquire work? Why not just `Thread.sleep()`?"

**What they're probing:** Understanding of blocking call interruptibility — common gap in
Java concurrency knowledge.

**Strong answer:**
> `Thread.sleep()` is interruptible in plain Java, but in the real system I modelled this on,
> Guava's `RateLimiter.acquire()` silently ignores `Thread.interrupt()` — you can't cancel a
> sync job that's parked in an acquire call. The fix: submit the blocking call to an executor
> and block the calling thread on `future.get()` instead. `future.get()` IS interruptible —
> it throws `InterruptedException` immediately. The executor thread finishes its wait and
> gets recycled. This pattern generalises to any non-interruptible blocking call.

---

## 4. "What's the risk in the HALF_OPEN state of your circuit breaker?"

**What they're probing:** Whether you thought about the HALF_OPEN → re-trip edge case, or
just know the textbook three states.

**Strong answer:**
> HALF_OPEN is the most dangerous state. Multiple threads checking `tryAcquire()` concurrently
> could all see HALF_OPEN and all be let through simultaneously, which is effectively the same
> as briefly closing the circuit — potentially re-triggering the failures that opened it.
> In this implementation `tryAcquire()` is `synchronized`, so only one thread probes at a time.
> In a distributed system, you'd need a distributed lock or a counter to limit probes to exactly
> one across all instances.

---

## 5. "This works for one process. How would you extend it to distributed rate limiting — multiple instances sharing one quota?"

**What they're probing:** Can you reason about the distributed systems extension? Senior signal.

**Strong answer:**
> The local token bucket breaks entirely in a multi-instance setup — each instance has its own
> bucket and there's no coordination. Three options:
>
> 1. **Redis + Lua script:** Store the sliding window or token count in Redis. A Lua script makes
>    the check-and-decrement atomic. High throughput, adds Redis as a dependency.
>
> 2. **Token bucket in a sidecar/rate-limit service:** One service owns the quota; all instances
>    request permits from it. Centralised logic, single point of failure.
>
> 3. **Rely on server-side 429 + backoff:** Accept that instances will occasionally over-shoot
>    and let the reactive layer (Retry-After backoff) handle it. Simpler — no coordination —
>    but wastes quota on requests that will 429.
>
> Which one to pick depends on how tight the limit is and whether wasted quota is acceptable.
> For Tosca's per-endpoint limits shared across our instances, option 3 was good enough because
> the retry layer already handled 429s correctly.
