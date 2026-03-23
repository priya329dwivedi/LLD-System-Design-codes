package org.designpattern.practiceQuestions.LRUCacheSystem;

import org.designpattern.practiceQuestions.LRUCacheSystem.CacheFactory.CacheFactory;
import org.designpattern.practiceQuestions.LRUCacheSystem.observer.CacheObserver;
import org.designpattern.practiceQuestions.LRUCacheSystem.observer.HitTracker;

public class Main {
    public static void main(String[] args) {

        // ========== LRU Cache (capacity 3) ==========
        System.out.println("========== LRU Cache ==========\n");

        CacheService service = CacheFactory.createCache("lru", 3);
        CacheObserver logger = new CacheObserver();
        HitTracker hitTracker = new HitTracker();
        service.addObserver(logger);
        service.addObserver(hitTracker);

        // Fill cache
        service.keyAdded("A", "Apple");
        service.keyAdded("B", "Banana");
        service.keyAdded("C", "Cherry");

        // Access A — moves A to front (most recently used)
        System.out.println("\n--- Getting A ---");
        System.out.println("Result: " + service.getKey("A"));

        // Miss — D not in cache
        System.out.println("\n--- Getting D (miss) ---");
        System.out.println("Result: " + service.getKey("D"));

        // Add D — cache full, evicts B (least recently used, since A was accessed)
        System.out.println("\n--- Adding D (should evict B) ---");
        service.keyAdded("D", "Date");

        // Verify B is evicted
        System.out.println("\n--- Getting B (should miss) ---");
        System.out.println("Result: " + service.getKey("B"));

        // C and A should still be there
        System.out.println("\n--- Getting C ---");
        System.out.println("Result: " + service.getKey("C"));

        System.out.println("\n--- Getting A ---");
        System.out.println("Result: " + service.getKey("A"));

        // Print hit/miss stats
        System.out.println();
        hitTracker.printStatusTracker();

        // ========== Switch to FIFO ==========
        System.out.println("\n========== Switching to FIFO ==========\n");

        CacheService fifoCache = CacheFactory.createCache("fifo", 3);
        fifoCache.addObserver(new CacheObserver());

        fifoCache.keyAdded("X", "Xylophone");
        fifoCache.keyAdded("Y", "Yogurt");
        fifoCache.keyAdded("Z", "Zucchini");

        // Access X — in FIFO this doesn't change order
        fifoCache.getKey("X");

        // Add W — cache full, evicts X (first added, FIFO ignores access)
        System.out.println("\n--- Adding W (should evict X in FIFO) ---");
        fifoCache.keyAdded("W", "Walnut");

        // X should be gone even though we accessed it
        System.out.println("\n--- Getting X (should miss in FIFO) ---");
        System.out.println("Result: " + fifoCache.getKey("X"));

        // Y should still be there
        System.out.println("\n--- Getting Y ---");
        System.out.println("Result: " + fifoCache.getKey("Y"));
    }
}
