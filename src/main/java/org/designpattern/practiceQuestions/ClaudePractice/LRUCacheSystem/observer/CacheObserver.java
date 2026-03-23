package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.observer;

public interface CacheObserver {
    void onCacheHit(String key, String value);
    void onCacheMiss(String key);
    void onEviction(String key, String value);
}
