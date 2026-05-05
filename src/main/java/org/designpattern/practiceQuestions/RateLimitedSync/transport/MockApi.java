package org.designpattern.practiceQuestions.RateLimitedSync.transport;

import org.designpattern.practiceQuestions.RateLimitedSync.model.Response;

import java.util.ArrayDeque;
import java.util.Deque;

// Simulates a remote API with a sliding-window rate limit.
// Limit: 3 requests per 3-second window. Exceeding it returns 429 with Retry-After.
public class MockApi implements RemoteTransport {

    private static final int  LIMIT          = 3;
    private static final long WINDOW_MS      = 3_000;
    private static final long RETRY_AFTER_MS = 2_000;

    private final Deque<Long> requestTimestamps = new ArrayDeque<>();

    @Override
    public synchronized Response fetch(String artifactId) {
        long now = System.currentTimeMillis();

        // Remove timestamps outside the current window
        requestTimestamps.removeIf(t -> now - t > WINDOW_MS);

        if (requestTimestamps.size() >= LIMIT) {
            System.out.printf("  [API] 429 → %s  (window full: %d/%d)%n",
                artifactId, requestTimestamps.size(), LIMIT);
            return Response.rateLimited(RETRY_AFTER_MS);
        }

        requestTimestamps.addLast(now);
        System.out.printf("  [API] 200 → %s  (%d/%d in window)%n",
            artifactId, requestTimestamps.size(), LIMIT);
        return Response.ok("{\"id\":\"" + artifactId + "\"}");
    }
}
