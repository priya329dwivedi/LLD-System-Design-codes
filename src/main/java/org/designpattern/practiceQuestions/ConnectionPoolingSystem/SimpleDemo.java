package org.designpattern.practiceQuestions.ConnectionPoolingSystem;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleDemo {

    // Simple connection — reusable object in the pool
    static class Connection {
        private final int id;

        Connection(int id) { this.id = id; }

        String execute(String request) {
            try {
                Thread.sleep(200 + (long) (Math.random() * 300));  // simulate HTTP call
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "200 OK";
        }

        @Override
        public String toString() { return "Conn-" + id; }
    }

    // Pool — ArrayBlockingQueue of reusable connections
    static class ConnectionPool {
        private final ArrayBlockingQueue<Connection> pool;

        ConnectionPool(int size) {
            pool = new ArrayBlockingQueue<>(size);
            for (int i = 1; i <= size; i++) {
                pool.offer(new Connection(i));
            }
            System.out.println("Pool created with " + size + " connections\n");
        }

        Connection acquire() throws InterruptedException {
            return pool.take();  // BLOCKS if pool empty
        }

        void release(Connection conn) {
            pool.offer(conn);    // returns to pool, wakes up blocked acquire()
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConnectionPool pool = new ConnectionPool(3);  // only 3 connections
        ExecutorService executor = Executors.newFixedThreadPool(5);  // 5 threads competing for 3 connections

        // Submit 10 requests — 5 threads fight over 3 connections
        for (int i = 1; i <= 10; i++) {
            final String request = "REQ-" + i;
            executor.submit(() -> {
                try {
                    Connection conn = pool.acquire();   // blocks if all 3 busy
                    try {
                        String response = conn.execute(request);
                        System.out.println(Thread.currentThread().getName()
                                + " | " + request + " | " + conn + " | " + response);
                    } finally {
                        pool.release(conn);             // always return to pool
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("\nAll requests processed.");
    }
}
