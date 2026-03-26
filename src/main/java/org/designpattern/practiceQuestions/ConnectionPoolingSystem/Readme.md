# Connection Pooling System — Per-Repository Isolation with Lifecycle Management

## Problem Statement

Design a connection pooling system where each external repository (connector) gets its own isolated pool of reusable connections. The system should manage connection lifecycle (acquire, use, release, cleanup, shutdown), prevent resource exhaustion via rate limiting, and handle graceful shutdown of pools with in-flight requests.

**Real-world context**: 64 connectors making HTTP/SOAP calls to external systems. Without pooling, every operation opens a new TCP+TLS handshake (~300ms). Under load, file descriptors exhaust, and one slow external system starves all others.

## Functional Requirements

- **Create pool per repository** — Each external system gets its own isolated connection pool (max 100 connections).
- **Acquire connection** — Get a reusable connection from the pool (avoid TLS handshake overhead).
- **Release connection** — Return connection to pool after use. Force-release on error.
- **Rate limit per repository** — Throttle request rate BEFORE accessing pool to prevent pool exhaustion.
- **Idle connection cleanup** — Daemon thread closes connections idle > 30 seconds every 30s.
- **Graceful shutdown** — When repository scope ends: immediate shutdown if no in-flight requests, otherwise poll every 10s with 10-minute force timeout.
- **Monitor pool health** — Track active, idle, pending connections per pool.

## Key Entities & Schema

| Entity | Fields |
|---|---|
| **Connection** | id, repositoryId, status (IDLE/ACTIVE/EXPIRED), createdAt, lastUsedAt |
| **ConnectionPool** | repositoryId, maxSize, connections, idleTimeout, activeCount, idleCount |
| **Repository** | id, name, baseUrl, maxConnections, rateLimit |
| **RequestThrottle** | repositoryId, maxRequestsPerSecond, permits |
| **PoolMonitor** | pools (tracked via WeakReferences), cleanupIntervalMs |
| **ShutdownManager** | pendingShutdowns, forceTimeoutMs |

### Relationships
- Repository → has one → ConnectionPool
- Repository → has one → RequestThrottle
- ConnectionPool → has many → Connections
- PoolMonitor (singleton) → tracks many → ConnectionPools
- ShutdownManager (singleton) → manages → pending pool shutdowns

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Singleton Pattern** | PoolMonitor (global daemon), ShutdownManager (global daemon) | One monitor/shutdown manager per JVM |
| **Factory Pattern** | ConnectionPoolFactory — creates pool + throttle per repository | Encapsulate pool creation with correct config |
| **Strategy Pattern** | RateLimitStrategy — FixedRate, TokenBucket, NoLimit | Swap throttling algorithm per repository tier |
| **Observer Pattern** | PoolObserver — HealthLogger, MetricsCollector, ExhaustionAlert | Notify on pool events (exhaustion, shutdown, leak detected) |

## Connection Lifecycle

```
acquire()         use()           release()        cleanup()       shutdown()
    │                │                │                │                │
    v                v                v                v                v
┌────────┐    ┌────────────┐   ┌──────────┐    ┌──────────┐    ┌──────────┐
│ IDLE   │───>│  ACTIVE    │──>│  IDLE    │───>│ EXPIRED  │───>│ CLOSED   │
│(in pool│    │ (leased to │   │(returned │    │(idle>30s │    │(pool shut│
│ ready) │    │  request)  │   │ to pool) │    │ cleaned) │    │  down)   │
└────────┘    └─────┬──────┘   └──────────┘    └──────────┘    └──────────┘
                    │
               on error
                    │
                    v
              ┌──────────┐
              │ ABORTED  │──> force returned to pool / destroyed
              └──────────┘
```

## Architecture Diagram

