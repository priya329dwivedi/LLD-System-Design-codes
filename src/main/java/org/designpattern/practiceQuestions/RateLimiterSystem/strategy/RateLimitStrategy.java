package org.designpattern.practiceQuestions.RateLimiterSystem.strategy;

import org.designpattern.practiceQuestions.RateLimiterSystem.model.RateLimitConfig;

public interface RateLimitStrategy {
    boolean isAllowed(String userId, RateLimitConfig config);
}
