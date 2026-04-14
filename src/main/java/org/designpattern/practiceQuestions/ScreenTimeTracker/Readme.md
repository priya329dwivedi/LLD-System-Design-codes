# Screen Time Tracker — LLD (Interview Style)

## Requirements
1. Track screen time of different applications
2. Display usage statistics (daily & weekly)
3. Persist data for the last 7 days

---

## Layers

```
Model       →  App, AppUsage, AppCategory
Repository  →  AppRepository, UsageRepository  (in-memory HashMap store)
Service     →  ScreenTimeService  (all business logic)
Main        →  demo driver
```

---

## Class Summary

```
model/
  AppCategory.java    enum: SOCIAL_MEDIA, PRODUCTIVITY, ENTERTAINMENT, GAMING
  App.java            id, name, category, dailyLimitMinutes
  AppUsage.java       appId, date, durationMinutes  — one usage record

repository/
  AppRepository.java       HashMap<String, App>               — keyed by appId
  UsageRepository.java     HashMap<String, List<AppUsage>>    — keyed by appId

service/
  ScreenTimeService.java   all tracking + stats logic

Main.java   demo
```

---

## Key Data Structures

| Field | Type | Purpose |
|-------|------|---------|
| `AppRepository.store` | `HashMap<String, App>` | O(1) app lookup by id |
| `UsageRepository.store` | `HashMap<String, List<AppUsage>>` | All usage records per app |
| `activeSessions` | `HashMap<String, Long>` | appId → start time ms for in-progress sessions |

---

## Core Flows

**Track a session**
```
startSession(appId)  → store start time in activeSessions
stopSession(appId)   → compute duration = (now - start) / 60000
                     → save AppUsage to usageRepo
                     → check daily limit inline
```

**Display daily stats**
```
for each App: loop its usages, sum minutes where date == requested date
```

**Display weekly stats**
```
for each App: loop its usages, sum minutes where date is in [today-6, today]
```

**7-day window**
The weekly display filters `!date.isBefore(weekStart)`.
In production, swap `UsageRepository` for a DB/file-backed store — no service code changes needed.

---

## What to say to the interviewer

1. **Entities first** — `App` is what you track; `AppUsage` is a timestamped record of usage on a date. Keeping them separate lets you query any time range without re-parsing timestamps.

2. **Repository = simulated DB** — `UsageRepository` uses `HashMap<appId, List<AppUsage>>` so all usages for an app are grouped together — avoids scanning every record for each stat query.

3. **7-day window** — the weekly filter (`!isBefore(weekStart)`) handles this. To truly persist across reboots, swap the in-memory HashMap store for file/DB I/O in the repository — the service layer needs zero changes.

4. **Daily limit check is inline** — no Observer pattern needed here. If the interviewer asks about extensibility (e.g. push notifications, email alerts), that's when you introduce Observer — only pull in a pattern when the requirement actually needs it.

5. **activeSessions map** — `HashMap<appId, Long>` tracks start time in ms for in-progress sessions. Duration is computed only on stop, so we never store partial data.
