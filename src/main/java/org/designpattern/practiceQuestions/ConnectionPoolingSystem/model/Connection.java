package org.designpattern.practiceQuestions.ConnectionPoolingSystem.model;

public class Connection {
    private final String id;
    private final String repositoryId;
    private ConnectionStatus status;
    private final long createdAt;
    private long lastUsedAt;

    public Connection(String id, String repositoryId) {
        this.id = id;
        this.repositoryId = repositoryId;
        this.status = ConnectionStatus.IDLE;
        this.createdAt = System.currentTimeMillis();
        this.lastUsedAt = this.createdAt;
    }

    public String execute(Request request) {
        try {
            // Simulate HTTP call latency (100-500ms)
            Thread.sleep(100 + (long) (Math.random() * 400));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "200 OK";
    }

    public void markActive() {
        this.status = ConnectionStatus.ACTIVE;
        this.lastUsedAt = System.currentTimeMillis();
    }

    public void markIdle() {
        this.status = ConnectionStatus.IDLE;
        this.lastUsedAt = System.currentTimeMillis();
    }

    public void close() {
        this.status = ConnectionStatus.CLOSED;
    }

    public boolean isIdleExpired(long idleTimeoutMs) {
        return status == ConnectionStatus.IDLE
                && (System.currentTimeMillis() - lastUsedAt) > idleTimeoutMs;
    }

    public String getId() { return id; }
    public String getRepositoryId() { return repositoryId; }
    public ConnectionStatus getStatus() { return status; }
    public long getLastUsedAt() { return lastUsedAt; }

    @Override
    public String toString() {
        return "Conn[" + id + "|" + repositoryId + "|" + status + "]";
    }
}
