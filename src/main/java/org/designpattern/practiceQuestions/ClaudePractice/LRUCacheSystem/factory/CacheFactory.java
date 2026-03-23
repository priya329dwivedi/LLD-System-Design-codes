package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.factory;

import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.service.CacheService;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy.EvictionPolicy;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy.FIFOPolicy;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy.LFUPolicy;
import org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy.LRUPolicy;

public class CacheFactory {
    public static CacheService createCache(String policyType, int capacity) {
        EvictionPolicy policy;
        switch (policyType) {
            case "LRU":
                policy = new LRUPolicy();
                break;
            case "LFU":
                policy = new LFUPolicy();
                break;
            case "FIFO":
                policy = new FIFOPolicy();
                break;
            default:
                throw new IllegalArgumentException("Unknown eviction policy: " + policyType);
        }
        CacheService.resetInstance();
        return CacheService.getInstance(capacity, policy);
    }
}
