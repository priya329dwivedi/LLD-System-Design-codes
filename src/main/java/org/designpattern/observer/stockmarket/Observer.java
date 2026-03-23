package org.designpattern.observer.stockmarket;

public interface Observer {
    void update(String stockSymbol, double price);
}
