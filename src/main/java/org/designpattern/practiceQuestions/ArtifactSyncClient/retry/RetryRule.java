package org.designpattern.practiceQuestions.ArtifactSyncClient.retry;

import org.designpattern.practiceQuestions.ArtifactSyncClient.exception.ApiLimitExceededException;
import org.designpattern.practiceQuestions.ArtifactSyncClient.exception.ConnectorException;
import org.designpattern.practiceQuestions.ArtifactSyncClient.exception.ConnectorInterruptedException;

import java.util.concurrent.Callable;

public class RetryRule {

    private static final int  MAX_RETRIES = 12;
    private static final long BASE_DELAY_MS = 100;
    private static final long MAX_DELAY_MS = 120_000;        // 2-minute cap for own formula
    private static final long MAX_SERVER_RETRY_AFTER_MS = 300_000; // 5-minute cap on server's value

    // Executes the operation, retrying with exponential backoff on retryable failures.
    public static <T> T execute(String label, Callable<T> operation) throws Exception {
        int attempt = 0;

        while (true) {
            try {
                return operation.call();

            } catch (ConnectorInterruptedException e) {
                // User cancelled the sync — never retry
                System.out.printf("  [RetryRule][%s] Cancellation detected — aborting%n", label);
                throw e;

            } catch (ConnectorException e) {
                attempt++;

                if (!isRetryable(e)) {
                    System.out.printf("  [RetryRule][%s] Non-retryable: %s%n", label, e.getMessage());
                    throw e;
                }

                if (attempt > MAX_RETRIES) {
                    System.out.printf("  [RetryRule][%s] Max retries (%d) exhausted%n", label, MAX_RETRIES);
                    throw e;
                }

                long delayMs = computeDelay(attempt, e);
                System.out.printf("  [RetryRule][%s] Attempt %d failed (%s) — retrying in %dms%n",
                    label, attempt, e.getMessage(), delayMs);

                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new ConnectorInterruptedException("Interrupted during retry wait");
                }
            }
        }
    }

    private static boolean isRetryable(ConnectorException e) {
        // 429 is retryable — the quota will refresh
        // Auth failures, not-found etc. are permanent and must not be retried
        return e instanceof ApiLimitExceededException;
    }

    private static long computeDelay(int attemptCount, ConnectorException e) {
        // Priority 1: honour the server's Retry-After — it knows exactly when quota resets.
        // Cap it: a server returning Retry-After: 86400 (1 day) must not park threads forever.
        // After the cap we still retry — the server may accept us before its stated window expires.
        if (e instanceof ApiLimitExceededException ex && ex.hasRetryDelay()) {
            long serverDelay = ex.getRetryDelayMs();
            long delay = Math.min(serverDelay, MAX_SERVER_RETRY_AFTER_MS);
            if (serverDelay > MAX_SERVER_RETRY_AFTER_MS) {
                System.out.printf("  [RetryRule] Server Retry-After %dms exceeds cap — clamped to %dms%n",
                    serverDelay, delay);
            } else {
                System.out.printf("  [RetryRule] Using server Retry-After: %dms%n", delay);
            }
            return delay;
        }

        // Priority 2: cubic backoff — gradual at low attempts, aggressive in the middle range
        // count³ × 100ms grows more smoothly than 2^count, giving transient errors
        // time to resolve without waiting unnecessarily long at attempt 1.
        long delay = (long) Math.pow(attemptCount, 3) * BASE_DELAY_MS;
        long capped = Math.min(delay, MAX_DELAY_MS);
        System.out.printf("  [RetryRule] Cubic backoff: %d³ × %dms = %dms%s%n",
            attemptCount, BASE_DELAY_MS, delay, capped < delay ? " (capped)" : "");
        return capped;
    }
}
