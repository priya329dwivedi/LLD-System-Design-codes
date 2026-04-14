package org.designpattern.practiceQuestions.SubscriptionPlatform.model;

import java.time.LocalDate;

public class Subscription {
    public String id;
    public String userId;
    public String planId;
    public SubscriptionStatus status;
    public LocalDate startDate;
    public LocalDate endDate;
    public boolean autoRenew;

    public Subscription(String id, String userId, String planId,
                        SubscriptionStatus status, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.planId = planId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.autoRenew = true;
    }
}
