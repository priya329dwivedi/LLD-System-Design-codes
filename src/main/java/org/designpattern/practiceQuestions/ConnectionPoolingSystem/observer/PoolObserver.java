package org.designpattern.practiceQuestions.ConnectionPoolingSystem.observer;

public interface PoolObserver {
    void onConnectionAcquired(String repositoryId, String connectionId);
    void onConnectionReleased(String repositoryId, String connectionId);
    void onPoolExhausted(String repositoryId);
    void onPoolShutdown(String repositoryId);
    void onIdleCleanup(String repositoryId, int closedCount);
}
