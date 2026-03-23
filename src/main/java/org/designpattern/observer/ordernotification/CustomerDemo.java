package org.designpattern.observer.ordernotification;

public class CustomerDemo {
    public static void main(String[] args) {
        Order order= new Order("123", "Zepto");
        NotificationObserver email= new Email("priya@gmail.com");
        NotificationObserver sms= new SMS(1234567890);
        NotificationObserver pushNotification= new PushNotification();
        order.addObserver(email);
        order.addObserver(sms);
        order.addObserver(pushNotification);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setOrderStatus(OrderStatus.SHIPPED);
        order.setOrderStatus(OrderStatus.CANCELLED);

        System.out.println("********************************************************");

        Order orderTwo= new Order("124", "Swiggy");
        orderTwo.addObserver(email);
        orderTwo.addObserver(sms);
        orderTwo.addObserver(pushNotification);
        orderTwo.setOrderStatus(OrderStatus.CREATED);
        orderTwo.setOrderStatus(OrderStatus.SHIPPED);
        orderTwo.setOrderStatus(OrderStatus.DELIVERED);

        System.out.println("**************Demo Ended***********************************");
    }

}
