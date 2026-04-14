package org.designpattern.practiceQuestions.SubscriptionPlatform.repository;

import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Content;

import java.util.HashMap;
import java.util.Map;

public class ContentRepository {
    Map<String, Content> store = new HashMap<>();

    public void save(Content content) {
        store.put(content.id, content);
    }

    public Content get(String contentId) {
        return store.get(contentId);
    }
}
