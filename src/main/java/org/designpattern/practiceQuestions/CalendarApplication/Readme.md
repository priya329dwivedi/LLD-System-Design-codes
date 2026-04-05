# Calendar Application - LLD

## Problem Statement

Design a Calendar Application that supports creating, updating, and deleting events. Events can be meetings (with location and guest-list) or personal events (holidays, birthdays, reminders). Users can accept/reject events, view their calendar, and find common free slots across multiple users.

## Functional Requirements

- **createEvent(event)** — Create a new event with {start, end, location, owner, user-list, title}.
- **updateEvent(eventId, updatedFields)** — Update an existing event.
- **deleteEvent(eventId, userId)** — Delete an event (only owner can delete).
- **respondToEvent(eventId, userId, response)** — Accept, reject, or leave neutral.
- **getCalendar(userId)** — Get all events for a user.
- **getEventDetails(eventId)** — Get full details of an event.
- **findCommonFreeSlots(userIds, duration, dateRange)** — Find overlapping free time across a set of users.

## Event Types

| Type | Description | Has Location | Has Guest List |
|---|---|---|---|
| **Meeting** | Scheduled meeting with participants | Yes | Yes |
| **Holiday** | Public/personal holiday | No | No |
| **Birthday** | Birthday reminder | No | No |
| **Reminder** | Personal reminder | No | No |

## User Response States

- **ACCEPTED** — User has accepted the event invite
- **REJECTED** — User has declined the event invite
- **PENDING** — Default state, user hasn't responded yet

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Strategy Pattern** | Conflict detection — skip conflict check for holidays/birthdays vs strict check for meetings | Different event types have different scheduling rules |
| **Observer Pattern** | Notify users when event is created/updated/deleted | Decouple event management from notification |
| **Factory Pattern** | Create different event types (Meeting, Holiday, Birthday, Reminder) | Centralize event creation logic |
| **Singleton Pattern** | CalendarService — single entry point for all operations | One service instance for consistency |

## Key Edge Cases

- Overlapping events for the same user (meetings should warn, holidays/birthdays should not)
- Owner deletes event → all user associations removed
- Finding free slots when users have no events (entire range is free)
- Finding free slots when no common slot exists
- User responds to an event they're not part of → reject gracefully
- Update event time → re-check conflicts for all participants
