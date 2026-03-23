package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.observer;

public interface RateLimitObserver {
    void onThrottled(String userId);
    void onApproachingLimit(String userId, int remaining);
}
