package org.designpattern.AbstractFactoryPattern.ConcreteClass;

import org.designpattern.AbstractFactoryPattern.Vehicle;

public class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car started!!");
    }

    @Override
    public void stop() {
        System.out.println("Car stopped!!");
    }
}
