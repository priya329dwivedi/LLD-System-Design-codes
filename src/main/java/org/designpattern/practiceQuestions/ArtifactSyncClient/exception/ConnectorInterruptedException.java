package org.designpattern.practiceQuestions.ArtifactSyncClient.exception;

// Thrown when a sync operation is cancelled by the user (Thread.interrupt()).
// RetryRule never retries this — cancellation is permanent.
public class ConnectorInterruptedException extends ConnectorException {

    public ConnectorInterruptedException(String message) {
        super(message);
        Thread.currentThread().interrupt(); // restore interrupt flag
    }
}
