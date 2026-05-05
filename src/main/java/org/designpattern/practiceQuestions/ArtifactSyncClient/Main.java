package org.designpattern.practiceQuestions.ArtifactSyncClient;

import org.designpattern.practiceQuestions.ArtifactSyncClient.api.MockRemoteApi;
import org.designpattern.practiceQuestions.ArtifactSyncClient.config.ThrottlingSettings;
import org.designpattern.practiceQuestions.ArtifactSyncClient.service.ArtifactSyncService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Demo: two integrations running simultaneously against the same mock API.
//
// What you should observe in the output:
//
//  Integration A (120 req/min = 2/sec, token every 500ms):
//    - First 3 artifacts: throttle fires, API returns 200 OK
//    - Artifact 4: throttle fires, API returns 429 (3 req window hit)
//      → RetryRule sees ApiLimitExceededException with Retry-After: 2000ms
//      → waits 2000ms (server's value, not our own formula)
//      → retries → API window has reset → 200 OK
//    - Artifact 5: throttle fires → 200 OK
//
//  Integration B (30 req/min = 0.5/sec, token every 2000ms):
//    - Slower throttle means the 3s API window always resets between calls
//    - No 429s — proactive throttling prevents the reactive layer from ever firing
//
// This shows both layers working: proactive prevention (B) and reactive recovery (A).
public class Main {

    public static void main(String[] args) throws InterruptedException {

        line('=', 70);
        System.out.println("       ARTIFACT SYNC ENGINE — RATE LIMITER DEMO");
        line('=', 70);

        System.out.println();
        System.out.println("MockRemoteApi rule: max 3 requests per 3-second window per integration");
        System.out.println("(Integration A will hit 429 — watch RetryRule honour server Retry-After)");
        System.out.println("(Integration B won't hit 429 — proactive throttle prevents it)");
        line('-', 70);

        // Shared mock API — per-integration counters, they don't share quota
        MockRemoteApi remoteApi = new MockRemoteApi();

        // Integration A: 120 req/min = 2 tokens/sec = 1 token every 500ms
        // Fires fast enough to hit the API's 3-req/3s window
        ThrottlingSettings settingsA = ThrottlingSettings.create(120);
        ArtifactSyncService serviceA = new ArtifactSyncService("tosca-cloud-1", settingsA, remoteApi);

        // Integration B: 30 req/min = 0.5 tokens/sec = 1 token every 2000ms
        // Fires slowly enough that the API window always resets between calls
        ThrottlingSettings settingsB = ThrottlingSettings.create(30);
        ArtifactSyncService serviceB = new ArtifactSyncService("tosca-cloud-2", settingsB, remoteApi);

        List<String> artifactsA = Arrays.asList("TC-001", "TC-002", "TC-003", "TC-004", "TC-005");
        List<String> artifactsB = Arrays.asList("TC-101", "TC-102", "TC-103");

        System.out.printf("%nIntegration A [tosca-cloud-1]: %s%n", settingsA);
        System.out.printf("Integration B [tosca-cloud-2]: %s%n", settingsB);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> serviceA.syncArtifacts(artifactsA));
        executor.submit(() -> serviceB.syncArtifacts(artifactsB));

        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println();
        line('=', 70);
        System.out.println("Demo complete.");
    }

    private static void line(char c, int len) {
        System.out.println(String.valueOf(c).repeat(len));
    }
}
