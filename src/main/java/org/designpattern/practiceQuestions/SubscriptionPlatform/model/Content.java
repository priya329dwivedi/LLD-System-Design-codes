package org.designpattern.practiceQuestions.SubscriptionPlatform.model;

public class Content {
    public String id;
    public String title;
    public PlanTier requiredTier;  // minimum tier needed to watch

    public Content(String id, String title, PlanTier requiredTier) {
        this.id = id;
        this.title = title;
        this.requiredTier = requiredTier;
    }
}
