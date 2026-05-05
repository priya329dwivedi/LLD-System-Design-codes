package org.designpattern.practiceQuestions.ArtifactSyncClient.throttle;

import org.designpattern.practiceQuestions.ArtifactSyncClient.config.ThrottlingSettings;
import org.designpattern.practiceQuestions.ArtifactSyncClient.exception.ConnectorInterruptedException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// One instance per integration (repository-scoped in the real SDK).
// Ensures all HTTP calls from this integration are throttled to the configured rate.
public class DefaultRequestThrottle {

    // Fail fast if a thread would have to queue longer than this.
    // Prevents silent 50-second waits when too many threads pile up.
    private static final long MAX_QUEUE_WAIT_MS = 10_000; // 10 seconds

    // CachedThreadPool: rate-limit waits are short (ms). Creates threads on demand,
    // recycles them after 60s idle. Better than a fixed pool for bursty short tasks.
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final NonBurstingTokenBucket tokenBucket; // null = throttling disabled
    private final String integrationUrl;

    public DefaultRequestThrottle(String integrationUrl, ThrottlingSettings settings) {
        this.integrationUrl = integrationUrl;
        this.tokenBucket = (settings == null)
            ? null
            : new NonBurstingTokenBucket(settings.getPermitsPerSecond());
    }

    public void acquirePermit() {
        if (tokenBucket == null) return; // throttling disabled for this integration

        long waitMs = tokenBucket.reserveToken(MAX_QUEUE_WAIT_MS);

        if (waitMs == 0) {
            log("Permit granted immediately");
            return;
        }

        log("Waiting " + waitMs + "ms for rate-limit permit...");
        long startMs = System.currentTimeMillis();

        // KEY PATTERN — mirrors the SDK's fix for Guava's non-interruptible acquire():
        //
        // Problem: if we just called Thread.sleep(waitMs) on the calling thread,
        // Thread.interrupt() would work — but in the real SDK, Guava's acquire()
        // is NOT interruptible. Calling thread.interrupt() has zero effect.
        //
        // Solution: submit the blocking wait to an executor thread.
        // The CALLING thread then blocks on future.get() instead.
        // future.get() IS interruptible — throws InterruptedException immediately.
        // The executor thread finishes its wait and its thread gets recycled.
        Future<?> future = executorService.submit(() -> {
            try {
                Thread.sleep(waitMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // restore flag on executor thread
            }
        });

        try {
            future.get(); // calling thread blocks here — interruptible
            log("Permit acquired after " + (System.currentTimeMillis() - startMs) + "ms");
        } catch (InterruptedException e) {
            future.cancel(true);
            throw new ConnectorInterruptedException("Interrupted while waiting for rate-limit permit");
        } catch (ExecutionException e) {
            // executor thread's sleep was interrupted — permit is still consumed, continue
        }
    }

    private void log(String msg) {
        System.out.printf("  [Throttle][%s] %s%n", integrationUrl, msg);
    }
}
