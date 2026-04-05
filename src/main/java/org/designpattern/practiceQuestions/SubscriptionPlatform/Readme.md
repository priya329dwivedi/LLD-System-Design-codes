# Subscription Platform — Amazon Prime Video LLD

## Problem Statement

Design a subscription platform (like Amazon Prime Video) that allows users to subscribe to plans, manage billing cycles, access content based on subscription tier, handle upgrades/downgrades, and process renewals/cancellations. The system should support multiple plan types with different content access levels, payment processing, and user notifications.

## Functional Requirements

- **Browse plans** — View available subscription plans (Free, Basic, Premium).
- **Subscribe to a plan** — User selects a plan, provides payment, subscription starts.
- **Access content** — Content access is gated by subscription tier (Free=ads+limited, Basic=no ads, Premium=4K+downloads).
- **Upgrade/Downgrade plan** — Switch plans mid-cycle with prorated billing.
- **Auto-renew subscription** — Charge user automatically at end of billing cycle.
- **Cancel subscription** — Cancel with access until end of current billing period.
- **Payment processing** — Support multiple payment methods (CreditCard, UPI, Wallet).
- **Notify users** — On subscription created, renewed, cancelled, payment failed, plan changed.
- **View subscription history** — Past and current subscriptions for a user.

## Key Entities & Schema

| Entity | Fields |
|---|---|
| **User** | id, name, email, phone |
| **Plan** | id, name, tier (FREE/BASIC/PREMIUM), price, billingCycle (MONTHLY/YEARLY), features |
| **Subscription** | id, userId, planId, status (ACTIVE/CANCELLED/EXPIRED/PAUSED), startDate, endDate, autoRenew |
| **Payment** | id, subscriptionId, amount, method, status (SUCCESS/FAILED/PENDING), transactionDate |
| **Content** | id, title, genre, requiredTier (FREE/BASIC/PREMIUM) |
| **Invoice** | id, subscriptionId, amount, billingPeriodStart, billingPeriodEnd, status |

### Relationships
- User → has many → Subscriptions
- Subscription → belongs to → User + Plan
- Subscription → has many → Payments
- Subscription → has many → Invoices
- Content → requires → Plan tier (access gate)

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Strategy Pattern** | Billing — monthly, yearly, prorated upgrade/downgrade calculation | Swap billing logic per plan type |
| **Factory Pattern** | Payment methods (CreditCard/UPI/Wallet), Plan creation | Create the right object based on type |
| **Observer Pattern** | Notify on subscribe, renew, cancel, payment failed (Email, SMS, Push) | Decouple subscription lifecycle from notifications |
| **State Pattern** | Subscription states — ACTIVE → CANCELLED → EXPIRED, ACTIVE → PAUSED → ACTIVE | Clean state transitions with behavior per state |
| **Singleton Pattern** | SubscriptionService — one shared instance managing all subscriptions | Consistent billing and state management |

## API Design

```
POST   /api/plans                          → List all available plans
POST   /api/subscribe                      → Create new subscription (userId, planId, paymentMethod)
POST   /api/subscription/{id}/cancel       → Cancel subscription
POST   /api/subscription/{id}/upgrade      → Upgrade plan (newPlanId)
POST   /api/subscription/{id}/downgrade    → Downgrade plan (newPlanId)
GET    /api/user/{id}/subscriptions        → Get user's subscription history
GET    /api/content/{id}/access            → Check if user can access content (userId, contentId)
POST   /api/subscription/{id}/renew        → Process renewal (auto or manual)
```

## Subscription State Diagram

```
                    subscribe()
                        │
                        v
                  ┌──────────┐
         ┌───────│  ACTIVE   │───────┐
         │       └─────┬─────┘       │
         │             │             │
    cancel()      auto-renew     pause()
         │             │             │
         │        ┌────+────┐        │
         │        │         │        v
         │        v         v   ┌─────────┐
         │   ┌────────┐  ┌──────┤ PAUSED  │
         │   │RENEWED │  │FAILED│         │
         │   │(ACTIVE)│  │      └────┬────┘
         │   └────────┘  v          │
         │          ┌─────────┐  resume()
         v          │ PAYMENT │     │
    ┌──────────┐    │ FAILED  │     │
    │CANCELLED │    └────┬────┘     │
    │(access   │         │          │
    │ till end)│    retry/expire     │
    └────┬─────┘         │          │
         │               v          │
         │          ┌─────────┐     │
         └─────────>│ EXPIRED │<────┘
         (end date) └─────────┘  (timeout)
```

## Content Access Flow

```
        User requests content
                │
                v
        ┌───────────────┐
        │ Get user's    │
        │ active sub    │
        └───────┬───────┘
                │
          ┌─────+─────┐
          │           │
          v           v
    ┌──────────┐  ┌──────────┐
    │ Has sub  │  │ No sub   │
    └────┬─────┘  │ (FREE    │
         │        │  content) │
         v        └──────────┘
    ┌──────────────────┐
    │ Check:           │
    │ user.plan.tier   │
    │ >= content.tier  │
    └───────┬──────────┘
            │
      ┌─────+─────┐
      │           │
      v           v
  ┌───────┐  ┌─────────┐
  │GRANTED│  │ DENIED  │
  │(play) │  │(show    │
  └───────┘  │ upgrade)│
             └─────────┘
```

## Interview Follow-ups to Consider

- How do you handle **prorated billing** on upgrade/downgrade? (Strategy Pattern — calculate remaining days, credit/charge difference)
- How do you prevent **double charging** on auto-renew? (Idempotent payment processing with transaction ID)
- How do you handle **payment failure** during renewal? (Retry with backoff, notify user, grace period, then expire)
- How would you implement **free trial**? (Subscription state: TRIAL → ACTIVE on first payment, EXPIRED if no payment)
- How do you **gate content** by subscription tier? (Content has requiredTier, check user's plan tier >= required)
- How would you handle **concurrent upgrade requests**? (Synchronized on subscription, optimistic locking)
