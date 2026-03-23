package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem;

import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.factory.CacheFactory;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.observer.CacheLogger;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.observer.HitRateTracker;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.service.CacheService;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy.FIFOPolicy;

public class Main {
    public static void main(String[] args) {

        // ========== LRU Cache (capacity 3) ==========
        System.out.println("========== LRU Policy ==========\n");

        CacheService cache = CacheFactory.createCache("LRU", 3);
        CacheLogger logger = new CacheLogger();
        HitRateTracker tracker = new HitRateTracker();
        cache.addObserver(logger);
        cache.addObserver(tracker);

        cache.put("A", "Apple");
        cache.put("B", "Banana");
        cache.put("C", "Cherry");

        cache.get("A");       // HIT — A moves to front
        cache.get("D");       // MISS

        cache.put("D", "Date");  // Cache full → evicts B (least recently used)

        cache.get("B");       // MISS — B was evicted
        cache.get("C");       // HIT

        System.out.println();
        tracker.printStats();

        // ========== Switch to FIFO ==========
        System.out.println("\n========== Switching to FIFO Policy ==========\n");

        cache.setEvictionPolicy(new FIFOPolicy());

        cache.put("E", "Elderberry");  // FIFO doesn't know old keys, evicts from its queue
        cache.put("F", "Fig");
        cache.put("G", "Grape");

        cache.get("E");       // HIT or MISS depends on eviction
        cache.get("A");       // check if A survived

        System.out.println();
        tracker.printStats();

        // ========== LFU Cache (fresh instance) ==========
        System.out.println("\n========== LFU Policy (Fresh Cache) ==========\n");

        CacheService lfuCache = CacheFactory.createCache("LFU", 3);
        lfuCache.addObserver(new CacheLogger());

        lfuCache.put("X", "Xylophone");
        lfuCache.put("Y", "Yogurt");
        lfuCache.put("Z", "Zucchini");

        lfuCache.get("X");    // X accessed twice (put + get) → freq 2
        lfuCache.get("X");    // X freq 3
        lfuCache.get("Y");    // Y freq 2

        lfuCache.put("W", "Walnut");  // Cache full → evicts Z (freq 1, least frequent)

        lfuCache.get("Z");    // MISS — Z was evicted
        lfuCache.get("X");    // HIT — X is safe (highest frequency)
    }
}
