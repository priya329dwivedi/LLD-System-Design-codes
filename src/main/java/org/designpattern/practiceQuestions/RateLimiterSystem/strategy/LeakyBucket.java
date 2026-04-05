package org.designpattern.practiceQuestions.RateLimiterSystem.strategy;

import java.util.HashMap;
import java.util.Map;

public class LeakyBucket implements RateLimitStrategy {
    private final int capacity;
    private final double leakRate;
    private final Map<String, Double> waterLevel;
    private final Map<String, Long> lastLeakTime;

    public LeakyBucket(int capacity, double leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.waterLevel = new HashMap<>();
        this.lastLeakTime = new HashMap<>();
    }

    @Override
    public boolean isAllowed(String userId) {
        long now = System.currentTimeMillis();

        if (!waterLevel.containsKey(userId)) {
            waterLevel.put(userId, 1.0);
            lastLeakTime.put(userId, now);
            return true;
        }

        long elapsed = now - lastLeakTime.get(userId);
        double leaked = (elapsed / 1000.0) * leakRate;
        double currentLevel = Math.max(0, waterLevel.get(userId) - leaked);
        lastLeakTime.put(userId, now);

        if (currentLevel + 1 > capacity) {
            waterLevel.put(userId, currentLevel);
            return false;
        }

        waterLevel.put(userId, currentLevel + 1);
        return true;
    }
}