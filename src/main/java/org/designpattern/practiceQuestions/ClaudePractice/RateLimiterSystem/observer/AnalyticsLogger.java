package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.observer;

public class AnalyticsLogger implements RateLimitObserver {
    private int totalThrottled = 0;

    @Override
    public void onThrottled(String userId) {
        totalThrottled++;
        System.out.println("[ANALYTICS] Throttle event #" + totalThrottled + " for user: " + userId);
    }

    @Override
    public void onApproachingLimit(String userId, int remaining) {
        System.out.println("[ANALYTICS] User " + userId + " at " + remaining + " remaining");
    }

    public int getTotalThrottled() {
        return totalThrottled;
    }
}
