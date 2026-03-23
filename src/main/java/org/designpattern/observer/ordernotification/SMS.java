package org.designpattern.observer.ordernotification;

public class SMS implements NotificationObserver{
    private Integer number;
    public SMS(Integer number){
        this.number=number;
    }
    @Override
    public void notify(String orderId, String orderName, OrderStatus status) {
        System.out.println("[SMS to " + number + "] Order #" + orderId + " (" + orderName + ") status changed to " + status);
    }

    @Override
    public void getName() {
        System.out.println("SMS Observer");
    }
}
