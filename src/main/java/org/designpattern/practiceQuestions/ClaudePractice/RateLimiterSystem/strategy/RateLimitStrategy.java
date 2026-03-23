package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy;

public interface RateLimitStrategy {
    boolean isAllowed(String userId);
    int getRemainingRequests(String userId);
}
