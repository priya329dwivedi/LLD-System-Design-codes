package org.designpattern.practiceQuestions.SubscriptionPlatform.observer;

import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Plan;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.User;

public interface SubscriptionObserver {
    void onSubscribed(User user, Plan plan);
    void onCancelled(User user, Plan plan);
    void onRenewed(User user, Plan plan);
    void onPlanChanged(User user, Plan oldPlan, Plan newPlan);
}
