package org.designpattern.observer.stockmarket;

import java.util.ArrayList;
import java.util.List;

public class StockMarket implements Subject {
    private List<Observer> observers;
    private String stockSymbol;
    private double price;

    public StockMarket() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer registered: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.remove(observer)) {
            System.out.println("Observer removed: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockSymbol, price);
        }
    }

    public void setStockPrice(String stockSymbol, double price) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        System.out.println("\n--- Stock Price Changed: " + stockSymbol + " = $" + price + " ---");
        notifyObservers();
    }

    public int getObserverCount() {
        return observers.size();
    }
}
