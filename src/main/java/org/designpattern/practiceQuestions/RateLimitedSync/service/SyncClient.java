package org.designpattern.practiceQuestions.RateLimitedSync.service;

import org.designpattern.practiceQuestions.RateLimitedSync.circuit.CircuitBreaker;
import org.designpattern.practiceQuestions.RateLimitedSync.model.Response;
import org.designpattern.practiceQuestions.RateLimitedSync.retry.RetryPolicy;
import org.designpattern.practiceQuestions.RateLimitedSync.throttle.TokenBucket;
import org.designpattern.practiceQuestions.RateLimitedSync.transport.RemoteTransport;

import java.util.List;
import java.util.Optional;

// Orchestrates the two-layer rate limiting flow:
//   Layer 1 (proactive) — TokenBucket throttles before every request.
//   Layer 2 (reactive)  — on 429, wait then retry with Retry-After-aware backoff.
// CircuitBreaker halts traffic when the downstream is consistently failing.
public class SyncClient {

    private final RemoteTransport transport;
    private final TokenBucket     bucket;
    private final CircuitBreaker  circuitBreaker;

    public SyncClient(RemoteTransport transport, double requestsPerSecond) {
        this.transport      = transport;
        this.bucket         = new TokenBucket(requestsPerSecond);
        this.circuitBreaker = new CircuitBreaker();
    }

    public void sync(List<String> artifactIds) {
        int success = 0, failed = 0;

        for (String id : artifactIds) {
            Optional<String> result = fetchWithRetry(id);
            if (result.isPresent()) {
                System.out.println("[Sync] OK   : " + id + " → " + result.get());
                success++;
            } else {
                System.out.println("[Sync] FAIL : " + id);
                failed++;
            }
        }

        System.out.printf("%n[Sync] Done — success: %d, failed: %d%n", success, failed);
    }

    private Optional<String> fetchWithRetry(String id) {
        for (int attempt = 1; attempt <= RetryPolicy.MAX_ATTEMPTS; attempt++) {

            if (!circuitBreaker.allowRequest()) {
                System.out.println("[CB] Circuit open — skipping " + id);
                return Optional.empty();
            }

            // Layer 1: throttle — wait for a token before sending the request
            long waitMs = bucket.reserve();
            if (waitMs > 0) {
                System.out.println("  [Throttle] waiting " + waitMs + "ms");
                sleep(waitMs);
            }

            Response response = transport.fetch(id);

            if (response.isOk()) {
                circuitBreaker.onSuccess();
                return Optional.of(response.getBody());
            }

            // Layer 2: reactive — handle 429 with backoff and retry
            circuitBreaker.onFailure();

            if (attempt < RetryPolicy.MAX_ATTEMPTS) {
                long delay = RetryPolicy.computeDelay(attempt, response.getRetryAfterMs());
                System.out.printf("  [Retry] attempt=%d, waiting=%dms%n", attempt, delay);
                sleep(delay);
            }
        }

        return Optional.empty();
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
