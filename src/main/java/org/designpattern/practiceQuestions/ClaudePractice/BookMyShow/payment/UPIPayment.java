package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.payment;

public class UPIPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("[UPI] Paid Rs " + amount);
        return true;
    }

    @Override
    public String getName() {
        return "UPI";
    }
}
