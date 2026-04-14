package org.designpattern.practiceQuestions.SubscriptionPlatform.strategy;

public class CreditCardPayment implements PaymentStrategy {
    String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("[Payment] $" + amount + " charged to card ending " + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }
}
