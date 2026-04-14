package org.designpattern.practiceQuestions.ParkingLot.model;

public class Vehicle {
    public String licensePlate;
    public VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }
}
