# Problem Statement 1: Payment Processor

## Problem Description

Implement a **PaymentStrategy** pattern for processing payments through different payment methods:
- **CreditCard**
- **PayPal**
- **Wallet**

Your `Checkout` class accepts a `PaymentStrategy` and processes payment.

## Design Focus

- **Interface Design**: Clean abstraction for payment processing
- **Dependency Injection**: Checkout class receives strategy via constructor
- **Single Responsibility**: Each class has one reason to change

## Requirements

1. Create a `PaymentStrategy` interface with a `process(amount)` method
2. Implement three concrete strategies:
   - `CreditCardPayment`
   - `PayPalPayment`
   - `WalletPayment`
3. Create a `Checkout` class that:
   - Accepts any `PaymentStrategy` via constructor (dependency injection)
   - Does NOT depend on concrete payment classes
   - Delegates payment processing to the injected strategy

## Design Pattern

**Strategy Pattern** - Defines a family of algorithms (payment methods), encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

## Class Diagram

```
+-------------------+       +------------------------+
|  PaymentStrategy  |<------| Checkout               |
|   <<interface>>   |       +------------------------+
+-------------------+       | - strategy: Payment... |
| + process(amount) |       +------------------------+
+-------------------+       | + processPayment()     |
         ^                  +------------------------+
         |
    +----+----+--------------------+
    |              |               |
+--------+    +---------+    +--------+
|CreditCard|  | PayPal  |    | Wallet |
| Payment  |  | Payment |    |Payment |
+----------+  +---------+    +--------+
```

## Key Principle

> "Program to an interface, not an implementation."

The `Checkout` class only knows about `PaymentStrategy` interface, making it easy to add new payment methods without modifying existing code (Open/Closed Principle).