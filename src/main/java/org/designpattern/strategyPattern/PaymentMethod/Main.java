package org.designpattern.strategyPattern.PaymentMethod;

import org.designpattern.strategyPattern.PaymentMethod.concreteClass.CreditCardPayment;
import org.designpattern.strategyPattern.PaymentMethod.concreteClass.PayPalPayment;

public class Main {
    public static void main(String[] args) {
        CreditCardPayment cardPayment = new CreditCardPayment();
        PayPalPayment payPalPayment = new PayPalPayment();
        PaymentProcessor paymentProcessor = new PaymentProcessor(cardPayment);
        paymentProcessor.processPayment();
        paymentProcessor.setPaymentProcessor(payPalPayment);
        paymentProcessor.processPayment();
    }
}
