package concepts.selfPractice.MultiLevelParkingLot.Model;

import concepts.selfPractice.MultiLevelParkingLot.Strategy.FloorStrategy;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Floor {
    private final int floorNumber;
    private final FloorStrategy floorStrategy;
    private final List<Slot> slots;

    public Floor(int floorNumber, FloorStrategy floorStrategy) {
        this.floorNumber = floorNumber;
        this.floorStrategy = floorStrategy;
        this.slots = new ArrayList<>();
    }

    public void addSlots(Slot slot){
        slots.add(slot);
    }

    public Slot findSlot(VehicleType type){
        return floorStrategy.findSlot(slots,type);
    }

    public void getAvailableSlots(){
        floorStrategy.getAvailableSlots(slots);
    }
}
