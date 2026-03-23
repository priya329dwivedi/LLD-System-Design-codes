# Order Status Notification System

## Problem Statement

Design an Order Status Notification System where multiple notification services must react to changes in an order's status.

## Requirements

### Order States
An order can have the following states:
- `CREATED`
- `SHIPPED`
- `DELIVERED`
- `CANCELLED`

### Behavior
- When the order status changes, all registered notification services must be notified.

### Notification Services
- Email Notification
- SMS Notification
- Push Notification

## Constraints

1. **Open/Closed Principle**: New notification types should be added without modifying existing code.
2. **Fault Tolerance**: Failure in one notification service must not affect others.
3. **Dynamic Registration**: Observers should be able to subscribe and unsubscribe dynamically.

## Design Pattern

This problem is a classic use case for the **Observer Design Pattern**.

### Key Components

1. **Subject (Observable)**: The entity being observed (Order/OrderManager)
2. **Observer**: The interface that all notification services implement
3. **Concrete Observers**: EmailNotification, SMSNotification, PushNotification

## Suggested File Structure

```
ordernotification/
├── OrderStatus.java              (enum)
├── Order.java                    (model)
├── OrderObserver.java            (observer interface)
├── OrderSubject.java             (subject interface)
├── OrderManager.java             (concrete subject)
├── EmailNotificationService.java (concrete observer)
├── SMSNotificationService.java   (concrete observer)
├── PushNotificationService.java  (concrete observer)
├── OrderNotificationDemo.java    (main class)
└── README.md
```

## Implementation Hints

1. Create an enum `OrderStatus` with: CREATED, SHIPPED, DELIVERED, CANCELLED
2. Define an `OrderObserver` interface with an `update()` method
3. The subject should maintain a list of observers and provide `subscribe()`, `unsubscribe()`, and `notifyObservers()` methods
4. Wrap each observer notification in try-catch to ensure fault tolerance
5. Consider what information observers need when notified (order id, old status, new status)

## Expected Output Example

```
=== Registering Notification Services ===
Observer registered: EmailNotificationService
Observer registered: SMSNotificationService
Observer registered: PushNotificationService

--- Order ORD-001 status changed: null -> CREATED ---
[Email] Sending email: Order ORD-001 status changed to CREATED
[SMS] Sending SMS: Order ORD-001 status changed to CREATED
[Push] Sending push notification: Order ORD-001 status changed to CREATED

--- Order ORD-001 status changed: CREATED -> SHIPPED ---
[Email] Sending email: Order ORD-001 status changed to SHIPPED
[SMS] Sending SMS: Order ORD-001 status changed to SHIPPED
[Push] Sending push notification: Order ORD-001 status changed to SHIPPED
```
