
package org.DSA.JunkLLD.SubscriptionModel.Strategy;

public class UPIPayment implements PaymentStrategy{
    String upi;

    public UPIPayment(String upi) {
        this.upi = upi;
    }

    @Override
    public void pay(double amount) {
        System.out.println("[UPI Amount Paid] "+ amount);
    }
}
