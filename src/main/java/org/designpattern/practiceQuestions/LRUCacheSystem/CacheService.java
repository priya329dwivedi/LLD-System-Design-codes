package org.designpattern.practiceQuestions.LRUCacheSystem;

import lombok.Setter;
import org.designpattern.practiceQuestions.LRUCacheSystem.CacheStrategy.CacheStrategy;
import org.designpattern.practiceQuestions.LRUCacheSystem.observer.NotifyObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheService {
   @Setter
   private CacheStrategy cacheStrategy;
    private final int capacity;
    private static CacheService instance;
    private final Map<String,String> storage;
    private final List<NotifyObserver> logger;

    public CacheService(int capacity, CacheStrategy cacheStrategy) {
        this.capacity = capacity;
        this.storage = new HashMap<>();
        this.cacheStrategy=cacheStrategy;
        this.logger = new ArrayList<>();
    }

    public static CacheService getInstance(int capacity,CacheStrategy cacheStrategy){
        if(instance==null) return new CacheService(capacity,cacheStrategy);
        return instance;
    }

    public static void resetInstance(){
        instance=null;
    }

    public void keyAdded(String key,String value){
        if(storage.containsKey(key)){
            storage.put(key,value);
            cacheStrategy.keyAccesed(key);
            return;
        }
        if(storage.size()==capacity){
            String removeKey=cacheStrategy.evict();
            if(removeKey!=null){
                String removedValue=storage.remove(removeKey);
                notifyEvict(removeKey,removedValue);
            }
        }
        storage.put(key,value);
        cacheStrategy.keyAdded(key);
    }

    public String getKey(String key){
        if(!storage.containsKey(key)){
            notifyCacheMiss(key);
            return null;
        }
        String value= storage.get(key);
        cacheStrategy.keyAccesed(key);
        notifyCacheHit(key);
        return value;
    }

    public void addObserver(NotifyObserver observer){
        logger.add(observer);
    }

    public void removeObserver(NotifyObserver observer){
        logger.remove(observer);
    }

    private void notifyCacheHit(String key) {
        for(NotifyObserver notifyObserver: logger){
            String value = storage.get(key);
            notifyObserver.onCacheHit(key,value);
        }

    }

    private void notifyCacheMiss(String key) {
        for(NotifyObserver notifyObserver: logger){
            notifyObserver.onCacheMiss(key);
        }
    }

    private void notifyEvict(String key, String value) {
        for(NotifyObserver notifyObserver: logger){
            notifyObserver.onEviction(key,value);
        }
    }
}
