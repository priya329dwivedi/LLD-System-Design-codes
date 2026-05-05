# Multi-Level Parking Lot

## Problem Statement

### Functional Requirements
- A parking lot has multiple floors; each floor has slots of different types (BIKE, CAR, TRUCK)
- Park a vehicle: find the first available slot matching the vehicle type, issue a ticket
- Unpark a vehicle: free the slot, calculate and display the fee
- Reject parking when no slot is available for that vehicle type
- Show availability per floor per vehicle type

### Non-Functional Requirements
- In-memory, no persistence
- Simple first-fit slot assignment (no optimisation needed)
- Single-threaded — concurrency not required for this scope

---

## Key Entities and Relationships

```
ParkingLot ──< Floor ──< Slot       (lot has floors, floors have slots)
Slot ── VehicleType                 (each slot accepts exactly one vehicle type)
Vehicle ── VehicleType              (each vehicle has a type that maps to a slot type)
Ticket ──> Slot + Vehicle + Floor   (issued on park, used to unpark)
```

---

## Design Choices and Trade-offs

### VehicleType drives Slot matching
One enum shared by both `Vehicle` and `Slot`. The match is a simple `==` comparison —
no if-else, no map, no subclass explosion.

### First-fit slot assignment
Floors are scanned in order; first available matching slot wins.
- Pro: simple, predictable, O(floors × slots per floor)
- Con: doesn't balance load across floors. Acceptable for an LLD interview.

### Optional<Ticket> instead of exceptions
`park()` returns `Optional.empty()` when full. Parking full is expected, not exceptional.

### Flat fee in Ticket
`calculateFee()` lives on `Ticket` — it has the entry/exit time and can compute duration.
Avoids a separate `FeeService` that would just delegate back to the same data.

---

## Patterns Used

| Pattern | Where | Why |
|---------|-------|-----|
| Enum mapping | `VehicleType` on both `Vehicle` and `Slot` | Eliminates subclass proliferation (no CarSlot, BikeSlot classes needed) |
| Optional return | `ParkingLot.park()` | Clean handling of "lot full" without exceptions |
| Tell-don't-ask on Slot | `occupy()` / `free()` | Slot owns its state; callers don't flip `boolean` directly |

---

## How to Extend

| Extension | Where to change |
|-----------|----------------|
| Pricing per vehicle type | Add rate map in `Ticket.calculateFee()` or extract `FeeStrategy` |
| Nearest-to-entrance slot | Replace `findFirst()` in `Floor` with a comparator on slot position |
| Reserved/handicap slots | Add `SlotCategory` enum to `Slot`; filter in `findAvailableSlot()` |
| Concurrency (multiple entry gates) | Add `synchronized` to `ParkingLot.park()` and `unpark()` |
| Persistence | Replace the `activeTickets` map with a repository interface |

---

## Demo Flow Walkthrough

**Setup:** 2 floors — Floor 1 has 2 bike + 2 car slots; Floor 2 has 1 car + 1 truck slot.

**Flow 1 — Normal parking:**
Car, bike, and truck are each parked. Each gets a ticket with floor + slot assigned.

**Flow 2 — Overflow:**
Two more cars fill the remaining car slots (F1-C2, F2-C1). A 6th car attempt is rejected
with `Optional.empty()` — no exception, no crash.

**Flow 3 — Unpark + re-park:**
The first car is unparked: slot freed, fee printed. A new car parks successfully in the
freed slot, confirming the release path works end-to-end.
