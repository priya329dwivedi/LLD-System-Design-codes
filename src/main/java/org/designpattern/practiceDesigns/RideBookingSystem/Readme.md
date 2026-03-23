# Ride Booking System (Uber-like) - Multiple Design Patterns

## Problem Statement

Design a ride booking system similar to Uber where users can request rides and drivers accept them. The system should support multiple ride types, dynamic pricing strategies, and notify different components when ride status changes.

## Functional Requirements

### 1. Ride Types (Factory Pattern)
The system supports multiple ride types:
- **Mini** - Budget ride option.
- **Sedan** - Standard comfort ride.
- **SUV** - Premium spacious ride.

### 2. Pricing Strategies (Strategy Pattern)
The system should support different pricing strategies:
- **Normal Pricing** - Standard fare based on distance.
- **Surge Pricing** - Dynamic pricing during high demand.
- **Shared Ride Pricing** - Reduced pricing when ride is shared among passengers.

### 3. Ride Status Updates (Observer Pattern)
The system should update ride status:
- **Requested**
- **Driver Assigned**
- **Ride Started**
- **Ride Completed**

Multiple systems should react when ride status changes:
- **Rider Notification** - Notifies the rider about status updates.
- **Driver App** - Updates the driver's app with ride info.
- **Billing System** - Tracks ride progress for billing.

### 4. Driver Actions
- Drivers can **accept** or **reject** rides.

## Design Patterns Used

| Pattern | Where |
|---|---|
| **Factory Pattern** | Ride types — create the right ride (Mini/Sedan/SUV) based on user's choice |
| **Strategy Pattern** | Pricing strategies — swappable pricing algorithms at runtime |
| **Observer Pattern** | Ride status changes — multiple systems get notified automatically |
