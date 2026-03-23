package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.observer;

public class AlertNotifier implements RateLimitObserver {
    @Override
    public void onThrottled(String userId) {
        System.out.println("[ALERT] User " + userId + " has been THROTTLED!");
    }

    @Override
    public void onApproachingLimit(String userId, int remaining) {
        System.out.println("[ALERT] User " + userId + " approaching limit — " + remaining + " requests left");
    }
}
