package org.designpattern.practiceQuestions.ParkingLot;

import org.designpattern.practiceQuestions.ParkingLot.model.ParkingSlot;
import org.designpattern.practiceQuestions.ParkingLot.model.SlotSize;
import org.designpattern.practiceQuestions.ParkingLot.model.Vehicle;
import org.designpattern.practiceQuestions.ParkingLot.model.VehicleType;
import org.designpattern.practiceQuestions.ParkingLot.store.ParkingStore;

public class Main {
    public static void main(String[] args) {
        ParkingStore store = new ParkingStore();
        ParkingService service = new ParkingService(store);

        // Setup: 3 small, 3 medium, 2 large slots
        for (int i = 1; i <= 3; i++) store.addSlot(new ParkingSlot("S" + i, SlotSize.SMALL));
        for (int i = 1; i <= 3; i++) store.addSlot(new ParkingSlot("M" + i, SlotSize.MEDIUM));
        for (int i = 1; i <= 2; i++) store.addSlot(new ParkingSlot("L" + i, SlotSize.LARGE));

        service.displayAvailability();

        // Park vehicles
        service.parkVehicle(new Vehicle("BIKE-01", VehicleType.BIKE));
        service.parkVehicle(new Vehicle("CAR-01",  VehicleType.CAR));
        service.parkVehicle(new Vehicle("CAR-02",  VehicleType.CAR));
        service.parkVehicle(new Vehicle("TRUCK-01", VehicleType.TRUCK));

        service.displayAvailability();

        // Exit vehicles
        service.exitVehicle("CAR-01");
        service.exitVehicle("BIKE-01");

        service.displayAvailability();
    }
}
