package org.designpattern.practiceQuestions.RateLimitedSync.retry;

// Backoff delay strategy for retries.
//
// Priority 1 — server's Retry-After: the server knows exactly when its quota window resets.
// Priority 2 — cubic backoff (n³ × 100ms): grows smoothly — 100ms, 800ms, 2700ms, ...
//              Cubic is gentler than exponential (2^n) at low attempt counts, giving
//              transient errors time to resolve without waiting too long on attempt 1.
public class RetryPolicy {

    public static final int  MAX_ATTEMPTS = 5;

    private static final long BASE_MS = 100;
    private static final long CAP_MS  = 30_000;

    public static long computeDelay(int attempt, long retryAfterMs) {
        if (retryAfterMs > 0) {
            return Math.min(retryAfterMs, CAP_MS);
        }
        long cubic = (long) Math.pow(attempt, 3) * BASE_MS;
        return Math.min(cubic, CAP_MS);
    }
}
