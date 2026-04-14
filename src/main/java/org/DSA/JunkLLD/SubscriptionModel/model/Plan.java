
package org.DSA.JunkLLD.SubscriptionModel.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Plan {
    String id;
    String name;
    PlantTier planTier;
    Integer pricePerMonth;
    Integer billingCycle;

    public Plan(String name, PlantTier planTier, Integer pricePerMonth, Integer billingCycle) {
        this.id =  UUID.randomUUID().toString().substring(0,8);
        this.name = name;
        this.planTier = planTier;
        this.pricePerMonth = pricePerMonth;
        this.billingCycle = billingCycle;
    }
}
