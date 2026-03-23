package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.factory;

import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy.FixedWindowStrategy;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy.RateLimitStrategy;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy.SlidingWindowStrategy;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy.TokenBucketStrategy;

public class RateLimiterFactory {
    public static RateLimitStrategy createStrategy(String tier) {
        switch (tier) {
            case "free":
                return new FixedWindowStrategy(5, 60000);         // 5 requests per minute
            case "premium":
                return new SlidingWindowStrategy(20, 60000);      // 20 requests per minute
            case "enterprise":
                return new TokenBucketStrategy(100, 10);          // 100 max tokens, 10 refill/sec
            default:
                throw new IllegalArgumentException("Unknown tier: " + tier);
        }
    }
}
