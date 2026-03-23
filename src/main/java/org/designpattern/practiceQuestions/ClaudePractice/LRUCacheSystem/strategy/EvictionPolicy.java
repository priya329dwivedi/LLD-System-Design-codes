package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy;

public interface EvictionPolicy {
    void keyAccessed(String key);
    void keyAdded(String key);
    String evict();
    void remove(String key);
}
