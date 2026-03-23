package org.designpattern.observer.ordernotification;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public String orderNo;
    public String orderName;
    public OrderStatus status;
    public List<NotificationObserver> observers;

    public Order(String orderNo, String orderName){
        this.orderNo=orderNo;
        this.orderName=orderName;
        this.observers= new ArrayList<>();
        this.status=OrderStatus.CREATED;
    }

    public void setOrderStatus(OrderStatus status){
        this.status= status;
        notifyObservers();
    }

    public void addObserver(NotificationObserver observer){
        observers.add(observer);
    }

    public void removeObserver(NotificationObserver observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(NotificationObserver ob: observers){
            try {
                ob.notify(orderNo, orderName, status);
            } catch (Exception e) {
                System.out.println("Failed to notify: " + ob.getClass().getSimpleName());
            }
        }
    }
}
