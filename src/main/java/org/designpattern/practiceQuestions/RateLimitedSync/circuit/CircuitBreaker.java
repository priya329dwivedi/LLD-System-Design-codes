package org.designpattern.practiceQuestions.RateLimitedSync.circuit;

// Three-state circuit breaker: CLOSED → OPEN → HALF_OPEN → CLOSED
//
// CLOSED:    normal operation — all requests go through.
// OPEN:      downstream is unhealthy — requests are rejected immediately (fail fast).
// HALF_OPEN: cooldown elapsed — one probe request is allowed to test recovery.
public class CircuitBreaker {

    private enum State { CLOSED, OPEN, HALF_OPEN }

    private static final int  FAILURE_THRESHOLD = 3;
    private static final long COOLDOWN_MS       = 5_000;

    private State state              = State.CLOSED;
    private int   consecutiveFailures = 0;
    private long  openedAt           = 0;

    public synchronized boolean allowRequest() {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - openedAt >= COOLDOWN_MS) {
                state = State.HALF_OPEN;  // cooldown elapsed — allow one probe
                System.out.println("  [CB] → HALF_OPEN (probing)");
                return true;
            }
            return false; // still in cooldown
        }
        return true; // CLOSED or HALF_OPEN
    }

    public synchronized void onSuccess() {
        consecutiveFailures = 0;
        if (state != State.CLOSED) System.out.println("  [CB] → CLOSED");
        state = State.CLOSED;
    }

    public synchronized void onFailure() {
        consecutiveFailures++;
        if (consecutiveFailures >= FAILURE_THRESHOLD) {
            state    = State.OPEN;
            openedAt = System.currentTimeMillis();
            System.out.println("  [CB] → OPEN (" + consecutiveFailures + " consecutive failures)");
        }
    }
}
