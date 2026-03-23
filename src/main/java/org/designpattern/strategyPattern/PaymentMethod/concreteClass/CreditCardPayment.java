package org.designpattern.strategyPattern.PaymentMethod.concreteClass;

import org.designpattern.strategyPattern.PaymentMethod.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void processPayment() {
        System.out.println("Credit card Payment Success!!");
    }
}
