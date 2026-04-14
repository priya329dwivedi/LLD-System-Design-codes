package org.designpattern.practiceQuestions.SubscriptionPlatform.observer;

import org.designpattern.practiceQuestions.SubscriptionPlatform.model.Plan;
import org.designpattern.practiceQuestions.SubscriptionPlatform.model.User;

public class NotificationService implements SubscriptionObserver {

    @Override
    public void onSubscribed(User user, Plan plan) {
        System.out.println("[Notify] " + user.name + " subscribed to " + plan.name + ". Welcome email sent to " + user.email);
    }

    @Override
    public void onCancelled(User user, Plan plan) {
        System.out.println("[Notify] " + user.name + " cancelled " + plan.name + ". Cancellation email sent to " + user.email);
    }

    @Override
    public void onRenewed(User user, Plan plan) {
        System.out.println("[Notify] " + user.name + "'s " + plan.name + " renewed. Renewal email sent to " + user.email);
    }

    @Override
    public void onPlanChanged(User user, Plan oldPlan, Plan newPlan) {
        System.out.println("[Notify] " + user.name + " changed plan: " + oldPlan.name + " → " + newPlan.name + ". Email sent to " + user.email);
    }
}
