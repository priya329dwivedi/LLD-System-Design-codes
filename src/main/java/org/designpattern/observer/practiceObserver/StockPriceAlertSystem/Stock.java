/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.observer.practiceObserver.StockPriceAlertSystem;

import java.util.ArrayList;
import java.util.List;

public class Stock implements StockSubject {
    private String stockSymbol;
    private double price;
    private List<StockObserver> observers;

    public Stock(String stockSymbol, double price) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.observers = new ArrayList<>();
    }

    @Override
    public void subscribe(StockObserver observer) {
        if(observer != null && !observers.contains(observer)){
            observers.add(observer);
            System.out.println("Observer subscribed to " + stockSymbol);
        }
    }

    @Override
    public void unsubscribe(StockObserver observer) {
        if(observers.remove(observer)){
            System.out.println("Observer unsubscribed from " + stockSymbol);
        }
    }

    @Override
    public void notifyObservers() {
        for(StockObserver observer : observers){
            observer.update(stockSymbol, price);
        }
    }

    public void setPrice(double newPrice) {
        System.out.println("\n--- " + stockSymbol + " price changed: $" + this.price + " -> $" + newPrice + " ---");
        this.price = newPrice;
        notifyObservers();
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public double getPrice() {
        return price;
    }
}
