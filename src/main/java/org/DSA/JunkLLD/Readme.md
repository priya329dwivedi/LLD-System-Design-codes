# Calendar Application - Low Level Design

## Problem Statement

Design a Calendar Application that supports creating, updating, and deleting events. Events can be meetings (with location and guest-list) or personal events (holidays, birthdays, reminders). Users can accept/reject events, view their calendar, and find common free slots across multiple users.

---

## Functional Requirements

| Method | Signature | Description |
|--------|-----------|-------------|
| Create Event | `createEvent(event)` | Create a new event with `{start, end, location, owner, user-list, title}` |
| Update Event | `updateEvent(eventId, updatedFields)` | Update an existing event |
| Delete Event | `deleteEvent(eventId, userId)` | Delete an event — only the owner can delete |
| Respond to Event | `respondToEvent(eventId, userId, response)` | Accept, reject, or leave neutral |
| Get Calendar | `getCalendar(userId)` | Get all events for a user |
| Get Event Details | `getEventDetails(eventId)` | Get full details of an event |
| Find Free Slots | `findCommonFreeSlots(userIds, duration, dateRange)` | Find overlapping free time across a set of users |

---

## Event Types

- **Meeting** — has a location, guest list, and invite response tracking
- **Personal Event** — holidays, birthdays, reminders (no guest list)

---

## Core Entities

### Event (base)
- `eventId: String`
- `title: String`
- `start: LocalDateTime`
- `end: LocalDateTime`
- `owner: User`
- `type: EventType` (MEETING | PERSONAL)

### Meeting (extends Event)
- `location: String`
- `invitees: List<User>`
- `responses: Map<UserId, ResponseStatus>` (ACCEPTED | REJECTED | NEUTRAL)

### PersonalEvent (extends Event)
- `subType: PersonalEventType` (HOLIDAY | BIRTHDAY | REMINDER)

### User
- `userId: String`
- `name: String`
- `calendar: List<Event>`

---

## Key Design Decisions

### 1. Ownership for Deletion
Only the event owner can delete an event. Other invitees can only remove themselves (respond with REJECTED or withdraw).

### 2. Event Response Tracking
Responses are stored as `Map<userId, ResponseStatus>` on the Meeting object. Default status for a new invite is `NEUTRAL`.

### 3. Finding Common Free Slots
Algorithm:
1. Collect all accepted/neutral events for each user in the date range.
2. Merge overlapping busy intervals per user.
3. Compute the union of all busy intervals across users.
4. Scan the date range for gaps >= `duration` in the merged busy list.
5. Return those gaps as available slots.

### 4. Calendar Storage
Each user maintains an ordered list of events. Events are indexed by `eventId` in a global `EventRepository` for O(1) lookup.

---

## Class Structure

```
CalendarService
  ├── createEvent(event) → Event
  ├── updateEvent(eventId, fields) → Event
  ├── deleteEvent(eventId, userId) → void
  ├── respondToEvent(eventId, userId, response) → void
  ├── getCalendar(userId) → List<Event>
  ├── getEventDetails(eventId) → Event
  └── findCommonFreeSlots(userIds, duration, dateRange) → List<TimeSlot>

EventRepository (in-memory store)
  └── Map<eventId, Event>

UserRepository
  └── Map<userId, User>

Models
  ├── Event (abstract base)
  ├── Meeting extends Event
  ├── PersonalEvent extends Event
  ├── User
  ├── TimeSlot { start, end }
  ├── EventType (enum)
  ├── ResponseStatus (enum)
  └── PersonalEventType (enum)
```

---

## Edge Cases

- Overlapping events — allowed (calendar does not enforce conflict by default)
- Non-owner tries to delete — throw `UnauthorizedException`
- Event not found — throw `EventNotFoundException`
- `findCommonFreeSlots` with no users or empty calendars — return full date range as free
- `duration` longer than any gap in the range — return empty list

---

## Concurrency Considerations

- Use `ConcurrentHashMap` for event and user stores
- Lock at event level when updating or responding to avoid lost updates
- `findCommonFreeSlots` reads a snapshot — no locking needed if reads are consistent