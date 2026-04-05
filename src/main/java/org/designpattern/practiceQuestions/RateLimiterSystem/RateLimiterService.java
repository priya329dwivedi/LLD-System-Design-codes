package org.designpattern.practiceQuestions.RateLimiterSystem;

import lombok.Setter;
import org.designpattern.practiceQuestions.RateLimiterSystem.observer.ThrottleObserver;
import org.designpattern.practiceQuestions.RateLimiterSystem.strategy.RateLimitStrategy;

import java.util.ArrayList;
import java.util.List;

public class RateLimiterService {
    @Setter
    private RateLimitStrategy strategy;
    private static RateLimiterService instance;
    private final List<ThrottleObserver> observers;

    private RateLimiterService(RateLimitStrategy strategy) {
        this.strategy = strategy;
        this.observers = new ArrayList<>();
    }

    public static RateLimiterService getInstance(RateLimitStrategy strategy) {
        if (instance == null) return new RateLimiterService(strategy);
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public boolean isAllowed(String userId) {
        boolean allowed = strategy.isAllowed(userId);
        if (allowed) {
            notifyAllowed(userId);
        } else {
            notifyThrottled(userId);
        }
        return allowed;
    }

    public void addObserver(ThrottleObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ThrottleObserver observer) {
        observers.remove(observer);
    }

    private void notifyAllowed(String userId) {
        for (ThrottleObserver observer : observers) {
            observer.onAllowed(userId);
        }
    }

    private void notifyThrottled(String userId) {
        for (ThrottleObserver observer : observers) {
            observer.onThrottled(userId);
        }
    }
}