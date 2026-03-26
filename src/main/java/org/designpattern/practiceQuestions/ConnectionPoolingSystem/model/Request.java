package org.designpattern.practiceQuestions.ConnectionPoolingSystem.model;

public class Request {
    private final String id;
    private final String repositoryId;
    private final String url;
    private final long submittedAt;

    public Request(String id, String repositoryId, String url) {
        this.id = id;
        this.repositoryId = repositoryId;
        this.url = url;
        this.submittedAt = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public String getRepositoryId() { return repositoryId; }
    public String getUrl() { return url; }
    public long getSubmittedAt() { return submittedAt; }

    @Override
    public String toString() {
        return "Request[" + id + " -> " + repositoryId + url + "]";
    }
}
