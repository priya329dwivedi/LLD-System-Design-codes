# Problem Statement 4: Seat Reservation Pricing

## Problem Description

Build a **SeatPriceStrategy** for a booking system with multiple pricing strategies:
- **Standard** - Base price
- **Peak** - Increases price during peak hours/days
- **MembershipDiscount** - Reduces price for members
- **Promotional** - Applies promo code discounts

**Key Requirements:**
- Support **combining strategies** (pipeline/chain)
- Order matters (non-commutative) - Peak before Discount ≠ Discount before Peak
- Enable/disable strategies at runtime
- Thread-safe for concurrent bookings

## Design Focus

- **Composition**: Combine multiple strategies into a pipeline
- **Priority**: Order of strategy execution affects final price
- **Determinism**: Same input always produces same output
- **Concurrency-Safety**: Safe for multiple threads

## Project Structure

```
org/seatpricing/
├── README.md
├── SeatPriceStrategy.java       # Interface
├── StandardPricing.java         # Base price strategy
├── PeakPricing.java             # Peak hours multiplier
├── MembershipDiscount.java      # Member discount strategy
├── PromotionalPricing.java      # Promo code strategy
├── PricingPipeline.java         # Composes strategies in order
├── PriceContext.java            # Immutable context (price, metadata)
├── SeatPricingDemo.java         # Demo/test class
```

## Class Diagram

```
+------------------------+
|   SeatPriceStrategy    |
|     <<interface>>      |
+------------------------+
| + apply(ctx): PriceCtx |
| + isEnabled(): boolean |
+------------------------+
         ^
         |
    +----+----+----+----+
    |    |    |         |
+------+ +----+ +------+ +-------+
|Stand | |Peak| |Member| | Promo |
| ard  | |    | |  ship| |tional |
+------+ +----+ +------+ +-------+

+-------------------+       +------------------+
| PricingPipeline   |------>| List<Strategy>   |
+-------------------+       +------------------+
| + addStrategy()   |
| + removeStrategy()|
| + calculate(ctx)  |  // applies strategies in order
+-------------------+

+-------------------+
|   PriceContext    |  // Immutable
+-------------------+
| - basePrice       |
| - finalPrice      |
| - isMember        |
| - isPeakTime      |
| - promoCode       |
+-------------------+
```

## Why Order Matters (Non-Commutative)

Base price: $100

**Order 1: Peak (+20%) → MemberDiscount (-10%)**
```
$100 → $120 (peak) → $108 (discount on $120)
```

**Order 2: MemberDiscount (-10%) → Peak (+20%)**
```
$100 → $90 (discount) → $108 (peak on $90)
```

Same result here, but with different percentages or fixed amounts, order changes outcome!

## Strategy Contract

Each strategy must:
1. Accept `PriceContext` (immutable)
2. Return **new** `PriceContext` with modified price
3. Not modify input context (immutability)
4. Be stateless (thread-safe)

```java
public interface SeatPriceStrategy {
    PriceContext apply(PriceContext context);
    boolean isEnabled();
    String getName();
}
```

## Pipeline Execution

```
Input Context → [Standard] → [Peak] → [Membership] → [Promo] → Final Price
                    ↓           ↓          ↓            ↓
                 enabled?    enabled?   enabled?    enabled?
```

## Enable/Disable at Runtime

```java
pipeline.enableStrategy("Peak");
pipeline.disableStrategy("Promotional");
```

## Concurrency Safety

- `PriceContext` is **immutable** - no shared mutable state
- Each strategy is **stateless** - no instance variables modified
- Pipeline can use **synchronized** list or **CopyOnWriteArrayList**

## Expected Usage

```java
// Create pipeline with strategies in order
PricingPipeline pipeline = new PricingPipeline();
pipeline.addStrategy(new StandardPricing());
pipeline.addStrategy(new PeakPricing());
pipeline.addStrategy(new MembershipDiscount());
pipeline.addStrategy(new PromotionalPricing());

// Calculate price
PriceContext context = new PriceContext(100.0, true, true, "SAVE10");
PriceContext result = pipeline.calculate(context);
System.out.println("Final price: " + result.getFinalPrice());

// Disable a strategy at runtime
pipeline.disableStrategy("Peak");
```

## Key Principles

- **Immutability**: PriceContext never modified, always return new instance
- **Single Responsibility**: Each strategy handles one pricing rule
- **Open/Closed**: Add new strategies without modifying pipeline
- **Thread-Safety**: Stateless strategies + immutable context
