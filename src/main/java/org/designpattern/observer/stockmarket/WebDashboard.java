package org.designpattern.observer.stockmarket;

public class WebDashboard implements Observer {
    private String dashboardId;

    public WebDashboard(String dashboardId) {
        this.dashboardId = dashboardId;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println("[WebDashboard - " + dashboardId + "] Updating chart: " + stockSymbol + " = $" + price);
    }
}
