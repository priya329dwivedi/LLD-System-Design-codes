package concepts.selfPractice.MultiLevelParkingLot.Factory;

import concepts.selfPractice.MultiLevelParkingLot.Model.Floor;
import concepts.selfPractice.MultiLevelParkingLot.Model.Slot;
import concepts.selfPractice.MultiLevelParkingLot.Model.VehicleType;
import concepts.selfPractice.MultiLevelParkingLot.Strategy.FloorStrategy;

public class FloorFactory {
    public static Floor createFloor(int floorNumber, int bike, int car, int truck, FloorStrategy strategy){
        Floor floor = new Floor(floorNumber,strategy);
        addSlot(floor,floorNumber,'B',bike, VehicleType.BIKE);
        addSlot(floor,floorNumber,'C',car,VehicleType.CAR);
        addSlot(floor,floorNumber,'T',truck,VehicleType.TRUCK);
        return floor;
    }

    private static void addSlot(Floor floor,int floorNumber, char prefix, int slot,VehicleType type) {
        for(int i=0;i<slot;i++){
            floor.addSlots(new Slot("F" + floorNumber +"-"+ prefix+i,type));
        }

    }
}
