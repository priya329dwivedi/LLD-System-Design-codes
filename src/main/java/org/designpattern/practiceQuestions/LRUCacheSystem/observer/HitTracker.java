/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceQuestions.LRUCacheSystem.observer;

public class HitTracker implements NotifyObserver{
    int hit=0;
    int miss=0;
    @Override
    public void onCacheHit(String key, String value) {
        hit++;
    }

    @Override
    public void onCacheMiss(String key) {
        miss++;
    }

    @Override
    public void onEviction(String key, String value) {
    }

    public void printStatusTracker(){
        int total= hit + miss;
        double hitRate = total == 0 ? 0 : (hit * 100.0) / total;
        System.out.println("[STATS] Hits: " + hit + ", Misses: " + miss + ", Hit Rate: " + String.format("%.1f", hitRate) + "%");
    }
}
