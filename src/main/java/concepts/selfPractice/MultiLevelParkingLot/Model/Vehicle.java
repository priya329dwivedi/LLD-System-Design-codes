package concepts.selfPractice.MultiLevelParkingLot.Model;

import lombok.Getter;

@Getter
public class Vehicle {
    String vehicleId;
    VehicleType type;

    public Vehicle(String vehicleId, VehicleType type) {
        this.vehicleId = vehicleId;
        this.type = type;
    }
}
