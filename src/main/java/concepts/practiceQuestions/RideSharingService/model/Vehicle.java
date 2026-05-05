package concepts.practiceQuestions.RideSharingService.model;

public class Vehicle {
    private final String id;
    private final String type;     // e.g. "SUV", "Minibus"
    private final int    capacity; // total seats

    public Vehicle(String id, String type, int capacity) {
        this.id       = id;
        this.type     = type;
        this.capacity = capacity;
    }

    public String getId()      { return id; }
    public String getType()    { return type; }
    public int    getCapacity(){ return capacity; }
}
