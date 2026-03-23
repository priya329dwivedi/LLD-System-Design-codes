package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.payment;

public class PaymentFactory {
    public static PaymentMethod createPayment(String type) {
        switch (type) {
            case "CreditCard":
                return new CreditCardPayment();
            case "UPI":
                return new UPIPayment();
            case "Wallet":
                return new WalletPayment(500);
            default:
                throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }
}
