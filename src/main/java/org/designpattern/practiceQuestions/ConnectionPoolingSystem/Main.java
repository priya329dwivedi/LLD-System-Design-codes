package org.designpattern.practiceQuestions.ConnectionPoolingSystem;

import org.designpattern.practiceQuestions.ConnectionPoolingSystem.model.Repository;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.model.Request;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.observer.HealthLogger;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.observer.MetricsCollector;
import org.designpattern.practiceQuestions.ConnectionPoolingSystem.service.RequestProcessor;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // ========== Setup Repositories (external systems) ==========
        // Each gets its own isolated pool + rate limiter
        Repository paymentGateway = new Repository("R1", "Payment Gateway", "https://pay.example.com", 3, 5);
        Repository inventoryService = new Repository("R2", "Inventory Service", "https://inv.example.com", 2, 3);
        Repository notificationApi = new Repository("R3", "Notification API", "https://notify.example.com", 4, 0);  // no rate limit

        // ========== Request Processor (Singleton) ==========
        RequestProcessor processor = RequestProcessor.getInstance();

        MetricsCollector metrics = new MetricsCollector();
        processor.addObserver(new HealthLogger());
        processor.addObserver(metrics);

        // ========== Register Repos → Factory creates pool + throttle each ==========
        System.out.println("========== Registering Repositories ==========\n");
        processor.registerRepository(paymentGateway);
        processor.registerRepository(inventoryService);
        processor.registerRepository(notificationApi);

        // ========== Start 3 worker threads ==========
        System.out.println("\n========== Starting Workers ==========\n");
        processor.start(3);

        // ========== Simulate incoming requests (producer) ==========
        System.out.println("========== Submitting 15 Requests ==========\n");

        // Mix of requests to different repositories
        for (int i = 1; i <= 5; i++) {
            processor.submitRequest(new Request("PAY-" + i, "R1", "/charge"));
        }
        for (int i = 1; i <= 5; i++) {
            processor.submitRequest(new Request("INV-" + i, "R2", "/check-stock"));
        }
        for (int i = 1; i <= 5; i++) {
            processor.submitRequest(new Request("NOTIF-" + i, "R3", "/send-email"));
        }

        // ========== Wait for processing ==========
        Thread.sleep(8000);

        // ========== Stats ==========
        processor.printPoolStats();
        metrics.printMetrics();

        // ========== Demonstrate pool isolation ==========
        System.out.println("\n========== Pool Isolation Demo ==========");
        System.out.println("Submitting 10 more requests ONLY to Payment Gateway...");
        System.out.println("(Inventory & Notification pools stay unaffected)\n");

        for (int i = 6; i <= 15; i++) {
            processor.submitRequest(new Request("PAY-" + i, "R1", "/charge"));
        }

        Thread.sleep(10000);

        // ========== Final Stats ==========
        processor.printPoolStats();
        metrics.printMetrics();

        // ========== Graceful Shutdown ==========
        System.out.println("\n========== Shutting Down ==========");
        processor.shutdown();
    }
}
