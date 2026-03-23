package org.designpattern.practiceQuestions.ClaudePractice.LRUCacheSystem.model;

import lombok.Getter;

@Getter
public class Node {
    private final String key;
    private String value;
    public Node prev;
    public Node next;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
