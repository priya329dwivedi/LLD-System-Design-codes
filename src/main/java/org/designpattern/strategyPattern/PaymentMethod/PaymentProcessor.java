package org.designpattern.strategyPattern.PaymentMethod;

public class PaymentProcessor {
    PaymentStrategy strategy;
    public PaymentProcessor(PaymentStrategy strategy){
        this.strategy= strategy;
    }
    public void processPayment(){
        strategy.processPayment();
    }
    public void setPaymentProcessor(PaymentStrategy strategy){
        this.strategy= strategy;
    }
}
