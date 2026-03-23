package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.strategy;

import java.util.HashMap;
import java.util.Map;

public class LRUPolicy implements EvictionPolicy {
    private final Map<String, DoublyNode> nodeMap;
    private final DoublyNode head;
    private final DoublyNode tail;

    private static class DoublyNode {
        String key;
        DoublyNode prev, next;
        DoublyNode(String key) { this.key = key; }
    }

    public LRUPolicy() {
        this.nodeMap = new HashMap<>();
        head = new DoublyNode(null);
        tail = new DoublyNode(null);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void keyAccessed(String key) {
        DoublyNode node = nodeMap.get(key);
        if (node != null) {
            removeNode(node);
            addToFront(node);
        }
    }

    @Override
    public void keyAdded(String key) {
        DoublyNode node = new DoublyNode(key);
        nodeMap.put(key, node);
        addToFront(node);
    }

    @Override
    public String evict() {
        DoublyNode lru = tail.prev;
        if (lru == head) return null;
        removeNode(lru);
        nodeMap.remove(lru.key);
        return lru.key;
    }

    @Override
    public void remove(String key) {
        DoublyNode node = nodeMap.remove(key);
        if (node != null) {
            removeNode(node);
        }
    }

    private void addToFront(DoublyNode node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DoublyNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
