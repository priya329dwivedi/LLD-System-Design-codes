/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceQuestions.LRUCacheSystem.CacheStrategy;

import java.util.LinkedList;
import java.util.Queue;

public class FIFO implements CacheStrategy{

    private final Queue<String> queue = new LinkedList<>();


    @Override
    public void keyAccesed(String key) {
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
    public void removeKey(String key) {
        queue.remove(key);
    }
}
