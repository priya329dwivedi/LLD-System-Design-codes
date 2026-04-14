
package org.DSA.JunkLLD.SubscriptionModel.model;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Subscription {
    String id;
    String userId;
    String planId;
    LocalDate startDate;
    LocalDate endDate;
    SubscriptionStatus status;

    public Subscription(String userId, String planId, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID().toString().substring(0,8);
        this.userId = userId;
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = SubscriptionStatus.ACTIVE;
    }

    public void setStatus(SubscriptionStatus status){
        this.status= status;
    }
}
