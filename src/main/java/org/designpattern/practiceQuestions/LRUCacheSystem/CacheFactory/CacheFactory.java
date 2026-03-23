/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceQuestions.LRUCacheSystem.CacheFactory;

import org.designpattern.practiceQuestions.LRUCacheSystem.CacheService;
import org.designpattern.practiceQuestions.LRUCacheSystem.CacheStrategy.CacheStrategy;
import org.designpattern.practiceQuestions.LRUCacheSystem.CacheStrategy.FIFO;
import org.designpattern.practiceQuestions.LRUCacheSystem.CacheStrategy.LRU;

public class CacheFactory {
    public static CacheService createCache(String cacheType, int capacity){
        CacheStrategy strategy;
        if(cacheType.equals("fifo")){
            strategy= new FIFO();
        }
        else if(cacheType.equals("lru")){
            strategy= new LRU();
        }
        else{
            throw new IllegalArgumentException("This type of cache we do not support");
        }
        CacheService.resetInstance();
        return CacheService.getInstance(capacity,strategy);
    }
}
