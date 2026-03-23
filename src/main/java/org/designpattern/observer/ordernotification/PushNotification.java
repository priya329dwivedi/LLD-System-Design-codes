package org.designpattern.observer.ordernotification;

public class PushNotification implements NotificationObserver{
    @Override
    public void notify(String orderId, String orderName, OrderStatus status) {
        System.out.println("[Push Notification] Order #" + orderId + " (" + orderName + ") status changed to " + status);
    }

    @Override
    public void getName() {
        System.out.println("Push Notification");
    }
}
