# E-Commerce Discount Engine - Strategy Pattern

## Problem Statement

Design a discount engine for an e-commerce platform like Amazon where different discount rules can be applied to a shopping cart.

## Discount Strategies

The system should support the following discount types:

1. **Percentage Discount** - Applies a percentage-based discount on the cart total (e.g., 10% off).
2. **Flat Discount** - Applies a fixed amount discount on the cart total (e.g., Rs 200 off).
3. **Seasonal Sale** - Applies a seasonal sale discount with a special rate on the cart total.
4. **Buy-One-Get-One (BOGO)** - Applies a BOGO offer where the cheapest item in the cart is free.

## Requirements

- Each discount type has its own logic to calculate the final price of the cart.
- The system should dynamically choose the appropriate discount strategy based on the **promotion applied**.
- The system should allow adding new discount types in the future **without modifying existing code**.

## Why Strategy Pattern?

This is a good candidate for the **Strategy Pattern**, where each discount calculation algorithm is encapsulated as a separate strategy and applied to the cart during checkout. This allows:

- Swapping discount algorithms dynamically at runtime.
- Adding new discount strategies without changing existing code (Open/Closed Principle).
- Clean separation of concerns — each strategy handles its own discount calculation logic.
