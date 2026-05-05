package org.designpattern.practiceQuestions.ArtifactSyncClient.service;

import org.designpattern.practiceQuestions.ArtifactSyncClient.api.MockRemoteApi;
import org.designpattern.practiceQuestions.ArtifactSyncClient.config.ThrottlingSettings;
import org.designpattern.practiceQuestions.ArtifactSyncClient.exception.ApiLimitExceededException;
import org.designpattern.practiceQuestions.ArtifactSyncClient.http.ApiResponse;
import org.designpattern.practiceQuestions.ArtifactSyncClient.retry.RetryRule;
import org.designpattern.practiceQuestions.ArtifactSyncClient.throttle.DefaultRequestThrottle;

import java.util.List;

public class ArtifactSyncService {

    private final String integrationUrl;
    private final DefaultRequestThrottle throttle;
    private final MockRemoteApi remoteApi;

    public ArtifactSyncService(String integrationUrl, ThrottlingSettings settings, MockRemoteApi remoteApi) {
        this.integrationUrl = integrationUrl;
        this.throttle = new DefaultRequestThrottle(integrationUrl, settings);
        this.remoteApi = remoteApi;
    }

    public void syncArtifacts(List<String> artifactIds) {
        System.out.printf("%n[SyncService][%s] Starting sync of %d artifacts%n",
            integrationUrl, artifactIds.size());

        int success = 0, failed = 0;

        for (String artifactId : artifactIds) {
            try {
                String body = RetryRule.execute(artifactId, () -> fetchArtifact(artifactId));
                System.out.printf("[SyncService][%s] ✅ %s synced%n", integrationUrl, artifactId);
                success++;
            } catch (Exception e) {
                System.out.printf("[SyncService][%s] ❌ %s failed: %s%n",
                    integrationUrl, artifactId, e.getMessage());
                failed++;
            }
        }

        System.out.printf("[SyncService][%s] Done — success: %d, failed: %d%n",
            integrationUrl, success, failed);
    }

    // The three-layer call: throttle → API → 429 check
    private String fetchArtifact(String artifactId) {
        // LAYER 1: proactive rate limiting — block until a token is available
        throttle.acquirePermit();

        // LAYER 2: HTTP call to the remote API
        ApiResponse response = remoteApi.fetchArtifact(integrationUrl, artifactId);

        if (response.isRateLimited()) {
            long retryAfterMs = parseRetryAfterMs(response);
            throw new ApiLimitExceededException("429 Too Many Requests", retryAfterMs);
        }

        if (!response.isSuccess()) {
            throw new ApiLimitExceededException(
                "Unexpected response: " + response.getStatusCode()
            );
        }

        // LAYER 3 (retry) is handled by RetryRule.execute() wrapping this method
        return response.getBody();
    }

    // Parses "Retry-After: 2" (seconds) → milliseconds
    private long parseRetryAfterMs(ApiResponse response) {
        String header = response.getHeader("Retry-After");
        if (header == null) return -1;
        try {
            return Long.parseLong(header.trim()) * 1000L;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
