package org.designpattern.practiceQuestions.SubscriptionPlatform.strategy;

public class UPIPayment implements PaymentStrategy {
    String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("[Payment] $" + amount + " charged via UPI: " + upiId);
        return true;
    }
}
