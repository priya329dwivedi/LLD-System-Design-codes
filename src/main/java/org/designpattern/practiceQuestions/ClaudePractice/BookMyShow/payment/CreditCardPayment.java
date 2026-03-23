package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.payment;

public class CreditCardPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("[CreditCard] Paid Rs " + amount);
        return true;
    }

    @Override
    public String getName() {
        return "CreditCard";
    }
}
