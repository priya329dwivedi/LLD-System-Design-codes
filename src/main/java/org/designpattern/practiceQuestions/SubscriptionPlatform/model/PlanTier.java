package org.designpattern.practiceQuestions.SubscriptionPlatform.model;

// Ordinal matters: FREE=0 < BASIC=1 < PREMIUM=2
// Used to compare user's tier vs content's required tier
public enum PlanTier {
    FREE, BASIC, PREMIUM
}
