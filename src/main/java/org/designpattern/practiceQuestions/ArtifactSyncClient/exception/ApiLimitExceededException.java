package org.designpattern.practiceQuestions.ArtifactSyncClient.exception;

// Thrown when the remote API returns HTTP 429.
// Carries the server's Retry-After value (in ms) so RetryRule can honour it.
public class ApiLimitExceededException extends ConnectorException {

    private final long retryDelayMs;

    public ApiLimitExceededException(String message, long retryDelayMs) {
        super(message);
        this.retryDelayMs = retryDelayMs;
    }

    public ApiLimitExceededException(String message) {
        this(message, -1);
    }

    // True when the server provided a Retry-After value we should honour.
    public boolean hasRetryDelay() {
        return retryDelayMs > 0;
    }

    public long getRetryDelayMs() {
        return retryDelayMs;
    }
}
