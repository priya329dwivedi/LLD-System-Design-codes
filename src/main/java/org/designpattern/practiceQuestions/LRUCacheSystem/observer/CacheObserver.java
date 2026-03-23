/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceQuestions.LRUCacheSystem.observer;

public class CacheObserver implements NotifyObserver{

    @Override
    public void onCacheHit(String key, String value) {
        System.out.println("[CACHE HIT] with key " + key+ " with value"+ value);
    }

    @Override
    public void onCacheMiss(String key) {
        System.out.println("[CACHE MISS] with key " + key);
    }

    @Override
    public void onEviction(String key, String value) {
        System.out.println("[KEY EVICT] with key " + key+ " with value"+ value);
    }
}
