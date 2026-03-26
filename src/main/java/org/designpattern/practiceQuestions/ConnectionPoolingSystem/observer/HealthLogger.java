package org.designpattern.practiceQuestions.ConnectionPoolingSystem.observer;

public class HealthLogger implements PoolObserver {
    @Override
    public void onConnectionAcquired(String repositoryId, String connectionId) {
        System.out.println("[HEALTH] " + repositoryId + " — acquired connection " + connectionId);
    }

    @Override
    public void onConnectionReleased(String repositoryId, String connectionId) {
        System.out.println("[HEALTH] " + repositoryId + " — released connection " + connectionId);
    }

    @Override
    public void onPoolExhausted(String repositoryId) {
        System.out.println("[HEALTH] WARNING: " + repositoryId + " — pool EXHAUSTED, requests will block!");
    }

    @Override
    public void onPoolShutdown(String repositoryId) {
        System.out.println("[HEALTH] " + repositoryId + " — pool shut down");
    }

    @Override
    public void onIdleCleanup(String repositoryId, int closedCount) {
        System.out.println("[HEALTH] " + repositoryId + " — cleaned " + closedCount + " idle connections");
    }
}
