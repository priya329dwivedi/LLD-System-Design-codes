package org.designpattern.practiceQuestions.ConnectionPoolingSystem.model;

public class Repository {
    private final String id;
    private final String name;
    private final String baseUrl;
    private final int maxConnections;
    private final double requestsPerSecond;  // 0 = no limit

    public Repository(String id, String name, String baseUrl, int maxConnections, double requestsPerSecond) {
        this.id = id;
        this.name = name;
        this.baseUrl = baseUrl;
        this.maxConnections = maxConnections;
        this.requestsPerSecond = requestsPerSecond;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getBaseUrl() { return baseUrl; }
    public int getMaxConnections() { return maxConnections; }
    public double getRequestsPerSecond() { return requestsPerSecond; }

    @Override
    public String toString() {
        return name + " (" + baseUrl + ")";
    }
}
