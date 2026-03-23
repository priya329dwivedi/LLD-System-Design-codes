package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowStrategy implements RateLimitStrategy {
    private final int maxRequests;
    private final long windowSizeMs;
    private final Map<String, Queue<Long>> userTimestamps;

    public SlidingWindowStrategy(int maxRequests, long windowSizeMs) {
        this.maxRequests = maxRequests;
        this.windowSizeMs = windowSizeMs;
        this.userTimestamps = new HashMap<>();
    }

    @Override
    public boolean isAllowed(String userId) {
        long now = System.currentTimeMillis();
        Queue<Long> timestamps = userTimestamps.computeIfAbsent(userId, k -> new LinkedList<>());

        // Remove expired timestamps
        while (!timestamps.isEmpty() && now - timestamps.peek() >= windowSizeMs) {
            timestamps.poll();
        }

        if (timestamps.size() < maxRequests) {
            timestamps.offer(now);
            return true;
        }

        return false;
    }

    @Override
    public int getRemainingRequests(String userId) {
        long now = System.currentTimeMillis();
        Queue<Long> timestamps = userTimestamps.get(userId);
        if (timestamps == null) return maxRequests;

        while (!timestamps.isEmpty() && now - timestamps.peek() >= windowSizeMs) {
            timestamps.poll();
        }

        return Math.max(0, maxRequests - timestamps.size());
    }
}
