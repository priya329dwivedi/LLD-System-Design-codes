
package org.DSA.JunkLLD.SubscriptionModel.Observer;

public class Sms implements Notificationservice{
    String phoneNo;

    public Sms(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public void onSubscribe(String name, String planName) {
        System.out.println("[Notification sent via SMS] "+ name + " [with Plan ] " + planName);
    }
    @Override
    public void cancelSubscription(String subscriptionOId) {
        System.out.println("[Cancel Notification sent via Email] ");
    }
}
