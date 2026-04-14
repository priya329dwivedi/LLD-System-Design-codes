
package org.DSA.JunkLLD.SubscriptionModel.Observer;

public class Email implements Notificationservice{
    String emailId;

    public Email(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public void onSubscribe(String name, String planName) {
        System.out.println("[Subscribe Notification sent via Email] "+ name + " [with Plan ] " + planName);
    }

    @Override
    public void cancelSubscription(String subscriptionOId) {
        System.out.println("[Cancel Notification sent via Email] ");
    }
}
