package org.designpattern.AbstractFactoryPattern;

import org.designpattern.AbstractFactoryPattern.ConcreteClass.Truck;
import org.designpattern.AbstractFactoryPattern.ConcreteFactories.BikeFactory;
import org.designpattern.AbstractFactoryPattern.ConcreteFactories.CarFactory;
import org.designpattern.AbstractFactoryPattern.ConcreteFactories.TruckFactory;

public class Main {
    public static void main(String[] args) {
        VehicleFactory factory = new TruckFactory();
        Vehicle vehicle = factory.createVehicle();
        vehicle.start();
        vehicle.stop();
        VehicleFactory factory1 = new CarFactory();
        Vehicle car = factory1.createVehicle();
        car.stop();
        car.start();
    }
}
