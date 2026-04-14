package org.designpattern.practiceQuestions.RateLimiterSystem;

import org.designpattern.practiceQuestions.RateLimiterSystem.model.RateLimitConfig;
import org.designpattern.practiceQuestions.RateLimiterSystem.observer.ThrottleObserver;
import org.designpattern.practiceQuestions.RateLimiterSystem.repository.UserConfigRepository;
import org.designpattern.practiceQuestions.RateLimiterSystem.strategy.RateLimitStrategy;

import java.util.ArrayList;
import java.util.List;

public class RateLimiterService {
    UserConfigRepository userConfigRepo;
    RateLimitStrategy strategy;
    List<ThrottleObserver> observers;

    public RateLimiterService(UserConfigRepository userConfigRepo, RateLimitStrategy strategy) {
        this.userConfigRepo = userConfigRepo;
        this.strategy = strategy;
        this.observers = new ArrayList<>();
    }

    public void addObserver(ThrottleObserver observer) {
        observers.add(observer);
    }

    public void registerUser(RateLimitConfig config) {
        userConfigRepo.save(config);
    }

    public boolean isAllowed(String userId) {
        RateLimitConfig config = userConfigRepo.get(userId);
        boolean allowed = strategy.isAllowed(userId, config);

        for (ThrottleObserver observer : observers) {
            if (allowed) observer.onAllowed(userId);
            else observer.onThrottled(userId);
        }

        return allowed;
    }
}
