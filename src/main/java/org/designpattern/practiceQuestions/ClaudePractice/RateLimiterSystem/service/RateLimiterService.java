package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.service;

import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.observer.RateLimitObserver;
import org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy.RateLimitStrategy;

import java.util.ArrayList;
import java.util.List;

public class RateLimiterService {
    private static RateLimiterService instance;
    private RateLimitStrategy strategy;
    private final List<RateLimitObserver> observers;
    private final int warningThreshold;

    private RateLimiterService(RateLimitStrategy strategy, int warningThreshold) {
        this.strategy = strategy;
        this.observers = new ArrayList<>();
        this.warningThreshold = warningThreshold;
    }

    public static RateLimiterService getInstance(RateLimitStrategy strategy, int warningThreshold) {
        if (instance == null) {
            instance = new RateLimiterService(strategy, warningThreshold);
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void addObserver(RateLimitObserver observer) {
        observers.add(observer);
    }

    public void setStrategy(RateLimitStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean handleRequest(String userId) {
        boolean allowed = strategy.isAllowed(userId);

        if (!allowed) {
            notifyThrottled(userId);
            return false;
        }

        int remaining = strategy.getRemainingRequests(userId);
        if (remaining <= warningThreshold && remaining > 0) {
            notifyApproachingLimit(userId, remaining);
        }

        return true;
    }

    private void notifyThrottled(String userId) {
        for (RateLimitObserver observer : observers) {
            observer.onThrottled(userId);
        }
    }

    private void notifyApproachingLimit(String userId, int remaining) {
        for (RateLimitObserver observer : observers) {
            observer.onApproachingLimit(userId, remaining);
        }
    }
}
