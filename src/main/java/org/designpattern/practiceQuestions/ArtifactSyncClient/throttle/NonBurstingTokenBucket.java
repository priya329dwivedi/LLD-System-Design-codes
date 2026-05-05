package org.designpattern.practiceQuestions.ArtifactSyncClient.throttle;

// Strict token bucket with NO burst accumulation.
//
// Bursting bucket (the danger): if no requests are made for 10s on a 3/sec limiter,
// it stores 30 tokens. The next 30 requests fire instantly — a burst that hits the
// API rate limit immediately.
//
// This bucket: on idle, nextAllowedTimeMs is in the past. The first request gets
// a token immediately and nextAllowedTimeMs is set to NOW + interval. No stored
// tokens, no burst possible.
public class NonBurstingTokenBucket {

    private final long intervalMs;       // ms between tokens e.g. 500ms for 2/sec
    private long nextAllowedTimeMs = 0;  // when the next token becomes available
    private final Object lock = new Object();

    public NonBurstingTokenBucket(double permitsPerSecond) {
        this.intervalMs = (long) (1000.0 / permitsPerSecond);
    }

    // Reserves the next token and returns how many milliseconds the caller must wait.
    // Returns 0 if a token is available immediately.
    // Throws if the projected wait exceeds maxWaitMs — fail fast rather than queue silently.
    public long reserveToken(long maxWaitMs) {
        synchronized (lock) {
            long nowMs = currentTimeMs();

            if (nowMs >= nextAllowedTimeMs) {
                // Token available now — reset from current time, NOT from stored position.
                // This is the key non-bursting behaviour: idle time is NOT accumulated.
                nextAllowedTimeMs = nowMs + intervalMs;
                return 0;
            }

            // Token not yet available — check if caller is willing to wait this long.
            long waitMs = nextAllowedTimeMs - nowMs;
            if (maxWaitMs > 0 && waitMs > maxWaitMs) {
                throw new IllegalStateException(
                    "Rate limit queue wait " + waitMs + "ms exceeds max allowed " + maxWaitMs + "ms. " +
                    "Too many concurrent threads for the configured rate."
                );
            }

            nextAllowedTimeMs += intervalMs; // advance queue slot for next caller
            return waitMs;
        }
    }

    // Edge case 2: System.currentTimeMillis() can jump backward on NTP correction.
    // If time goes back, nextAllowedTimeMs - nowMs becomes huge → threads wait unexpectedly long.
    // Fix: derive ms from System.nanoTime() which is monotonic — it never goes backward.
    private static final long NANO_ORIGIN = System.nanoTime();

    private static long currentTimeMs() {
        return (System.nanoTime() - NANO_ORIGIN) / 1_000_000L;
    }

    public long getIntervalMs() { return intervalMs; }
}
