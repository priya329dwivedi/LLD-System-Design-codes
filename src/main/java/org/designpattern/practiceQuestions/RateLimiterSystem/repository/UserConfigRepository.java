package org.designpattern.practiceQuestions.RateLimiterSystem.repository;

import org.designpattern.practiceQuestions.RateLimiterSystem.model.RateLimitConfig;

import java.util.HashMap;
import java.util.Map;

// Simulates a DB table: each user has their own rate limit config
// (e.g. free users get 5 req/s, premium users get 50 req/s)
public class UserConfigRepository {
    Map<String, RateLimitConfig> store = new HashMap<>();  // userId -> config

    public void save(RateLimitConfig config) {
        store.put(config.userId, config);
    }

    public RateLimitConfig get(String userId) {
        return store.get(userId);
    }
}
