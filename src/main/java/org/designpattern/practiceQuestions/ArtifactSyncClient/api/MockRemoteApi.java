package org.designpattern.practiceQuestions.ArtifactSyncClient.api;

import org.designpattern.practiceQuestions.ArtifactSyncClient.http.ApiResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Simulates a Tosca Cloud REST API that enforces rate limits per integration.
// Each integration URL gets its own independent request window — they don't share quota.
//
// Limit: MAX_REQUESTS_PER_WINDOW requests per WINDOW_MS milliseconds.
// When exceeded: returns HTTP 429 with Retry-After: RETRY_AFTER_SECONDS.
public class MockRemoteApi {

    private static final int  MAX_REQUESTS_PER_WINDOW = 3;
    private static final long WINDOW_MS               = 3000; // 3-second window
    private static final long RETRY_AFTER_SECONDS     = 2;    // tell client to wait 2s

    // Per-integration state: url → [requestCount, windowStartMs]
    private final Map<String, long[]> windows = new ConcurrentHashMap<>();

    public ApiResponse fetchArtifact(String integrationUrl, String artifactId) {
        long[] window = windows.computeIfAbsent(
            integrationUrl, k -> new long[]{0, System.currentTimeMillis()}
        );

        synchronized (window) {
            long nowMs = System.currentTimeMillis();

            if (nowMs - window[1] > WINDOW_MS) {
                // Current window expired — open a fresh one
                window[0] = 0;
                window[1] = nowMs;
            }

            if (window[0] >= MAX_REQUESTS_PER_WINDOW) {
                System.out.printf("  [MockApi][%s] Rate limit hit for %s → 429 (Retry-After: %ds)%n",
                    integrationUrl, artifactId, RETRY_AFTER_SECONDS);
                return ApiResponse.tooManyRequests(RETRY_AFTER_SECONDS);
            }

            window[0]++;
            System.out.printf("  [MockApi][%s] Request %d/%d in window → 200 OK for %s%n",
                integrationUrl, (int) window[0], MAX_REQUESTS_PER_WINDOW, artifactId);
            return ApiResponse.ok(String.format(
                "{\"id\":\"%s\",\"name\":\"TestCase-%s\",\"status\":\"PASSED\"}",
                artifactId, artifactId
            ));
        }
    }
}
