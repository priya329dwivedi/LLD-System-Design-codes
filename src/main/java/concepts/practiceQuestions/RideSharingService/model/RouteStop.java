package concepts.practiceQuestions.RideSharingService.model;

// A single pickup/dropoff point on a route, ordered by sequence.
public class RouteStop {
    private final String id;
    private final String locationName;
    private final int    sequence; // lower = picked up earlier

    public RouteStop(String id, String locationName, int sequence) {
        this.id           = id;
        this.locationName = locationName;
        this.sequence     = sequence;
    }

    public String getId()           { return id; }
    public String getLocationName() { return locationName; }
    public int    getSequence()     { return sequence; }
}
