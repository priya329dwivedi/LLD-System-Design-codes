package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOPolicy implements EvictionPolicy {
    private final Queue<String> queue;

    public FIFOPolicy() {
        this.queue = new LinkedList<>();
    }

    @Override
    public void keyAccessed(String key) {
        // FIFO doesn't care about access order — do nothing
    }

    @Override
    public void keyAdded(String key) {
        queue.offer(key);
    }

    @Override
    public String evict() {
        return queue.poll();
    }

    @Override
    public void remove(String key) {
        queue.remove(key);
    }
}
