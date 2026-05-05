package concepts.practiceQuestions.ParkingLot.strategy;

import concepts.practiceQuestions.ParkingLot.model.Slot;
import concepts.practiceQuestions.ParkingLot.model.VehicleType;

import java.util.List;

// Returns the first available slot that matches the vehicle type.
public class FirstFitStrategy implements SlotAssignmentStrategy {

    @Override
    public Slot findSlot(List<Slot> slots, VehicleType type) {
        for (Slot slot : slots) {
            if (slot.getType() == type && slot.isAvailable()) return slot;
        }
        return null;
    }
}
