package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy;

import java.util.HashMap;
import java.util.Map;

public class TokenBucketStrategy implements RateLimitStrategy {
    private final int maxTokens;
    private final double refillRatePerMs;
    private final Map<String, double[]> userBuckets;  // [tokens, lastRefillTime]

    public TokenBucketStrategy(int maxTokens, double refillRatePerSecond) {
        this.maxTokens = maxTokens;
        this.refillRatePerMs = refillRatePerSecond / 1000.0;
        this.userBuckets = new HashMap<>();
    }

    @Override
    public boolean isAllowed(String userId) {
        long now = System.currentTimeMillis();
        double[] bucket = userBuckets.get(userId);

        if (bucket == null) {
            userBuckets.put(userId, new double[]{maxTokens - 1, now});
            return true;
        }

        // Refill tokens based on elapsed time
        double elapsed = now - bucket[1];
        double newTokens = Math.min(maxTokens, bucket[0] + elapsed * refillRatePerMs);
        bucket[1] = now;

        if (newTokens >= 1) {
            bucket[0] = newTokens - 1;
            return true;
        }

        bucket[0] = newTokens;
        return false;
    }

    @Override
    public int getRemainingRequests(String userId) {
        double[] bucket = userBuckets.get(userId);
        if (bucket == null) return maxTokens;

        long now = System.currentTimeMillis();
        double elapsed = now - bucket[1];
        double tokens = Math.min(maxTokens, bucket[0] + elapsed * refillRatePerMs);
        return (int) tokens;
    }
}
