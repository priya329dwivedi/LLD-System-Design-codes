package org.designpattern.practiceQuestions.ClaudePractice.RateLimiterSystem.strategy;

import java.util.HashMap;
import java.util.Map;

public class FixedWindowStrategy implements RateLimitStrategy {
    private final int maxRequests;
    private final long windowSizeMs;
    private final Map<String, long[]> userWindows;  // [windowStart, requestCount]

    public FixedWindowStrategy(int maxRequests, long windowSizeMs) {
        this.maxRequests = maxRequests;
        this.windowSizeMs = windowSizeMs;
        this.userWindows = new HashMap<>();
    }

    @Override
    public boolean isAllowed(String userId) {
        long now = System.currentTimeMillis();
        long[] window = userWindows.get(userId);

        if (window == null || now - window[0] >= windowSizeMs) {
            userWindows.put(userId, new long[]{now, 1});
            return true;
        }

        if (window[1] < maxRequests) {
            window[1]++;
            return true;
        }

        return false;
    }

    @Override
    public int getRemainingRequests(String userId) {
        long now = System.currentTimeMillis();
        long[] window = userWindows.get(userId);
        if (window == null || now - window[0] >= windowSizeMs) {
            return maxRequests;
        }
        return Math.max(0, maxRequests - (int) window[1]);
    }
}
