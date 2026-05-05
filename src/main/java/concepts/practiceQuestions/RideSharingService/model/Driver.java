package concepts.practiceQuestions.RideSharingService.model;

public class Driver {
    private final String id;
    private final String name;

    public Driver(String id, String name) {
        this.id   = id;
        this.name = name;
    }

    public String getId()   { return id; }
    public String getName() { return name; }
}
