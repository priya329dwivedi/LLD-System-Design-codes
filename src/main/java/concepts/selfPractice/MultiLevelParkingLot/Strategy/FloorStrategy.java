package concepts.selfPractice.MultiLevelParkingLot.Strategy;

import concepts.selfPractice.MultiLevelParkingLot.Model.Floor;
import concepts.selfPractice.MultiLevelParkingLot.Model.Slot;
import concepts.selfPractice.MultiLevelParkingLot.Model.VehicleType;

import java.util.List;

public interface FloorStrategy {
    Slot findSlot(List<Slot> slots, VehicleType type);
    void getAvailableSlots(List<Slot> slots);
}
