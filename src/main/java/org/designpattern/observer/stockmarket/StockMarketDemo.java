package org.designpattern.observer.stockmarket;

public class StockMarketDemo {
    public static void main(String[] args) {
        // Create the subject (publisher)
        StockMarket stockMarket = new StockMarket();

        // Create observers (subscribers)
        Observer mobileApp = new MobileApp("StockTracker Pro");
        Observer webDashboard = new WebDashboard("Dashboard-001");
        Observer emailService = new EmailAlertService("investor@example.com");

        System.out.println("=== Registering Observers ===");
        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webDashboard);
        stockMarket.registerObserver(emailService);

        System.out.println("\nTotal observers: " + stockMarket.getObserverCount());

        // Simulate stock price changes - all observers get notified
        stockMarket.setStockPrice("AAPL", 150.25);
        stockMarket.setStockPrice("GOOGL", 2800.50);

        // Remove an observer
        System.out.println("\n=== Removing WebDashboard Observer ===");
        stockMarket.removeObserver(webDashboard);
        System.out.println("Total observers: " + stockMarket.getObserverCount());

        // Price change after removal - only remaining observers notified
        stockMarket.setStockPrice("AAPL", 152.00);

        // Add a new observer dynamically
        System.out.println("\n=== Adding New Observer ===");
        Observer anotherMobileApp = new MobileApp("QuickStock");
        stockMarket.registerObserver(anotherMobileApp);
        System.out.println("Total observers: " + stockMarket.getObserverCount());

        // All current observers get notified
        stockMarket.setStockPrice("MSFT", 310.75);
    }
}
