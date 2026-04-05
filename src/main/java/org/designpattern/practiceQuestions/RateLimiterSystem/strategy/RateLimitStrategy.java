package org.designpattern.practiceQuestions.RateLimiterSystem.strategy;

public interface RateLimitStrategy {
    boolean isAllowed(String userId);
}