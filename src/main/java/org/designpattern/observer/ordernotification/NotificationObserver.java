package org.designpattern.observer.ordernotification;

public interface NotificationObserver {
    void notify(String orderId, String orderName, OrderStatus status);
    void getName();
}
