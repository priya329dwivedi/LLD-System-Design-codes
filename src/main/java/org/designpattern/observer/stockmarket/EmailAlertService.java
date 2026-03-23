package org.designpattern.observer.stockmarket;

public class EmailAlertService implements Observer {
    private String emailAddress;

    public EmailAlertService(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println("[EmailAlertService] Sending email to " + emailAddress + ": Stock " + stockSymbol + " price changed to $" + price);
    }
}
