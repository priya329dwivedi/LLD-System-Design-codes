# Ride Pricing System - Strategy Pattern

## Problem Statement

Design a ride pricing module for a ride-hailing application where the fare calculation can vary based on different pricing strategies.

## Pricing Strategies

The system should support the following pricing models:

1. **Normal Pricing** - Standard fare calculation based on distance.
2. **Surge Pricing** - Dynamic pricing that factors in demand along with distance.
3. **Shared Ride Pricing** - Reduced pricing that considers the number of passengers sharing the ride.

## Requirements

- Each pricing model uses a different algorithm to calculate the final fare based on factors like **distance**, **demand**, and **number of passengers**.
- The system should allow selecting the appropriate pricing strategy at **runtime**.
- The system should be easily **extensible** to support new pricing models in the future **without modifying existing code**.

## Why Strategy Pattern?

This is a good use case for the **Strategy Pattern**, where each pricing algorithm is encapsulated in its own strategy implementation. This allows:

- Swapping pricing algorithms at runtime.
- Adding new pricing strategies without changing existing code (Open/Closed Principle).
- Clean separation of concerns — each strategy handles its own calculation logic.