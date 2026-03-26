package org.designpattern.practiceQuestions.ConnectionPoolingSystem.strategy;

public class NoLimitStrategy implements RateLimitStrategy {
    @Override
    public boolean acquirePermit() {
        return true;  // always allow
    }

    @Override
    public int getRemainingPermits() {
        return Integer.MAX_VALUE;
    }
}
