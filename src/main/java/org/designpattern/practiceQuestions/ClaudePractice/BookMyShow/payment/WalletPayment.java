package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.payment;

public class WalletPayment implements PaymentMethod {
    private double balance;

    public WalletPayment(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        if (balance < amount) {
            System.out.println("[Wallet] Insufficient balance. Have Rs " + balance + ", need Rs " + amount);
            return false;
        }
        balance -= amount;
        System.out.println("[Wallet] Paid Rs " + amount + ". Remaining balance: Rs " + balance);
        return true;
    }

    @Override
    public String getName() {
        return "Wallet";
    }
}
