
package org.DSA.JunkLLD.SubscriptionModel.Observer;

public interface Notificationservice {
    public void onSubscribe(String name, String planName);
    public void cancelSubscription(String subscriptionOId);
}
