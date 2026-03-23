package org.designpattern.strategyPattern.PaymentMethod.concreteClass;

import org.designpattern.strategyPattern.PaymentMethod.PaymentStrategy;

public class PayPalPayment implements PaymentStrategy {
    @Override
    public void processPayment() {
        System.out.println("PayPal payment success!!");
    }
}
