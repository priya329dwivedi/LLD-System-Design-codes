package org.designpattern.practiceQuestions.ConnectionPoolingSystem.service;

import org.designpattern.practiceQuestions.ConnectionPoolingSystem.observer.PoolObserver;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.pool.ConnectionPool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Singleton daemon thread that periodically cleans up idle connections across all pools.
 * In real code, this uses WeakReferences so GC'd pools are auto-removed.
 */
public class PoolMonitor {
    private static PoolMonitor instance;

    private final CopyOnWriteArrayList<ConnectionPool> pools;
    private final List<PoolObserver> observers;
    private final long cleanupIntervalMs;
    private volatile boolean running;
    private Thread monitorThread;

    private PoolMonitor(long cleanupIntervalMs, List<PoolObserver> observers) {
        this.pools = new CopyOnWriteArrayList<>();
        this.observers = observers;
        this.cleanupIntervalMs = cleanupIntervalMs;
        this.running = false;
    }

    public static synchronized PoolMonitor getInstance(long cleanupIntervalMs, List<PoolObserver> observers) {
        if (instance == null) {
            instance = new PoolMonitor(cleanupIntervalMs, observers);
        }
        return instance;
    }

    public void registerPool(ConnectionPool pool) {
        pools.add(pool);
    }

    public void start() {
        if (running) return;
        running = true;
        monitorThread = new Thread(() -> {
            System.out.println("[PoolMonitor] Daemon started — cleanup every " + cleanupIntervalMs + "ms");
            while (running) {
                try {
                    Thread.sleep(cleanupIntervalMs);
                    cleanup();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "PoolMonitor-Daemon");
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    private void cleanup() {
        for (ConnectionPool pool : pools) {
            if (pool.isShutdown()) continue;
            int cleaned = pool.cleanupIdleConnections();
            if (cleaned > 0) {
                for (PoolObserver observer : observers) {
                    observer.onIdleCleanup(pool.getRepositoryId(), cleaned);
                }
            }
        }
    }

    // Graceful shutdown: immediate if no active, else wait up to forceTimeoutMs
    public void shutdownPool(ConnectionPool pool, long forceTimeoutMs) {
        if (pool.canShutdownGracefully()) {
            pool.shutdown();
            notifyShutdown(pool.getRepositoryId());
            return;
        }

        // Poll until active connections drain or timeout
        System.out.println("[PoolMonitor] Waiting for in-flight requests on " + pool.getRepositoryId() + "...");
        long deadline = System.currentTimeMillis() + forceTimeoutMs;
        while (System.currentTimeMillis() < deadline) {
            if (pool.canShutdownGracefully()) {
                pool.shutdown();
                notifyShutdown(pool.getRepositoryId());
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Force shutdown after timeout
        System.out.println("[PoolMonitor] Force shutting down " + pool.getRepositoryId());
        pool.shutdown();
        notifyShutdown(pool.getRepositoryId());
    }

    public void stop() {
        running = false;
        if (monitorThread != null) {
            monitorThread.interrupt();
        }
    }

    private void notifyShutdown(String repositoryId) {
        for (PoolObserver observer : observers) {
            observer.onPoolShutdown(repositoryId);
        }
    }
}
