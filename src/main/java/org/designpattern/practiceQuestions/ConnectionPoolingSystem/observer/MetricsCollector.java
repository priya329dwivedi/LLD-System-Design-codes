package org.designpattern.practiceQuestions.ConnectionPoolingSystem.observer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsCollector implements PoolObserver {
    private final Map<String, AtomicInteger> requestsProcessed = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> exhaustionCount = new ConcurrentHashMap<>();

    @Override
    public void onConnectionAcquired(String repositoryId, String connectionId) {
        requestsProcessed.computeIfAbsent(repositoryId, k -> new AtomicInteger(0)).incrementAndGet();
    }

    @Override
    public void onConnectionReleased(String repositoryId, String connectionId) {
        // tracked via acquired
    }

    @Override
    public void onPoolExhausted(String repositoryId) {
        exhaustionCount.computeIfAbsent(repositoryId, k -> new AtomicInteger(0)).incrementAndGet();
    }

    @Override
    public void onPoolShutdown(String repositoryId) {}

    @Override
    public void onIdleCleanup(String repositoryId, int closedCount) {}

    public void printMetrics() {
        System.out.println("\n========== Pool Metrics ==========");
        for (Map.Entry<String, AtomicInteger> entry : requestsProcessed.entrySet()) {
            String repoId = entry.getKey();
            System.out.println("  " + repoId + " — requests processed: " + entry.getValue().get()
                    + ", pool exhaustions: " + exhaustionCount.getOrDefault(repoId, new AtomicInteger(0)).get());
        }
    }
}
