package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy;

import java.util.HashMap;
import java.util.Map;

public class LFUPolicy implements EvictionPolicy {
    private final Map<String, Integer> frequencyMap;

    public LFUPolicy() {
        this.frequencyMap = new HashMap<>();
    }

    @Override
    public void keyAccessed(String key) {
        frequencyMap.merge(key, 1, Integer::sum);
    }

    @Override
    public void keyAdded(String key) {
        frequencyMap.put(key, 1);
    }

    @Override
    public String evict() {
        String minKey = null;
        int minFreq = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() < minFreq) {
                minFreq = entry.getValue();
                minKey = entry.getKey();
            }
        }
        if (minKey != null) {
            frequencyMap.remove(minKey);
        }
        return minKey;
    }

    @Override
    public void remove(String key) {
        frequencyMap.remove(key);
    }
}
