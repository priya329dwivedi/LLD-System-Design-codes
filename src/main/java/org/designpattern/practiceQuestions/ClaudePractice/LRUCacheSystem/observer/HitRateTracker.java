package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.observer;

public class HitRateTracker implements CacheObserver {
    private int hits;
    private int misses;

    @Override
    public void onCacheHit(String key, String value) {
        hits++;
    }

    @Override
    public void onCacheMiss(String key) {
        misses++;
    }

    @Override
    public void onEviction(String key, String value) {
        // not tracked
    }

    public void printStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;
        System.out.println("[STATS] Hits: " + hits + ", Misses: " + misses + ", Hit Rate: " + String.format("%.1f", hitRate) + "%");
    }
}