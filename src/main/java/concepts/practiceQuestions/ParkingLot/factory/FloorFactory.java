package concepts.practiceQuestions.ParkingLot.factory;

import concepts.practiceQuestions.ParkingLot.model.Floor;
import concepts.practiceQuestions.ParkingLot.model.Slot;
import concepts.practiceQuestions.ParkingLot.model.VehicleType;
import concepts.practiceQuestions.ParkingLot.strategy.SlotAssignmentStrategy;

// Creates a Floor with a fixed number of slots per vehicle type.
// Keeps Main clean — callers declare capacity, not individual slots.
public class FloorFactory {

    public static Floor create(int floorNumber, int bikeSlots, int carSlots, int truckSlots,
                               SlotAssignmentStrategy strategy) {
        Floor floor = new Floor(floorNumber, strategy);
        addSlots(floor, floorNumber, "B", VehicleType.BIKE,  bikeSlots);
        addSlots(floor, floorNumber, "C", VehicleType.CAR,   carSlots);
        addSlots(floor, floorNumber, "T", VehicleType.TRUCK, truckSlots);
        return floor;
    }

    private static void addSlots(Floor floor, int floorNumber, String prefix, VehicleType type, int count) {
        for (int i = 1; i <= count; i++) {
            floor.addSlot(new Slot("F" + floorNumber + "-" + prefix + i, type));
        }
    }
}
