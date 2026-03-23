package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.observer;

public class CacheLogger implements CacheObserver {
    @Override
    public void onCacheHit(String key, String value) {
        System.out.println("[LOG] Cache HIT: key=" + key + ", value=" + value);
    }

    @Override
    public void onCacheMiss(String key) {
        System.out.println("[LOG] Cache MISS: key=" + key);
    }

    @Override
    public void onEviction(String key, String value) {
        System.out.println("[LOG] EVICTED: key=" + key + ", value=" + value);
    }
}
