package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy;

import java.util.HashMap;
import java.util.Map;

public class LeakyBucketStrategy implements RateLimitStrategy {
    private final int capacity;
    private final double leakRatePerMs;
    private final Map<String, double[]> userBuckets;  // [waterLevel, lastLeakTime]

    public LeakyBucketStrategy(int capacity, double leakRatePerSecond) {
        this.capacity = capacity;
        this.leakRatePerMs = leakRatePerSecond / 1000.0;
        this.userBuckets = new HashMap<>();
    }

    @Override
    public boolean isAllowed(String userId) {
        long now = System.currentTimeMillis();
        double[] bucket = userBuckets.get(userId);

        if (bucket == null) {
            userBuckets.put(userId, new double[]{1, now});
            return true;
        }

        // Leak water based on elapsed time
        double elapsed = now - bucket[1];
        double leaked = elapsed * leakRatePerMs;
        double currentLevel = Math.max(0, bucket[0] - leaked);
        bucket[1] = now;

        if (currentLevel + 1 > capacity) {
            bucket[0] = currentLevel;
            return false;
        }

        bucket[0] = currentLevel + 1;
        return true;
    }

    @Override
    public int getRemainingRequests(String userId) {
        double[] bucket = userBuckets.get(userId);
        if (bucket == null) return capacity;

        long now = System.currentTimeMillis();
        double elapsed = now - bucket[1];
        double leaked = elapsed * leakRatePerMs;
        double currentLevel = Math.max(0, bucket[0] - leaked);
        return (int) (capacity - currentLevel);
    }
}