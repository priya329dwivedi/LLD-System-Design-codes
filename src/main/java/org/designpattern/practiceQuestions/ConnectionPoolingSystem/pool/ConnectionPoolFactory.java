package org.designpattern.practiceQuestions.ConnectionPoolingSystem.pool;

import org.designpattern.practiceQuestions.ConnectionPoolingSystem.model.Repository;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.strategy.NoLimitStrategy;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.strategy.RateLimitStrategy;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.strategy.TokenBucketStrategy;

public class ConnectionPoolFactory {

    private static final long DEFAULT_IDLE_TIMEOUT_MS = 30000;  // 30 seconds

    public static ConnectionPool createPool(Repository repository) {
        return new ConnectionPool(
                repository.getId(),
                repository.getMaxConnections(),
                DEFAULT_IDLE_TIMEOUT_MS
        );
    }

    public static RateLimitStrategy createThrottle(Repository repository) {
        if (repository.getRequestsPerSecond() <= 0) {
            return new NoLimitStrategy();
        }
        return new TokenBucketStrategy(
                repository.getRequestsPerSecond(),
                repository.getRequestsPerSecond()  // bucket size = rate
        );
    }
}
