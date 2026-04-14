package org.designpattern.practiceQuestions.RateLimiterSystem.strategy;

import org.designpattern.practiceQuestions.RateLimiterSystem.model.RateLimitConfig;

import java.util.HashMap;
import java.util.Map;

// Leaky Bucket: water drips in with each request, leaks out at a fixed rate.
// Request is allowed only if there is room (currentLevel + 1 <= capacity).
public class LeakyBucketStrategy implements RateLimitStrategy {
    Map<String, Double> bucketLevels = new HashMap<>();  // userId -> current water level
    Map<String, Long> lastLeakTimes = new HashMap<>();   // userId -> last leak timestamp (ms)

    @Override
    public boolean isAllowed(String userId, RateLimitConfig config) {
        long now = System.currentTimeMillis();

        if (!bucketLevels.containsKey(userId)) {
            bucketLevels.put(userId, 0.0);
            lastLeakTimes.put(userId, now);
        }

        // Leak: remove water proportional to elapsed time
        double elapsed = (now - lastLeakTimes.get(userId)) / 1000.0;  // seconds
        double leaked = elapsed * config.ratePerSecond;
        double newLevel = Math.max(0, bucketLevels.get(userId) - leaked);
        lastLeakTimes.put(userId, now);

        if (newLevel + 1 <= config.capacity) {
            bucketLevels.put(userId, newLevel + 1);
            return true;
        }

        bucketLevels.put(userId, newLevel);
        return false;
    }
}
