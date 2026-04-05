package org.designpattern.practiceQuestions.RateLimiterSystem.observer;

public interface ThrottleObserver {
    void onAllowed(String userId);
    void onThrottled(String userId);
}