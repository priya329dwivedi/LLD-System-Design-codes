package org.designpattern.AbstractFactoryPattern.ConcreteClass;

import org.designpattern.AbstractFactoryPattern.Vehicle;

public class Truck implements Vehicle {
    @Override
    public void start() {
        System.out.println("Truck started!!");
    }

    @Override
    public void stop() {
        System.out.println("Truck stopped!!");
    }
}
