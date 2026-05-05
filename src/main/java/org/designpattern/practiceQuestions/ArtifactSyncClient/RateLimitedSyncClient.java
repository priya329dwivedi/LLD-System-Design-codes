package org.designpattern.practiceQuestions.ArtifactSyncClient;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Interview reference: rate-limited artifact sync client (~170 lines).
 *
 * Two-layer design:
 *   Layer 1 — proactive: TokenBucket throttles before each request.
 *   Layer 2 — reactive:  on 429, retry with Retry-After-aware cubic backoff.
 * Cross-cutting: CircuitBreaker halts traffic to a failing endpoint.
 * Transport is a Strategy interface — swap mock for real HTTP without touching sync logic.
 */
public class RateLimitedSyncClient {

    // ── Strategy: pluggable transport ────────────────────────────────────────

    interface RemoteTransport {
        Response fetch(String artifactId);
    }

    record Response(int status, String body, long retryAfterMs) {
        boolean ok()          { return status == 200; }
        boolean rateLimited() { return status == 429; }
    }

    // ── Mock: 3 requests per 3-second sliding window ─────────────────────────

    static class MockApi implements RemoteTransport {
        private static final int  LIMIT     = 3;
        private static final long WINDOW_MS = 3_000;
        private final Deque<Long> hits = new ArrayDeque<>();

        @Override
        public synchronized Response fetch(String id) {
            long now = System.currentTimeMillis();
            hits.removeIf(t -> now - t > WINDOW_MS);
            if (hits.size() >= LIMIT) {
                System.out.println("  [API] 429 → " + id + " (Retry-After: 2s)");
                return new Response(429, null, 2_000);
            }
            hits.addLast(now);
            System.out.printf("  [API] 200 → %s (%d/%d in window)%n", id, hits.size(), LIMIT);
            return new Response(200, "{id:" + id + "}", -1);
        }
    }

    // ── Layer 1: Non-bursting token bucket ───────────────────────────────────
    //
    // Standard token bucket accumulates unused tokens during idle periods and
    // releases them as a burst — which is the exact failure mode we're preventing.
    // Fix: when idle, reset nextMs from NOW (not stored position). No accumulation.

    static class TokenBucket {
        private static final long ORIGIN_NS = System.nanoTime(); // monotonic — immune to NTP jumps
        private final ReentrantLock lock = new ReentrantLock();
        private final long intervalMs;
        private long nextMs = 0;

        TokenBucket(double rps) { intervalMs = (long) (1000.0 / rps); }

        long reserve() {
            lock.lock();
            try {
                long now = (System.nanoTime() - ORIGIN_NS) / 1_000_000;
                if (now >= nextMs) {
                    nextMs = now + intervalMs; // reset from NOW — not from stored position
                    return 0;
                }
                long wait = nextMs - now;
                nextMs += intervalMs;          // advance slot for the next queued caller
                return wait;
            } finally {
                lock.unlock();
            }
        }
    }

    // ── Circuit breaker: CLOSED → OPEN (cooldown) → HALF_OPEN → CLOSED ───────

    static class CircuitBreaker {
        enum State { CLOSED, OPEN, HALF_OPEN }

        private static final int  FAILURE_THRESHOLD = 3;
        private static final long COOLDOWN_MS       = 5_000;

        private State state = State.CLOSED;
        private int   failures;
        private long  openedAt;

        synchronized boolean tryAcquire() {
            if (state == State.OPEN) {
                if (System.currentTimeMillis() - openedAt < COOLDOWN_MS) return false;
                state = State.HALF_OPEN; // probe after cooldown
            }
            return true;
        }

        synchronized void onSuccess() {
            failures = 0;
            state    = State.CLOSED;
        }

        synchronized void onFailure() {
            if (++failures >= FAILURE_THRESHOLD) {
                state    = State.OPEN;
                openedAt = System.currentTimeMillis();
                System.out.println("  [CB] Circuit OPEN — stopping traffic for " + COOLDOWN_MS + "ms");
            }
        }
    }

    // ── Layer 2: retry with Retry-After-aware cubic backoff ──────────────────

    private static final int  MAX_ATTEMPTS = 5;
    private static final long BASE_MS      = 100;
    private static final long CAP_MS       = 30_000;

    // Cubic (n³ × 100ms) grows more smoothly than exponential (2^n):
    // attempt 1→100ms, 2→800ms, 3→2700ms, 4→6400ms — then capped.
    // Server's Retry-After takes priority — it knows exactly when quota resets.
    static long computeDelay(int attempt, long retryAfterMs) {
        if (retryAfterMs > 0) return Math.min(retryAfterMs, CAP_MS);
        return Math.min((long) Math.pow(attempt, 3) * BASE_MS, CAP_MS);
    }

    // ── SyncClient: wires throttle → transport → retry ───────────────────────

    static class SyncClient {
        private final RemoteTransport transport;
        private final TokenBucket     bucket;
        private final CircuitBreaker  cb  = new CircuitBreaker();
        // CachedThreadPool: acquire waits are short (ms), threads idle between syncs
        private final ExecutorService exe = Executors.newCachedThreadPool();

        SyncClient(RemoteTransport transport, double rps) {
            this.transport = transport;
            this.bucket    = new TokenBucket(rps);
        }

        void sync(List<String> ids) {
            for (String id : ids) {
                try {
                    System.out.println("[Sync] OK   : " + fetchWithRetry(id));
                } catch (Exception e) {
                    System.out.println("[Sync] FAIL : " + id + " — " + e.getMessage());
                }
            }
        }

        private String fetchWithRetry(String id) throws Exception {
            for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
                if (!cb.tryAcquire()) throw new RuntimeException("circuit open");

                // Interruptible throttle acquire.
                // Rationale: a non-interruptible blocking call (e.g., Guava's RateLimiter.acquire())
                // ignores Thread.interrupt(). Submitting it to an executor and blocking on
                // future.get() instead makes the CALLING thread interruptible at no extra cost.
                long waitMs = bucket.reserve();
                if (waitMs > 0) {
                    System.out.println("  [Throttle] waiting " + waitMs + "ms");
                    try {
                        exe.submit(() -> sleep(waitMs)).get(); // future.get() propagates interrupt
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("interrupted during throttle wait");
                    } catch (ExecutionException ignored) {} // executor sleep interrupted; permit still consumed
                }

                Response r = transport.fetch(id);

                if (r.ok()) { cb.onSuccess(); return r.body(); }

                cb.onFailure();
                if (attempt == MAX_ATTEMPTS) throw new RuntimeException("max retries exceeded");

                long d = computeDelay(attempt, r.retryAfterMs());
                System.out.printf("  [Retry] attempt=%d delay=%dms%n", attempt, d);
                sleep(d);
            }
            throw new RuntimeException("unreachable");
        }

        static void sleep(long ms) {
            try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    // ── Demo ──────────────────────────────────────────────────────────────────
    // 2 req/sec throttle + mock limited to 3/3s → TC-003 hits the window limit,
    // triggers 429, RetryRule honours Retry-After: 2000ms, then recovers.

    public static void main(String[] args) {
        System.out.println("Mock API: 3 req / 3-second window. Throttle: 2 req/sec.");
        System.out.println("Expect: 429 after 3rd artifact, 2s Retry-After, then recovery.\n");
        SyncClient client = new SyncClient(new MockApi(), 2.0);
        client.sync(List.of("TC-001", "TC-002", "TC-003", "TC-004", "TC-005", "TC-006"));
    }
}
