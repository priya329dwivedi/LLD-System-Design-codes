package concepts.selfPractice.MultiLevelParkingLot.Strategy;

import concepts.selfPractice.MultiLevelParkingLot.Model.Slot;
import concepts.selfPractice.MultiLevelParkingLot.Model.VehicleType;

import java.util.List;

public class FindFirst implements FloorStrategy{

    @Override
    public Slot findSlot(List<Slot> slots, VehicleType type) {
        for(Slot slot: slots){
            if(slot.getVehicleType().equals(type) && slot.isAvailable()){
                return slot;
            }
        }
        return null;
    }
    @Override
    public void getAvailableSlots(List<Slot> slots){
        int bike=0;
        int car=0;
        int truck=0;
        for(Slot slot: slots){
            if(slot.isAvailable()){
                System.out.printf("  SlotId: [%s] Type: [%s]%n", slot.getSlotId(), slot.getVehicleType());
                if(slot.getVehicleType().equals(VehicleType.BIKE))  bike++;
                if(slot.getVehicleType().equals(VehicleType.CAR))   car++;
                if(slot.getVehicleType().equals(VehicleType.TRUCK)) truck++;
            }
        }
        System.out.printf("Available Spaces BIKES:- [%d] CAR:- [%d] TRUCK:- [%d] %n",bike,car,truck);
    }
}
