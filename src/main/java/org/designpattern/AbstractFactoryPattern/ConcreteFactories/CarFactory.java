package org.designpattern.AbstractFactoryPattern.ConcreteFactories;

import org.designpattern.AbstractFactoryPattern.ConcreteClass.Car;
import org.designpattern.AbstractFactoryPattern.Vehicle;
import org.designpattern.AbstractFactoryPattern.VehicleFactory;

public class CarFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}
