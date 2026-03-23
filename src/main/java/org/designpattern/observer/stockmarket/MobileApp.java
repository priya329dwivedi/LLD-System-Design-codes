package org.designpattern.observer.stockmarket;

public class MobileApp implements Observer {
    private String appName;

    public MobileApp(String appName) {
        this.appName = appName;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println("[MobileApp - " + appName + "] Push Notification: " + stockSymbol + " is now $" + price);
    }
}
