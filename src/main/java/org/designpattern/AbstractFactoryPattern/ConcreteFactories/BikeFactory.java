package org.designpattern.AbstractFactoryPattern.ConcreteFactories;

import org.designpattern.AbstractFactoryPattern.Vehicle;
import org.designpattern.AbstractFactoryPattern.VehicleFactory;
import org.designpattern.AbstractFactoryPattern.ConcreteClass.Bike;

public class BikeFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Bike();
    }
}
