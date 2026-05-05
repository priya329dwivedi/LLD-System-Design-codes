# Interview Problem: Rate-Limited Artifact Sync Client

## Context

You are building a sync engine that fetches artifacts from a third-party REST API (think Jira,
Tosca, or similar). A full sync can involve 1000+ artifact fetches. The remote API enforces a
rate limit and returns HTTP 429 when exceeded — along with a `Retry-After` header indicating
how long to wait before retrying.

Without protection, a full sync hammers the API, triggers 429 cascades, and fails entirely.
Your job is to make the sync self-healing.

---

## Functional Requirements

1. Fetch a list of artifacts from a remote API, one at a time.
2. Throttle outgoing requests to stay within a configured rate limit (e.g., 2 req/sec).
3. On HTTP 429, retry after the server's `Retry-After` delay (in ms). If no header, use
   your own backoff formula.
4. Stop retrying after a maximum number of attempts and record the artifact as failed.
5. If the remote API is consistently failing, stop sending requests for a cooldown period
   (circuit breaker), then probe again.
6. Log success/failure per artifact. Partial success is acceptable — one failing artifact
   must not abort the full sync.

---

## Non-Functional Requirements

- Thread-safe: multiple sync jobs may run concurrently against different API endpoints.
- The throttle acquire must be interruptible — sync jobs can be cancelled mid-flight.
- The transport layer must be swappable for testing without changing sync logic.

---

## Constraints (for this session)

- In-memory only — no DB, no real HTTP.
- Standard Java (`java.util.concurrent.*`, `ReentrantLock`) — no Guava, no Spring.
- Single file, ~150–200 lines. Runnable `main()` that demonstrates a 429 being hit
  and recovered from.

---

## Clarifying Questions to Ask

- Is the rate limit global or per-endpoint? (Per-endpoint — each integration has its own bucket.)
- Does the server always send `Retry-After`? (No — treat it as optional; fall back to your own formula.)
- Should failed artifacts be retried later or skipped? (Skipped after max attempts.)
- What triggers the circuit breaker — only 429s, or all errors? (All non-success responses.)

---

## What the Interviewer Is Looking For

| Signal | What it shows |
|--------|---------------|
| Two-layer design (proactive throttle + reactive retry) | Understands the problem has two distinct sub-problems |
| Non-bursting token bucket | Knows the burst-accumulation failure mode |
| `Retry-After` priority over own formula | Defers to server knowledge |
| Cubic vs exponential backoff | Thought about backoff shape, not just "use 2^n" |
| Interruptible acquire | Knows blocking calls can be made interruptible via `Future.get()` |
| Circuit breaker | Thinks about downstream health, not just retries |
| Strategy for transport | Knows to separate I/O from logic |
