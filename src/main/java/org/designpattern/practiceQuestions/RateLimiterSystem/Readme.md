# Rate Limiter System - LLD

## Problem Statement

Design a Rate Limiter that controls the number of API requests a user can make within a time window. If a user exceeds the limit, the request should be rejected. The system should support multiple rate limiting algorithms and notify systems when a user is throttled.

## Functional Requirements

- **isAllowed(userId)** — Returns true if the user can make a request, false if rate limited.
- Support **different rate limiting strategies**:
  - **Fixed Window** — Count requests in a fixed time window (e.g., 100 requests per minute).
  - **Sliding Window** — Rolling time window for smoother rate limiting.
  - **Token Bucket** — Tokens refill at a fixed rate; each request consumes a token.
- Each user should have an **independent rate limit**.
- Notify systems when a user is **throttled** or **approaching the limit**.

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Strategy Pattern** | Rate limiting algorithm — Fixed Window, Sliding Window, Token Bucket | Swap algorithm without changing the API |
| **Observer Pattern** | Throttle events — notify alerting, logging, analytics when users are rate limited | Decouple rate limiting from monitoring |
| **Factory Pattern** | Create the right limiter strategy based on API tier (free, premium, enterprise) | Centralize strategy creation based on user type |
| **Singleton Pattern** | RateLimiterService — shared across all API endpoints | One limiter instance for consistency |
