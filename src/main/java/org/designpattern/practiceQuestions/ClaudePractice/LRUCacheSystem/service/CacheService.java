package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.service;

import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.observer.CacheObserver;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy.EvictionPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheService {
    private static CacheService instance;
    private final Map<String, String> storage;
    private final List<CacheObserver> observers;
    private EvictionPolicy evictionPolicy;
    private final int capacity;

    private CacheService(int capacity, EvictionPolicy evictionPolicy) {
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
        this.storage = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public static CacheService getInstance(int capacity, EvictionPolicy evictionPolicy) {
        if (instance == null) {
            instance = new CacheService(capacity, evictionPolicy);
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void addObserver(CacheObserver observer) {
        observers.add(observer);
    }

    public void setEvictionPolicy(EvictionPolicy evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    public String get(String key) {
        if (!storage.containsKey(key)) {
            notifyCacheMiss(key);
            return null;
        }
        String value = storage.get(key);
        evictionPolicy.keyAccessed(key);
        notifyCacheHit(key, value);
        return value;
    }

    public void put(String key, String value) {
        if (storage.containsKey(key)) {
            storage.put(key, value);
            evictionPolicy.keyAccessed(key);
            return;
        }
        if (storage.size() == capacity) {
            String evictedKey = evictionPolicy.evict();
            if (evictedKey != null) {
                String evictedValue = storage.remove(evictedKey);
                notifyEviction(evictedKey, evictedValue);
            }
        }
        storage.put(key, value);
        evictionPolicy.keyAdded(key);
    }

    public int size() {
        return storage.size();
    }

    private void notifyCacheHit(String key, String value) {
        for (CacheObserver observer : observers) {
            observer.onCacheHit(key, value);
        }
    }

    private void notifyCacheMiss(String key) {
        for (CacheObserver observer : observers) {
            observer.onCacheMiss(key);
        }
    }

    private void notifyEviction(String key, String value) {
        for (CacheObserver observer : observers) {
            observer.onEviction(key, value);
        }
    }
}
