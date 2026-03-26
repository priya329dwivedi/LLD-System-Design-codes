package org.designpattern.practiceQuestions.ConnectionPoolingSystem.strategy;

public class TokenBucketStrategy implements RateLimitStrategy {
    private final double maxTokens;
    private final double refillRate;  // tokens per second
    private double tokens;
    private long lastRefillTime;

    public TokenBucketStrategy(double maxTokens, double refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean acquirePermit() {
        refill();
        if (tokens >= 1) {
            tokens--;
            return true;
        }
        return false;
    }

    @Override
    public synchronized int getRemainingPermits() {
        refill();
        return (int) tokens;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        double elapsed = (now - lastRefillTime) / 1000.0;
        tokens = Math.min(maxTokens, tokens + elapsed * refillRate);
        lastRefillTime = now;
    }
}
