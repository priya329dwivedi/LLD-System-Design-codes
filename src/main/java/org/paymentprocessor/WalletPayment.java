package org.paymentprocessor;

/**
 * Digital wallet payment strategy implementation.
 */
public class WalletPayment implements PaymentStrategy {

    private final String walletId;
    private double balance;

    public WalletPayment(String walletId, double initialBalance) {
        this.walletId = walletId;
        this.balance = initialBalance;
    }

    @Override
    public boolean process(double amount) {
        if (!hasEnoughBalance(amount)) {
            System.out.println("Wallet payment failed: Insufficient balance.");
            System.out.println("Current balance: $" + String.format("%.2f", balance));
            System.out.println("Required amount: $" + String.format("%.2f", amount));
            return false;
        }

        // Simulate payment processing
        System.out.println("Processing wallet payment...");
        System.out.println("Wallet ID: " + walletId);
        balance -= amount;
        System.out.println("Amount charged: $" + String.format("%.2f", amount));
        System.out.println("Remaining balance: $" + String.format("%.2f", balance));
        System.out.println("Payment successful!");

        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Digital Wallet";
    }

    public double getBalance() {
        return balance;
    }

    public void addFunds(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    private boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }
}
