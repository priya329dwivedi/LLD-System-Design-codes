package org.designpattern.practiceQuestions.RateLimitedSync.throttle;

// Non-bursting token bucket: one token every intervalMs milliseconds.
//
// The "non-bursting" distinction matters: a standard bucket accumulates tokens
// during idle periods and releases them all at once — a burst that immediately
// triggers API rate limits. This bucket resets from NOW on idle, so there is
// no stored balance to release.
public class TokenBucket {

    // Monotonic clock origin — immune to NTP jumps that can make System.currentTimeMillis() go backward.
    private static final long ORIGIN_NS = System.nanoTime();

    private final long intervalMs;
    private long nextAllowedMs = 0;

    public TokenBucket(double requestsPerSecond) {
        this.intervalMs = (long) (1000.0 / requestsPerSecond);
    }

    // Returns how many milliseconds the caller must wait before sending.
    // Returns 0 if a token is available right now.
    public synchronized long reserve() {
        long now = (System.nanoTime() - ORIGIN_NS) / 1_000_000;

        if (now >= nextAllowedMs) {
            nextAllowedMs = now + intervalMs; // reset from NOW — idle time is discarded
            return 0;
        }

        long wait = nextAllowedMs - now;
        nextAllowedMs += intervalMs;          // advance slot for the next caller
        return wait;
    }
}
