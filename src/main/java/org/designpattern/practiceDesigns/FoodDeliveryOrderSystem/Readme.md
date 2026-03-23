# Food Delivery Order System - Multiple Design Patterns

## Problem Statement

Design a food delivery order system similar to platforms like Uber Eats or Amazon Food. Users can place food orders from restaurants, choose a payment method, and receive real-time updates about the order status.

## Functional Requirements

### 1. Payment Methods (Factory Pattern)
Users can choose different payment methods:
- **Credit Card**
- **UPI**
- **Wallet**

### 2. Delivery Fee Calculation (Strategy Pattern)
The system should calculate delivery fees using different strategies:
- **Standard Delivery** - Normal delivery fee.
- **Express Delivery** - Higher fee for faster delivery.
- **Surge Pricing** - Dynamic pricing during peak hours.

### 3. Order Status Updates (Observer Pattern)
Users should receive real-time updates for order status changes:
- **Order Placed**
- **Preparing**
- **Out for Delivery**
- **Delivered**

Different systems should react to order updates:
- **Notification System** - Sends notifications to the user.
- **Delivery Tracking System** - Updates delivery tracking in real time.
- **Analytics System** - Logs order data for analytics.

## Design Patterns Used

| Pattern | Where |
|---|---|
| **Factory Pattern** | Payment methods — create the right payment processor based on user's choice |
| **Strategy Pattern** | Delivery fee calculation — swappable algorithms at runtime |
| **Observer Pattern** | Order status changes — multiple systems get notified automatically |
