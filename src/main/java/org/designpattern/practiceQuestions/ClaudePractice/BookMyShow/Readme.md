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

## Class Diagram (LLD)

```
+================================================================+
|                    SINGLETON                                    |
|                                                                |
|  +----------------------------------------------------------+  |
|  |              BookingService (Singleton)                   |  |
|  |----------------------------------------------------------|  |
|  | - instance: BookingService                                |  |
|  | - pricingStrategy: PricingStrategy     ←── STRATEGY      |  |
|  | - observers: List<BookingObserver>     ←── OBSERVER       |  |
|  | - bookingHistory: List<Booking>                           |  |
|  |----------------------------------------------------------|  |
|  | + getInstance()                                           |  |
|  | + selectSeats(show, seatIds, user): List<ShowSeat>        |  |
|  | + confirmBooking(user, show, seats, payment): Booking     |  |
|  | + cancelBooking(booking)                                  |  |
|  | + calculateTotal(seats): double                           |  |
|  | + setPricingStrategy(strategy)                            |  |
|  +----------------------------------------------------------+  |
|         |                |                    |                  |
+=========|================|====================|=================+
          |                |                    |
          v                v                    v
+------------------+ +---------------------+ +---------------------+
| STRATEGY PATTERN | | FACTORY PATTERN     | | OBSERVER PATTERN    |
+------------------+ +---------------------+ +---------------------+
|                  | |                     | |                     |
| <<interface>>    | | <<interface>>       | | <<interface>>       |
| PricingStrategy  | | PaymentMethod      | | BookingObserver     |
| +calculatePrice()| | +pay(amount): bool | | +onBookingConfirmed |
|                  | | +getName(): String  | | +onBookingCancelled |
|   ^   ^   ^      | |                    | | +onPaymentFailed    |
|   |   |   |      | |   ^       ^    ^   | |                    |
|   |   |   |      | |   |       |    |   | |    ^          ^    |
+---|---|---|------+ +---|-------|----|-+ +----|----------|---+
    |   |   |            |       |    |       |          |
    |   |   |            |       |    |       |          |
+---+  +--+ +------+ +--+-+ +--+-+ +-++ +----+---+ +----+----+
|Base| |Wknd| |Coupon| |Card| |UPI | |Wa| |Email   | |SMS      |
|Pric| |Surg| |Disc  | |Pay | |Pay | |ll| |Notifier| |Notifier |
|ing | |e   | |ount  | |    | |    | |et| |        | |         |
+----+ +----+ +------+ +----+ +----+ +--+ +--------+ +---------+
                             |
                     +-------+--------+
                     | PaymentFactory  |
                     | +createPayment()|
                     +-----------------+

+==================================================================+
|                         MODEL CLASSES                             |
+==================================================================+
|                                                                  |
|  +----------+     has many    +-----------+                      |
|  | Theatre  |---------------->| Screen    |                      |
|  | -id      |                 | -id       |                      |
|  | -name    |                 | -name     |                      |
|  | -city    |                 | -seats[]  |                      |
|  +----------+                 +-----+-----+                      |
|                                     |                            |
|                                has many                          |
|                                     |                            |
|  +----------+   belongs to   +------v-----+    +----------+     |
|  | Movie    |<---------------| Show       |    | Seat     |     |
|  | -id      |                | -id        |    | -id      |     |
|  | -name    |                | -movie     |    | -row     |     |
|  | -genre   |                | -screen    |    | -col     |     |
|  | -duration|                | -startTime |    | -seatType|     |
|  +----------+                | -date      |    +-----+----+     |
|                              | -showSeats |          |          |
|                              +------+-----+          |          |
|                                     |                |          |
|                                  has many            |          |
|                                     |                |          |
|                              +------v--------+       |          |
|                              | ShowSeat      |<------+          |
|                              | -seat         | wraps Seat       |
|                              | -status       |                  |
|                              | -lockedByUser |                  |
|                              | -lockedAt     |                  |
|                              |---------------|                  |
|                              | +lock(userId) |                  |
|                              | +confirmBook()|                  |
|                              | +release()    |                  |
|                              | +releaseIf    |                  |
|                              |  Expired()    |                  |
|                              +-------+-------+                  |
|                                      |                          |
|  +----------+   has many    +--------v-------+                  |
|  | User     |<--------------| Booking        |                  |
|  | -id      |  belongs to   | -id            |                  |
|  | -name    |               | -user          |                  |
|  | -email   |               | -show          |                  |
|  | -phone   |               | -seats[]       |                  |
|  +----------+               | -totalAmount   |                  |
|                             | -status        |                  |
|                             +----------------+                  |
|                                                                 |
+==================================================================+
|                          ENUMS                                   |
+==================================================================+
|                                                                  |
|  +-----------------+  +--------------+  +-------------------+   |
|  | SeatType        |  | SeatStatus   |  | BookingStatus     |   |
|  |-----------------|  |--------------|  |-------------------|   |
|  | REGULAR  (Rs150)|  | AVAILABLE    |  | PENDING           |   |
|  | PREMIUM  (Rs300)|  | LOCKED       |  | CONFIRMED         |   |
|  | RECLINER (Rs500)|  | BOOKED       |  | CANCELLED         |   |
|  +-----------------+  +--------------+  +-------------------+   |
|                                                                  |
+==================================================================+
```

