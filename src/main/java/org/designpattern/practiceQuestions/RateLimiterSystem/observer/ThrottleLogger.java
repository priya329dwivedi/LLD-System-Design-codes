package org.designpattern.practiceQuestions.RateLimiterSystem.observer;

public class ThrottleLogger implements ThrottleObserver {

    @Override
    public void onAllowed(String userId) {
        System.out.println("[LOG] Request ALLOWED for user: " + userId);
    }

    @Override
    public void onThrottled(String userId) {
        System.out.println("[ALERT] Request THROTTLED for user: " + userId);
    }
}