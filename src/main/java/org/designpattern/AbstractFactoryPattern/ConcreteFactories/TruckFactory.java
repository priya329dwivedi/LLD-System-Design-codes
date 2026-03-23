package org.designpattern.AbstractFactoryPattern.ConcreteFactories;

import org.designpattern.AbstractFactoryPattern.ConcreteClass.Truck;
import org.designpattern.AbstractFactoryPattern.Vehicle;
import org.designpattern.AbstractFactoryPattern.VehicleFactory;

public class TruckFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Truck();
    }
}
