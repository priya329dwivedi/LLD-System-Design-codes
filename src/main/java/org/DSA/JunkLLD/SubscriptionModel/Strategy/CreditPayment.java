
package org.DSA.JunkLLD.SubscriptionModel.Strategy;

public class CreditPayment implements PaymentStrategy{
    String cardNumber;

    public CreditPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("[Credit Amount Paid ] "+ amount);
    }
}
