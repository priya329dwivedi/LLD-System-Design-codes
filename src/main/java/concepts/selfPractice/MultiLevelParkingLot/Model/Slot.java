package concepts.selfPractice.MultiLevelParkingLot.Model;

import lombok.Getter;

@Getter
public class Slot {
    private final String slotId;
    private final VehicleType vehicleType;
    private boolean occupied;

    public Slot(String slotId, VehicleType vehicleType) {
        this.slotId = slotId;
        this.vehicleType = vehicleType;
    }

    public boolean isAvailable(){
        return !occupied;
    }

    public void occupy(){
        this.occupied=true;
    }
    public void free(){
        this.occupied=false;
    }
}