## User Flow Diagram

```
                              +------------------+
                              |   User Opens App |
                              +--------+---------+
                                       |
                                       v
                              +------------------+
                              | Select City      |
                              +--------+---------+
                                       |
                                       v
                              +------------------+
                              | Browse Movies    |
                              | (Movie list)     |
                              +--------+---------+
                                       |
                                       v
                              +------------------+
                              | Select Movie     |
                              | (Pushpa 2)       |
                              +--------+---------+
                                       |
                                       v
                              +------------------+
                              | Select Show      |
                              | (Theatre, Time)  |
                              +--------+---------+
                                       |
                                       v
                         +---------------------------+
                         | View Seat Layout          |
                         | [R1][R2][XX][P1][P2][V1]  |
                         | (Real-time availability)  |
                         +------------+--------------+
                                      |
                                      v
                         +---------------------------+
                         | Select Seats              |
                         | (C2, C3 - Premium)        |
                         +------------+--------------+
                                      |
                                      v
                         +---------------------------+
                         | Seats LOCKED (5 min timer)|
                         | (Prevents double-booking) |
                         +------------+--------------+
                                      |
                                      v
                    +-----------------+------------------+
                    |                                    |
                    v                                    v
         +-------------------+              +--------------------+
         | Calculate Price   |              | Lock Expires       |
         | (Strategy:        |              | (User abandoned)   |
         |  Base/Weekend/    |              +----------+---------+
         |  Coupon)          |                         |
         +--------+----------+                         v
                  |                          +--------------------+
                  v                          | Seats RELEASED     |
         +-------------------+               | (Available again)  |
         | Select Payment    |               +--------------------+
         | (Factory:         |
         |  UPI/Card/Wallet) |
         +--------+----------+
                  |
                  v
         +-------------------+
         | Process Payment   |
         +--------+----------+
                  |
        +---------+---------+
        |                   |
        v                   v
+---------------+   +----------------+
| Payment OK    |   | Payment FAILED |
+-------+-------+   +-------+--------+
        |                    |
        v                    v
+---------------+   +----------------+
| Seats BOOKED  |   | Seats RELEASED |
| Booking       |   | Booking        |
| CONFIRMED     |   | CANCELLED      |
+-------+-------+   +-------+--------+
        |                    |
        v                    v
+---------------+   +----------------+
| Notify User   |   | Notify User    |
| [Email]       |   | [Email]        |
| [SMS]         |   | [SMS]          |
+-------+-------+   +----------------+
        |
        v
+-------------------+
| Booking History   |
| (View past        |
|  bookings)        |
+-------------------+
```

## Interview Follow-ups to Consider

- How do you **prevent double-booking**? (Seat locking with timeout)
- How does **pricing change** for weekend vs weekday? (Strategy Pattern)
- How would you **cancel a booking** and release seats? (State transition)
- How do you **show seat availability** in real-time? (Seat status on Show)
- How would you handle **payment failure** mid-booking? (Rollback seats)
