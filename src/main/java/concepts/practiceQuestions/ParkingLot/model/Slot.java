package concepts.practiceQuestions.ParkingLot.model;

// A single parking space. Accepts only vehicles matching its type.
public class Slot {
    private final String      id;
    private final VehicleType type;
    private       boolean     occupied;

    public Slot(String id, VehicleType type) {
        this.id       = id;
        this.type     = type;
        this.occupied = false;
    }

    public boolean isAvailable() { return !occupied; }
    public void    occupy()      { occupied = true; }
    public void    free()        { occupied = false; }

    public String      getId()   { return id; }
    public VehicleType getType() { return type; }

    @Override
    public String toString() { return id + "[" + type + "]"; }
}
