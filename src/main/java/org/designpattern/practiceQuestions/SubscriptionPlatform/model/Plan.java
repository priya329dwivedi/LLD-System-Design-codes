package org.designpattern.practiceQuestions.SubscriptionPlatform.model;

public class Plan {
    public String id;
    public String name;
    public PlanTier tier;
    public double pricePerMonth;
    public int billingCycleDays;  // 30 = monthly, 365 = yearly

    public Plan(String id, String name, PlanTier tier, double pricePerMonth, int billingCycleDays) {
        this.id = id;
        this.name = name;
        this.tier = tier;
        this.pricePerMonth = pricePerMonth;
        this.billingCycleDays = billingCycleDays;
    }

    @Override
    public String toString() {
        return name + " [" + tier + "] $" + pricePerMonth + "/mo";
    }
}
