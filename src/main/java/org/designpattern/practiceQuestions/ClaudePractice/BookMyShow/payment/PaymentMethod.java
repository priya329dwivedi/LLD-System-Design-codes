package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.payment;

public interface PaymentMethod {
    boolean pay(double amount);
    String getName();
}
