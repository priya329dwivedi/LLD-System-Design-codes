# Amazon Prime Video — Subscription Platform LLD (Interview Style)

## Requirements
1. Plan Management — FREE / BASIC / PREMIUM tiers
2. Subscription Lifecycle — subscribe, upgrade, downgrade, renew, cancel
3. Content Gating — access based on subscription tier
4. Payment Processing — pluggable payment methods (Strategy)
5. Notifications — email/SMS on subscription events (Observer)
6. Subscription History — track all past + current subscriptions per user

---

## Layers

```
Model       →  PlanTier, SubscriptionStatus, Plan, User, Subscription, Content
Repository  →  PlanRepository, UserRepository, SubscriptionRepository, ContentRepository
Strategy    →  PaymentStrategy, CreditCardPayment, UPIPayment
Observer    →  SubscriptionObserver, NotificationService
Service     →  SubscriptionService
Main        →  demo driver
```

---

## Entity Design

```
Plan          id, name, tier (PlanTier), pricePerMonth, billingCycleDays
User          id, name, email
Subscription  id, userId, planId, status, startDate, endDate, autoRenew
Content       id, title, requiredTier (PlanTier)
```

**Relationships:**
- User → has many → Subscriptions (one ACTIVE at a time)
- Subscription → belongs to → one Plan
- Content → requires minimum → PlanTier

---

## Data Modelling

| Repository | Key | Value | Purpose |
|---|---|---|---|
| `PlanRepository` | planId | Plan | Catalogue of available plans |
| `UserRepository` | userId | User | Registered users |
| `SubscriptionRepository` | subscriptionId | Subscription | All subscriptions; `getActiveSub(userId)` loops to find ACTIVE |
| `ContentRepository` | contentId | Content | Content library with tier gates |

---

## API Design (Service methods)

```
subscribe(userId, planId, PaymentStrategy)          → charge + create Subscription + notify
cancel(subscriptionId)                              → mark CANCELLED + notify (access until endDate)
upgrade(subscriptionId, newPlanId, PaymentStrategy) → charge price diff + update planId + notify
downgrade(subscriptionId, newPlanId)                → update planId + notify (no refund in basic flow)
renew(subscriptionId, PaymentStrategy)              → charge + extend endDate + notify
canAccess(userId, contentId)                        → compare user tier vs content.requiredTier
listPlans()                                         → return all plans
showHistory(userId)                                 → all subscriptions for user
```

---

## Content Access Logic

```java
PlanTier userTier = (activeSub == null) ? FREE : plan.tier;
boolean allowed = userTier.ordinal() >= content.requiredTier.ordinal();
// FREE=0, BASIC=1, PREMIUM=2 — ordinal comparison handles all tier checks in one line
```

---

## Subscription State Machine

```
subscribe()  →  ACTIVE
ACTIVE       →  CANCELLED  (cancel)
ACTIVE       →  ACTIVE     (renew — extends endDate)
ACTIVE       →  ACTIVE     (upgrade/downgrade — changes planId)
CANCELLED    →  terminal   (access until endDate)
```

---

## Design Patterns

| Pattern | Where | Why |
|---|---|---|
| **Strategy** | `PaymentStrategy` → `CreditCardPayment`, `UPIPayment` | Swap payment method without changing the service |
| **Observer** | `SubscriptionObserver` → `NotificationService` | Decouple email/SMS notifications from subscription logic |

---

## Interview Follow-ups

- **Prorated billing on upgrade?** — `charge(newPrice - oldPrice) * remainingDays / cycleDays`
- **Prevent double charging?** — Idempotency key on payment (transactionId stored and checked before charging)
- **Payment failure on renewal?** — Mark PAYMENT_PENDING, retry with backoff, then EXPIRED after grace period
- **Free trial?** — Add TRIAL to `SubscriptionStatus`; trial converts to ACTIVE only after first payment
- **Auto-renew?** — Scheduled job loops subscriptions where `autoRenew=true` and `endDate == today`, calls `renew()`

---

## What to say to the interviewer

1. **Entities first** — `Plan` defines tiers; `Subscription` links user + plan + billing dates; `Content` stores the minimum tier needed. These three cover the complete access-control flow.

2. **Tier comparison with enum ordinal** — `PlanTier` is declared `FREE, BASIC, PREMIUM` in order so `userTier.ordinal() >= content.requiredTier.ordinal()` covers all rules in one line — no if-else chain.

3. **Strategy for payment** — pass `CreditCardPayment` or `UPIPayment` at the call site. Adding Wallet = one new class, zero service changes.

4. **Observer for notifications** — `NotificationService` is registered once. Adding `SMSObserver` or `AnalyticsObserver` doesn't touch `SubscriptionService`.

5. **Upgrade charges only the diff** — `upgrade()` calls `processPayment(newPlan.price - oldPlan.price)`. Mention prorated version as a follow-up: multiply by `remainingDays / cycleDays`.
