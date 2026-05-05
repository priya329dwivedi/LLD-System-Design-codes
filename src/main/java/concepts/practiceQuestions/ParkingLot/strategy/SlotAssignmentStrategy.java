package concepts.practiceQuestions.ParkingLot.strategy;

import concepts.practiceQuestions.ParkingLot.model.Slot;
import concepts.practiceQuestions.ParkingLot.model.VehicleType;

import java.util.List;

public interface SlotAssignmentStrategy {
    Slot findSlot(List<Slot> slots, VehicleType type);
}
