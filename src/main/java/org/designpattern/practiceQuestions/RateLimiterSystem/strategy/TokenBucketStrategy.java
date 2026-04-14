package org.designpattern.practiceQuestions.RateLimiterSystem.strategy;

import org.designpattern.practiceQuestions.RateLimiterSystem.model.RateLimitConfig;

import java.util.HashMap;
import java.util.Map;

// Token Bucket: bucket starts full; tokens refill over time.
// Request consumes one token and is allowed only if a token is available.
public class TokenBucketStrategy implements RateLimitStrategy {
    Map<String, Double> tokenCounts = new HashMap<>();   // userId -> current tokens
    Map<String, Long> lastRefillTimes = new HashMap<>();  // userId -> last refill timestamp (ms)

    @Override
    public boolean isAllowed(String userId, RateLimitConfig config) {
        long now = System.currentTimeMillis();

        if (!tokenCounts.containsKey(userId)) {
            tokenCounts.put(userId, (double) config.capacity);  // start full
            lastRefillTimes.put(userId, now);
        }

        // Refill: add tokens proportional to elapsed time, capped at capacity
        double elapsed = (now - lastRefillTimes.get(userId)) / 1000.0;  // seconds
        double refilled = elapsed * config.ratePerSecond;
        double newTokens = Math.min(config.capacity, tokenCounts.get(userId) + refilled);
        lastRefillTimes.put(userId, now);

        if (newTokens >= 1) {
            tokenCounts.put(userId, newTokens - 1);
            return true;
        }

        tokenCounts.put(userId, newTokens);
        return false;
    }
}
