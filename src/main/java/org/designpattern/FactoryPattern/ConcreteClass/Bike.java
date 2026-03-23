package org.designpattern.FactoryPattern.ConcreteClass;

import org.designpattern.AbstractFactoryPattern.Vehicle;

public class Bike implements Vehicle {
    @Override
    public void start() {
        System.out.println("Bike started!!");
    }

    @Override
    public void stop() {
        System.out.println("Bike stopped!!");
    }
}
