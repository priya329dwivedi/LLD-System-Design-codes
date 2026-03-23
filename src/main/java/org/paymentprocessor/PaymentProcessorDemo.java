package org.paymentprocessor;

/**
 * Demo class to showcase the Payment Processor Strategy Pattern implementation.
 */
public class PaymentProcessorDemo {

    public static void main(String[] args) {
        double orderAmount = 99.99;

        System.out.println("=== PAYMENT PROCESSOR DEMO ===\n");
        System.out.println("Order Amount: $" + String.format("%.2f", orderAmount) + "\n");

        // Demo 1: Credit Card Payment
        System.out.println("--- Test 1: Credit Card Payment ---");
        PaymentStrategy creditCard = new CreditCardPayment(
                "4111111111111234",
                "John Doe",
                "12/25",
                "123"
        );
        Checkout creditCardCheckout = new Checkout(creditCard);
        creditCardCheckout.processPayment(orderAmount);

        // Demo 2: PayPal Payment
        System.out.println("--- Test 2: PayPal Payment ---");
        PaymentStrategy paypal = new PayPalPayment(
                "john.doe@email.com",
                "securePassword123"
        );
        Checkout paypalCheckout = new Checkout(paypal);
        paypalCheckout.processPayment(orderAmount);

        // Demo 3: Wallet Payment with sufficient balance
        System.out.println("--- Test 3: Wallet Payment (Sufficient Balance) ---");
        PaymentStrategy wallet = new WalletPayment("WALLET-001", 200.00);
        Checkout walletCheckout = new Checkout(wallet);
        walletCheckout.processPayment(orderAmount);

        // Demo 4: Wallet Payment with insufficient balance
        System.out.println("--- Test 4: Wallet Payment (Insufficient Balance) ---");
        PaymentStrategy lowBalanceWallet = new WalletPayment("WALLET-002", 50.00);
        Checkout lowBalanceCheckout = new Checkout(lowBalanceWallet);
        lowBalanceCheckout.processPayment(orderAmount);

        // Demo 5: Switching payment strategies at runtime
        System.out.println("--- Test 5: Runtime Strategy Switching ---");
        System.out.println("Demonstrating how the same Checkout process");
        System.out.println("can work with different payment strategies:\n");

        PaymentStrategy[] strategies = {
                new CreditCardPayment("5500000000000004", "Jane Smith", "06/26", "456"),
                new PayPalPayment("jane@company.com", "password"),
                new WalletPayment("WALLET-003", 150.00)
        };

        for (PaymentStrategy strategy : strategies) {
            Checkout checkout = new Checkout(strategy);
            checkout.processPayment(25.00);
        }

        System.out.println("=== DEMO COMPLETE ===");
    }
}
