package org.designpattern.practiceQuestions.RateLimiterSystem.factory;

import org.designpattern.practiceQuestions.RateLimiterSystem.RateLimiterService;
import org.designpattern.practiceQuestions.RateLimiterSystem.strategy.LeakyBucket;
import org.designpattern.practiceQuestions.RateLimiterSystem.strategy.RateLimitStrategy;

public class RateLimiterFactory {
    public static RateLimiterService createRateLimiter(String type, int capacity, double rate) {
        RateLimitStrategy strategy;
        if (type.equals("leaky_bucket")) {
            strategy = new LeakyBucket(capacity, rate);
        } else {
            throw new IllegalArgumentException("Unsupported rate limiter type: " + type);
        }
        RateLimiterService.resetInstance();
        return RateLimiterService.getInstance(strategy);
    }
}