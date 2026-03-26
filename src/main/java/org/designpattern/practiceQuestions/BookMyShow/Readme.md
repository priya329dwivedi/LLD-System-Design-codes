# BookMyShow - LLD

## Problem Statement

Design a movie ticket booking system (like BookMyShow) that allows users to browse movies, select a show, view seat layout, and book tickets. The system should handle seat availability in real-time, support different seat types with dynamic pricing, process payments, and notify users on booking events.

## Functional Requirements

- **Browse movies** and available shows for a given city/theatre.
- **View seat layout** — Display the hall as a grid of seat objects (Regular, Premium, VIP) with real-time availability.
- **Select & lock seats** — Temporarily lock seats during booking to prevent double-booking.
- **Book tickets** — Confirm booking after payment. Release seats if payment fails or times out.
- Support **different seat types** (Regular, Premium, Recliner) — each with its own pricing.
- Support **dynamic pricing strategies** (base price, weekend surge, peak hour, offer/coupon discount).
- Support **multiple payment methods** (CreditCard, UPI, Wallet).
- **Notify users** on booking confirmation, cancellation, and payment failure.
- **Get booking history** for a user.

## Key Entities & Schema

| Entity | Fields |
|---|---|
| **Movie** | id, name, genre, duration |
| **Theatre** | id, name, city, list of screens |
| **Screen** | id, theatreId, name, list of seats |
| **Seat** | id, screenId, row, column, seatType (REGULAR/PREMIUM/RECLINER) |
| **Show** | id, movieId, screenId, startTime, date |
| **Booking** | id, userId, showId, list of seats, totalAmount, status, paymentMethod |
| **User** | id, name, email, phone |

### Relationships (Joins)
- Theatre → has many → Screens
- Screen → has many → Seats
- Show → belongs to → Movie + Screen
- Booking → belongs to → User + Show + Seats

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Strategy Pattern** | Pricing — base price, weekend surge, peak hour, coupon discount | Swap pricing logic without changing booking flow |
| **Factory Pattern** | Seat creation (Regular/Premium/Recliner), Payment methods (CreditCard/UPI/Wallet) | Create the right object based on type |
| **Observer Pattern** | Notify on booking confirmed, cancelled, payment failed (Email, SMS, Push) | Decouple booking from notifications |
| **State Pattern** | Booking states — PENDING → CONFIRMED → CANCELLED | Clean state transitions with behavior per state |
| **Singleton Pattern** | BookingService — one shared instance | Consistent seat locking and booking |

## Seat Layout Design

The hall is represented as a **2D grid**. Each seat is a rectangle object with position (row, col), type, and availability:

```
Screen
──────────────────────────────
Row A  [R1][R2][R3][__][R4][R5][R6]     ← Regular
Row B  [R1][R2][R3][__][R4][R5][R6]     ← Regular
Row C  [P1][P2][P3][__][P4][P5][P6]     ← Premium
Row D  [P1][P2][P3][__][P4][P5][P6]     ← Premium
Row E  [V1][V2][__][__][__][V3][V4]     ← Recliner (VIP)

[XX] = Booked    [R1] = Available Regular
[__] = Gap/Aisle [P1] = Available Premium
```

## Interview Follow-ups to Consider

- How do you **prevent double-booking**? (Seat locking with timeout)
- How does **pricing change** for weekend vs weekday? (Strategy Pattern)
- How would you **cancel a booking** and release seats? (State transition)
- How do you **show seat availability** in real-time? (Seat status on Show)
- How would you handle **payment failure** mid-booking? (Rollback seats)
- How would you handle **concurrency** when two users try to lock the same seat? (synchronized on ShowSeat)