```
┌─────────── Global (JVM Singleton) ──────────────────────────────┐
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  PoolMonitor (Singleton, daemon thread)                  │   │
│  │  - tracks pools via WeakReference list                   │   │
│  │  - every 30s: close expired + close idle > 30s           │   │
│  │  - auto-removes GC'd pool references                     │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  ShutdownManager (Singleton, daemon thread)              │   │
│  │  - pending shutdowns map: pool → timestamp               │   │
│  │  - every 10s: if leased==0 && pending==0 → shutdown      │   │
│  │  - force shutdown after 10 minutes                       │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  ConnectionPoolFactory (Factory Pattern)                  │   │
│  │  + createPool(repository): ConnectionPool                │   │
│  │  - sets maxSize, idle timeout, creates throttle           │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  List<PoolObserver>                    ←── OBSERVER      │   │
│  │  - HealthLogger                                           │   │
│  │  - MetricsCollector                                       │   │
│  │  - ExhaustionAlertNotifier                                │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
         │                    │                    │
         v                    v                    v
┌─── Repository A ───┐ ┌─── Repository B ───┐ ┌─── Repository C ───┐
│                     │ │                     │ │                     │
│ ConnectionPool      │ │ ConnectionPool      │ │ ConnectionPool      │
│  - max: 100         │ │  - max: 100         │ │  - max: 100         │
│  - idle timeout:30s │ │  - idle timeout:30s │ │  - idle timeout:30s │
│  ┌───┬───┬───┐      │ │  ┌───┬───┐          │ │  ┌───┐              │
│  │C1 │C2 │C3 │ ...  │ │  │C1 │C2 │ ...      │ │  │C1 │ ...          │
│  └───┴───┴───┘      │ │  └───┴───┘          │ │  └───┘              │
│                     │ │                     │ │                     │
│ RequestThrottle     │ │ RequestThrottle     │ │ RequestThrottle     │
│  ←── STRATEGY       │ │  ←── STRATEGY       │ │  ←── STRATEGY       │
│  (FixedRate: 50/s)  │ │  (TokenBucket:100/s)│ │  (NoLimit)          │
│                     │ │                     │ │                     │
└─────────────────────┘ └─────────────────────┘ └─────────────────────┘
```

## User Flow Diagram

```
                        +─────────────────────+
                        │  Client Request     │
                        │  (to Repository A)  │
                        +─────────┬───────────+
                                  │
                                  v
                        +─────────────────────+
                        │  Get/Create Pool    │
                        │  for Repository A   │
                        │  (Factory Pattern)  │
                        +─────────┬───────────+
                                  │
                                  v
                        +─────────────────────+
                        │  RequestThrottle    │
                        │  acquirePermit()    │
                        │  (Strategy Pattern) │
                        +─────────┬───────────+
                                  │
                    +─────────────+─────────────+
                    │                           │
                    v                           v
          +─────────────────+        +─────────────────+
          │ Permit Granted  │        │ Rate Limited     │
          │                 │        │ (wait/reject)    │
          +────────┬────────+        +─────────────────+
                   │
                   v
          +─────────────────+
          │ Pool.acquire()  │
          │ Get IDLE conn   │
          │ or create new   │
          +────────┬────────+
                   │
         +─────────+──────────+
         │                    │
         v                    v
+────────────────+   +────────────────+
│ Connection     │   │ Pool Exhausted │
│ Acquired       │   │ (all 100 used) │
│ status=ACTIVE  │   │ Block/Timeout  │
+───────┬────────+   +────────────────+
        │
        v
+────────────────+
│ Execute HTTP   │
│ Request        │
+───────┬────────+
        │
   +────+────+
   │         │
   v         v
+──────+  +──────────+
│  OK  │  │  Error   │
+──┬───+  +────┬─────+
   │           │
   v           v
+──────────+  +──────────────+
│ Release  │  │ Abort        │
│ conn     │  │ Force return │
│ → IDLE   │  │ → destroyed  │
+──────────+  +──────────────+
        │
        v
+───────────────────────────────────────────+
│ Background: PoolMonitor (every 30s)       │
│   - close connections idle > 30s          │
│   - close expired connections             │
│   - remove GC'd pool WeakReferences       │
+───────────────────────────────────────────+
        │
        v (when repository scope ends)
+───────────────────────────────────────────+
│ Background: ShutdownManager               │
│   - leased==0 && pending==0 → shutdown    │
│   - else poll every 10s                   │
│   - force shutdown after 10 minutes       │
│   - notify observers                      │
+───────────────────────────────────────────+
```

## Trade-offs & Solutions

| Trade-off | Problem | Solution |
|---|---|---|
| Per-repo pools = more memory | 64 repos × 100 max = 6400 potential connections | WeakReference tracking — pools GC'd when scope ends. Idle closed after 30s. Rarely hits 100 in practice |
| Pool exhaustion = blocking | All 100 leased → new requests block then timeout | Rate limiter BEFORE pool — throttle inflow, don't handle exhaustion after the fact |
| Connection leaks on exceptions | Response entity not consumed → connection never returned | `finally { release(conn) }`. On failure: `abort()` forces return. Monitor thread as safety net |
| Graceful shutdown with in-flight | Destroying pool mid-request → IllegalStateException | Two-phase: immediate if idle, poll 10s intervals, force after 10 minutes |

## Interview Follow-ups to Consider

- Why **per-repository pools** instead of one global pool? (Isolation — slow system can't starve others)
- Why **rate limit before the pool** and not after? (Throttle inflow > handle exhaustion)
- How do you **prevent connection leaks**? (finally block + abort + monitor thread safety net)
- How do you **handle graceful shutdown** with in-flight requests? (Two-phase: check leased count, force after timeout)
- Why **WeakReferences** for pool tracking? (Automatic cleanup when repository scope ends, prevents memory leaks)
- How would you **monitor pool health**? (Observer pattern — log metrics, alert on exhaustion)
