package org.designpattern.practiceQuestions.ConnectionPoolingSystem.service;

import org.designpattern.practiceQuestions.ConnectionPoolingSystem.model.Connection;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.model.Repository;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.model.Request;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.observer.PoolObserver;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.pool.ConnectionPool;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.pool.ConnectionPoolFactory;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.strategy.RateLimitStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Singleton service.
 * - Incoming requests go into a LinkedBlockingQueue (producer-consumer).
 * - Worker threads take from queue → throttle → acquire connection → execute → release.
 */
public class RequestProcessor {
    private static RequestProcessor instance;

    private final LinkedBlockingQueue<Request> requestQueue;
    private final Map<String, ConnectionPool> pools;
    private final Map<String, RateLimitStrategy> throttles;
    private final Map<String, Repository> repositories;
    private final List<PoolObserver> observers;
    private final PoolMonitor poolMonitor;
    private ExecutorService workers;
    private volatile boolean running;

    private RequestProcessor() {
        this.requestQueue = new LinkedBlockingQueue<>(500);  // bounded — backpressure
        this.pools = new ConcurrentHashMap<>();
        this.throttles = new ConcurrentHashMap<>();
        this.repositories = new ConcurrentHashMap<>();
        this.observers = new ArrayList<>();
        this.poolMonitor = PoolMonitor.getInstance(5000, observers);  // cleanup every 5s for demo
        this.running = false;
    }

    public static synchronized RequestProcessor getInstance() {
        if (instance == null) {
            instance = new RequestProcessor();
        }
        return instance;
    }

    public void addObserver(PoolObserver observer) {
        observers.add(observer);
    }

    // Factory creates pool + throttle per repository
    public void registerRepository(Repository repo) {
        ConnectionPool pool = ConnectionPoolFactory.createPool(repo);
        RateLimitStrategy throttle = ConnectionPoolFactory.createThrottle(repo);
        pools.put(repo.getId(), pool);
        throttles.put(repo.getId(), throttle);
        repositories.put(repo.getId(), repo);
        poolMonitor.registerPool(pool);
        System.out.println("Registered repository: " + repo.getName()
                + " [maxConn=" + repo.getMaxConnections()
                + ", rateLimit=" + repo.getRequestsPerSecond() + " req/s]");
    }

    // Producer calls this — blocks if queue is full (backpressure)
    public void submitRequest(Request request) throws InterruptedException {
        requestQueue.put(request);
    }

    // Start worker threads + monitor daemon
    public void start(int workerCount) {
        running = true;
        poolMonitor.start();
        workers = Executors.newFixedThreadPool(workerCount);
        for (int i = 0; i < workerCount; i++) {
            final int workerId = i;
            workers.submit(() -> processLoop(workerId));
        }
        System.out.println("Started " + workerCount + " worker threads\n");
    }

    private void processLoop(int workerId) {
        while (running) {
            try {
                // Step 1: Take request from queue (BLOCKS if empty)
                Request request = requestQueue.take();

                String repoId = request.getRepositoryId();
                RateLimitStrategy throttle = throttles.get(repoId);
                ConnectionPool pool = pools.get(repoId);

                if (pool == null) {
                    System.out.println("[Worker-" + workerId + "] Unknown repository: " + repoId);
                    continue;
                }

                // Step 2: Acquire rate limit permit (STRATEGY) — wait if throttled
                while (!throttle.acquirePermit()) {
                    Thread.sleep(100);
                }

                // Step 3: Acquire connection from pool (BLOCKS if pool exhausted)
                Connection conn = pool.acquire(5000);
                if (conn == null) {
                    notifyPoolExhausted(repoId);
                    System.out.println("[Worker-" + workerId + "] Pool exhausted for " + repoId + ", dropping " + request.getId());
                    continue;
                }

                notifyAcquired(repoId, conn.getId());

                // Step 4: Execute request
                try {
                    String response = conn.execute(request);
                    System.out.println("[Worker-" + workerId + "] " + request.getId()
                            + " -> " + repositories.get(repoId).getName()
                            + " | conn=" + conn.getId()
                            + " | " + response);
                } finally {
                    // Step 5: Release connection back to pool (always, even on error)
                    pool.release(conn);
                    notifyReleased(repoId, conn.getId());
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void printPoolStats() {
        System.out.println("\n========== Pool Stats ==========");
        for (Map.Entry<String, ConnectionPool> entry : pools.entrySet()) {
            Repository repo = repositories.get(entry.getKey());
            System.out.println("  " + repo.getName() + " " + entry.getValue().getStats());
        }
    }

    public void shutdown() {
        running = false;
        // Graceful shutdown all pools (5s force timeout for demo)
        for (ConnectionPool pool : pools.values()) {
            poolMonitor.shutdownPool(pool, 5000);
        }
        poolMonitor.stop();
        if (workers != null) {
            workers.shutdownNow();
        }
        System.out.println("\nRequestProcessor shut down.");
    }

    public int getQueueSize() { return requestQueue.size(); }

    private void notifyAcquired(String repoId, String connId) {
        for (PoolObserver o : observers) o.onConnectionAcquired(repoId, connId);
    }

    private void notifyReleased(String repoId, String connId) {
        for (PoolObserver o : observers) o.onConnectionReleased(repoId, connId);
    }

    private void notifyPoolExhausted(String repoId) {
        for (PoolObserver o : observers) o.onPoolExhausted(repoId);
    }
}
