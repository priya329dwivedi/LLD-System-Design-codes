package org.paymentprocessor;

/**
 * Strategy interface for processing payments.
 * Implementations handle specific payment method logic.
 */
public interface PaymentStrategy {

    /**
     * Process a payment for the given amount.
     *
     * @param amount the amount to charge
     * @return true if payment successful, false otherwise
     */
    boolean process(double amount);

    /**
     * Get the name of this payment method.
     *
     * @return payment method name
     */
    String getPaymentMethodName();
}