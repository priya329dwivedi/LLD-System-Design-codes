package org.designpattern.practiceQuestions.ConnectionPoolingSystem.strategy;

public interface RateLimitStrategy {
    boolean acquirePermit();
    int getRemainingPermits();
}
