package org.designpattern.observer.ordernotification;

public class Email implements NotificationObserver{
    private String emailId;
    public Email(String emailId){
        this.emailId= emailId;
    }
    @Override
    public void notify(String orderId, String orderName, OrderStatus status) {
        System.out.println("[Email to " + emailId + "] Order #" + orderId + " (" + orderName + ") status changed to " + status);
    }

    @Override
    public void getName() {
        System.out.println("Email Observer");
    }
}
