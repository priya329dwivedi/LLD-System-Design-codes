# Ride Sharing Service (MoveInSync style)

## Problem Statement

### Functional Requirements
- Create named routes with ordered pickup stops
- Schedule a trip on a route by assigning a driver and vehicle at a fixed departure time
- Employees book seats on a scheduled trip, specifying their pickup stop
- Cancelling a booking releases the seat for another employee
- Prevent overbooking beyond vehicle capacity
- Advance a trip through its lifecycle: SCHEDULED → IN_PROGRESS → COMPLETED

### Non-Functional Requirements
- In-memory, no persistence
- Seat booking is thread-safe (two employees must not claim the last seat simultaneously)
- Partial failure is acceptable — a failed booking does not affect others

---

## Key Entities and Relationships

```
Route ──< RouteStop          (a route has ordered stops)
Route <── Trip               (a trip is a scheduled instance of a route)
Vehicle ──> Trip             (a vehicle is assigned to a trip, determines seat capacity)
Driver  ──> Trip             (a driver is assigned to a trip)
Trip ──< Booking             (a trip has many bookings)
Employee ──> Booking         (an employee books a seat on a trip)
Booking ──> RouteStop        (booking records where the employee boards)
```

---

## Design Choices and Trade-offs

### Trip owns seat count
`availableSeats` lives on `Trip`, not derived from counting bookings.
- Pro: O(1) check, no list scan on every book/cancel
- Con: must keep in sync with booking list — handled by `reserveSeat()` / `releaseSeat()`

### Synchronized seat reservation in BookingService
`bookSeat()` and `cancelBooking()` are `synchronized` — seat reservation is a critical section.
A simple monitor lock is enough for a single-process in-memory system.
- Trade-off: if this were distributed, you'd need Redis or a DB row lock instead.

### Optional<Booking> instead of exceptions
`bookSeat()` returns `Optional.empty()` on failure rather than throwing.
Keeps the caller clean — booking failure is an expected outcome, not an error.

### TripService owns Vehicle registry
Vehicles are registered with `TripService` so it can look up capacity when scheduling a trip.
Avoids passing Vehicle objects around everywhere.

---

## Patterns Used

| Pattern | Where | Why |
|---------|-------|-----|
| Strategy (implicit) | `RouteService`, `TripService`, `BookingService` are separate | Single Responsibility — each owns one domain concept |
| State machine | `TripStatus` enum + `setStatus()` | Makes valid transitions explicit and readable |
| Optional return | `BookingService.bookSeat()` | Avoids exception-as-control-flow for expected failure |

No over-engineered patterns. No factory, no observer, no strategy interface added speculatively.

---

## How to Extend

| Extension | Where to change |
|-----------|----------------|
| Add recurring trips (Mon–Fri 9AM) | Add `TripSchedule` model; `TripService` generates `Trip` instances from it |
| Add corporate billing per seat | Add `BillingService`; on `bookSeat()` success, create an invoice record |
| Add route optimization (reorder stops) | Add `RouteOptimizationService` that sorts `RouteStop` by sequence before a trip starts |
| Add ratings | Add `Rating` model; `BookingService` allows rating after trip is COMPLETED |
| New vehicle type (e.g., EV, wheelchair-accessible) | Add `vehicleType` field to `Vehicle`; no other changes needed |

---

## Demo Flow Walkthrough

**Flow 1 — Happy path:**
Route `Koramangala → Whitefield` is created with 3 stops. A Minibus (3 seats) is scheduled.
Alice, Bob, and Carol each book a seat — all succeed, `availableSeats` drops to 0.

**Flow 2 — Overbooking rejected:**
Dave tries to book a 4th seat on the same Minibus. `reserveSeat()` returns false,
`bookSeat()` returns `Optional.empty()`. No exception, no crash.

**Flow 3 — Cancellation frees seat:**
Bob cancels. `releaseSeat()` increments `availableSeats` back to 1.
Dave retries and succeeds.

**Trip lifecycle:** Status advances SCHEDULED → IN_PROGRESS → COMPLETED at the end.
