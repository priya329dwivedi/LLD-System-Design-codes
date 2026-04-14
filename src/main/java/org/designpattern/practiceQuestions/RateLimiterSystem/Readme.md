# Rate Limiter System — LLD (Interview Style)

## Requirements
- `isAllowed(userId)` — return true if allowed, false if throttled
- Support multiple rate limiting algorithms (Strategy pattern)
- Each user has their own independent limit and can have different configs
- Notify observers when a request is allowed or throttled (Observer pattern)

---

## Layers

```
Model       →  Algorithm (enum), RateLimitConfig
Repository  →  UserConfigRepository  (HashMap: userId → config)
Strategy    →  RateLimitStrategy, LeakyBucketStrategy, TokenBucketStrategy
Observer    →  ThrottleObserver, ThrottleLogger
Service     →  RateLimiterService
Main        →  demo driver
```

---

## Class Summary

```
model/
  Algorithm.java           enum: LEAKY_BUCKET, TOKEN_BUCKET
  RateLimitConfig.java     userId, algorithm, capacity, ratePerSecond

repository/
  UserConfigRepository.java    HashMap<String, RateLimitConfig>  — keyed by userId

strategy/
  RateLimitStrategy.java       interface: boolean isAllowed(String userId, RateLimitConfig config)
  LeakyBucketStrategy.java     stateful: HashMap<userId, Double> bucketLevels + lastLeakTimes
  TokenBucketStrategy.java     stateful: HashMap<userId, Double> tokenCounts + lastRefillTimes

observer/
  ThrottleObserver.java        interface: onAllowed(String), onThrottled(String)
  ThrottleLogger.java          prints [LOG] / [ALERT]

RateLimiterService.java   wires repo + strategy + observers, calls isAllowed()
Main.java                 demo
```

---

## Algorithm Intuition

### Leaky Bucket
```
Each request adds 1 unit of water. Water leaks out at ratePerSecond.
Allowed if: currentLevel + 1 <= capacity
```

### Token Bucket
```
Bucket starts full. Tokens refill at ratePerSecond, capped at capacity.
Allowed if: currentTokens >= 1  (consumes 1 token)
```

| | Leaky Bucket | Token Bucket |
|---|---|---|
| Burst handling | Smooths out bursts | Allows bursts up to capacity |
| Use case | Steady-rate APIs | APIs that allow occasional spikes |

---

## Key Data Structures

| Field | Type | Purpose |
|-------|------|---------|
| `UserConfigRepository.store` | `HashMap<String, RateLimitConfig>` | Per-user limits (free vs premium) |
| `LeakyBucketStrategy.bucketLevels` | `HashMap<String, Double>` | Current water level per user |
| `LeakyBucketStrategy.lastLeakTimes` | `HashMap<String, Long>` | Last time we leaked, to compute elapsed time |
| `TokenBucketStrategy.tokenCounts` | `HashMap<String, Double>` | Remaining tokens per user |

---

## What to say to the interviewer

1. **Strategy pattern is the core** — `RateLimitStrategy` interface lets you swap Leaky Bucket for Token Bucket (or Sliding Window) without touching the service. Each strategy is stateful per-user using its own HashMaps.

2. **Repository stores per-user configs** — `UserConfigRepository` maps userId → `RateLimitConfig`, so free users get (capacity=5, rate=2/s) while premium users get (capacity=50, rate=20/s). Same service, different limits.

3. **Observer decouples notifications** — `ThrottleLogger` is plugged in at setup. To add alerting or metrics, just add another observer — service code stays untouched.

4. **Leaky vs Token tradeoff** — Leaky Bucket enforces a strict output rate (good for downstream protection). Token Bucket allows short bursts up to capacity (better UX for bursty clients). Pick based on the requirement.

5. **Time-based state** — Both strategies track `lastLeakTime`/`lastRefillTime` in ms. On each call, elapsed seconds are computed to adjust the level. No background threads or scheduled tasks needed.
