package org.paymentprocessor;

/**
 * Checkout class that processes payments using the Strategy pattern.
 * This class depends only on the PaymentStrategy interface,
 * not on concrete payment implementations.
 */
public class Checkout {

    private final PaymentStrategy paymentStrategy;

    /**
     * Constructor with dependency injection.
     * Accepts any PaymentStrategy implementation.
     *
     * @param paymentStrategy the payment strategy to use
     */
    public Checkout(PaymentStrategy paymentStrategy) {
        if (paymentStrategy == null) {
            throw new IllegalArgumentException("Payment strategy cannot be null");
        }
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * Process payment for the given amount using the injected strategy.
     *
     * @param amount the amount to charge
     * @return true if payment was successful, false otherwise
     */
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount: must be greater than zero.");
            return false;
        }

        System.out.println("========================================");
        System.out.println("Starting checkout process...");
        System.out.println("Payment method: " + paymentStrategy.getPaymentMethodName());
        System.out.println("========================================");

        boolean result = paymentStrategy.process(amount);

        System.out.println("========================================");
        if (result) {
            System.out.println("Checkout completed successfully!");
        } else {
            System.out.println("Checkout failed. Please try again.");
        }
        System.out.println("========================================\n");

        return result;
    }

    /**
     * Get the payment method name being used.
     *
     * @return the payment method name
     */
    public String getPaymentMethodName() {
        return paymentStrategy.getPaymentMethodName();
    }
}
