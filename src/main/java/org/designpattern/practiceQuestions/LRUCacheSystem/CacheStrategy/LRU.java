/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceQuestions.LRUCacheSystem.CacheStrategy;

import java.util.HashMap;
import java.util.Map;

public class LRU implements CacheStrategy{
    private final Map<String,DoublyNode> storage;
    private final DoublyNode head;
    private final DoublyNode tail;

    public LRU() {
        this.storage = new HashMap<>();
        this.head = new DoublyNode(null);
        this.tail= new DoublyNode(null);
        head.next=tail;
        tail.prev=head;
    }

    public static class DoublyNode{
        String key;
        DoublyNode prev, next;
        DoublyNode(String key){
            this.key=key;
        }
    }

    @Override
    public void keyAccesed(String key) {
        DoublyNode node = storage.get(key);
        removeNode(node);
        addToFront(node);

    }

    @Override
    public void keyAdded(String key) {
        DoublyNode node = new DoublyNode(key);
        storage.put(key,node);
        addToFront(node);
    }

    private void addToFront(DoublyNode node) {
        node.next=head.next;
        node.prev=head;
        head.next.prev=node;
        head.next=node;
    }

    private void removeNode(DoublyNode node) {
        node.prev.next=node.next;
        node.next.prev=node.prev;
    }

    @Override
    public String evict() {
        DoublyNode lru= tail.prev;
        if(lru==head) return null;
        removeNode(lru);
        storage.remove(lru.key);
        return lru.key;
    }

    @Override
    public void removeKey(String key) {
        DoublyNode node= storage.remove(key);
        if(node!=null) removeNode(node);
    }
}
