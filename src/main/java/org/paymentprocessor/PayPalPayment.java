package org.paymentprocessor;

/**
 * PayPal payment strategy implementation.
 */
public class PayPalPayment implements PaymentStrategy {

    private final String email;
    private final String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean process(double amount) {
        if (!authenticate()) {
            System.out.println("PayPal authentication failed.");
            return false;
        }

        // Simulate payment processing
        System.out.println("Processing PayPal payment...");
        System.out.println("Account: " + maskEmail(email));
        System.out.println("Amount charged: $" + String.format("%.2f", amount));
        System.out.println("Payment successful!");

        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }

    private boolean authenticate() {
        return email != null && email.contains("@")
                && password != null && !password.isEmpty();
    }

    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 2) {
            return email;
        }
        return email.substring(0, 2) + "***" + email.substring(atIndex);
    }
}
