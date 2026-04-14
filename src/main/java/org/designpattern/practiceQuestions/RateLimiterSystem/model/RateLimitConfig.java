package org.designpattern.practiceQuestions.RateLimiterSystem.model;

// Per-user rate limit config — different users can have different limits
public class RateLimitConfig {
    public String userId;
    public Algorithm algorithm;
    public int capacity;        // max requests / tokens
    public double ratePerSecond; // leak rate (leaky) or refill rate (token)

    public RateLimitConfig(String userId, Algorithm algorithm, int capacity, double ratePerSecond) {
        this.userId = userId;
        this.algorithm = algorithm;
        this.capacity = capacity;
        this.ratePerSecond = ratePerSecond;
    }
}