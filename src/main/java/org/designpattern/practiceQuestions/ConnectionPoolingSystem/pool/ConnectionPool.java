package org.designpattern.practiceQuestions.ConnectionPoolingSystem.pool;

import org.designpattern.practiceQuestions.ConnectionPoolingSystem.model.Connection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    private final String repositoryId;
    private final int maxSize;
    private final long idleTimeoutMs;
    private final ArrayBlockingQueue<Connection> idleQueue;
    private final CopyOnWriteArrayList<Connection> allConnections;
    private final AtomicInteger activeCount;
    private volatile boolean shutdown;

    public ConnectionPool(String repositoryId, int maxSize, long idleTimeoutMs) {
        this.repositoryId = repositoryId;
        this.maxSize = maxSize;
        this.idleTimeoutMs = idleTimeoutMs;
        this.idleQueue = new ArrayBlockingQueue<>(maxSize);
        this.allConnections = new CopyOnWriteArrayList<>();
        this.activeCount = new AtomicInteger(0);
        this.shutdown = false;
    }

    // acquire: get idle connection, or create new, or block until one freed
    public Connection acquire(long timeoutMs) throws InterruptedException {
        if (shutdown) {
            throw new IllegalStateException("Pool is shut down for repository: " + repositoryId);
        }

        // 1. Try to get idle connection (non-blocking)
        Connection conn = idleQueue.poll();
        if (conn != null) {
            conn.markActive();
            activeCount.incrementAndGet();
            return conn;
        }

        // 2. No idle connection — create new if pool has room
        synchronized (this) {
            if (allConnections.size() < maxSize) {
                conn = createConnection();
                conn.markActive();
                activeCount.incrementAndGet();
                return conn;
            }
        }

        // 3. Pool at max — BLOCK until someone releases a connection
        conn = idleQueue.poll(timeoutMs, TimeUnit.MILLISECONDS);
        if (conn == null) {
            return null;  // timeout — pool exhausted
        }
        conn.markActive();
        activeCount.incrementAndGet();
        return conn;
    }

    // release: return connection to idle queue
    public void release(Connection conn) {
        conn.markIdle();
        activeCount.decrementAndGet();
        if (!shutdown) {
            idleQueue.offer(conn);  // wakes up any blocked acquire()
        }
    }

    // cleanup: close connections idle longer than timeout
    public int cleanupIdleConnections() {
        int cleaned = 0;
        Iterator<Connection> it = idleQueue.iterator();
        List<Connection> toRemove = new ArrayList<>();

        for (Connection conn : idleQueue) {
            if (conn.isIdleExpired(idleTimeoutMs)) {
                toRemove.add(conn);
            }
        }

        for (Connection conn : toRemove) {
            if (idleQueue.remove(conn)) {
                conn.close();
                allConnections.remove(conn);
                cleaned++;
            }
        }
        return cleaned;
    }

    // shutdown: close all connections
    public void shutdown() {
        this.shutdown = true;
        for (Connection conn : idleQueue) {
            conn.close();
        }
        idleQueue.clear();
        allConnections.clear();
    }

    public boolean canShutdownGracefully() {
        return activeCount.get() == 0;
    }

    private Connection createConnection() {
        String connId = UUID.randomUUID().toString().substring(0, 6);
        Connection conn = new Connection(connId, repositoryId);
        allConnections.add(conn);
        return conn;
    }

    public String getRepositoryId() { return repositoryId; }
    public int getActiveCount() { return activeCount.get(); }
    public int getIdleCount() { return idleQueue.size(); }
    public int getTotalCount() { return allConnections.size(); }
    public boolean isShutdown() { return shutdown; }

    public String getStats() {
        return "[" + repositoryId + "] total=" + getTotalCount()
                + " active=" + getActiveCount()
                + " idle=" + getIdleCount();
    }
}
