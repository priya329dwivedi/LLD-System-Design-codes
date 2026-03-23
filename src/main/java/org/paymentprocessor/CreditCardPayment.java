package org.paymentprocessor;

/**
 * Credit card payment strategy implementation.
 */
public class CreditCardPayment implements PaymentStrategy {

    private final String cardNumber;
    private final String cardHolderName;
    private final String expiryDate;
    private final String cvv;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean process(double amount) {
        if (!validateCard()) {
            System.out.println("Credit card validation failed.");
            return false;
        }

        // Simulate payment processing
        System.out.println("Processing credit card payment...");
        System.out.println("Card: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Amount charged: $" + String.format("%.2f", amount));
        System.out.println("Payment successful!");

        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }

    private boolean validateCard() {
        return cardNumber != null && cardNumber.length() >= 13
                && cvv != null && cvv.length() >= 3
                && expiryDate != null && !expiryDate.isEmpty();
    }
}