package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model;

import lombok.Getter;

@Getter
public class ShowSeat {
    private final Seat seat;
    private SeatStatus status;
    private String lockedByUserId;
    private long lockedAt;

    private static final long LOCK_TIMEOUT_MS = 300000;  // 5 minutes

    public ShowSeat(Seat seat) {
        this.seat = seat;
        this.status = SeatStatus.AVAILABLE;
    }

    public synchronized boolean lock(String userId) {
        releaseIfExpired();
        if (status != SeatStatus.AVAILABLE) return false;
        this.status = SeatStatus.LOCKED;
        this.lockedByUserId = userId;
        this.lockedAt = System.currentTimeMillis();
        return true;
    }

    public synchronized boolean confirmBooking(String userId) {
        if (status != SeatStatus.LOCKED || !userId.equals(lockedByUserId)) return false;
        this.status = SeatStatus.BOOKED;
        return true;
    }

    public synchronized void release() {
        this.status = SeatStatus.AVAILABLE;
        this.lockedByUserId = null;
        this.lockedAt = 0;
    }

    public synchronized void releaseIfExpired() {
        if (status == SeatStatus.LOCKED && System.currentTimeMillis() - lockedAt >= LOCK_TIMEOUT_MS) {
            release();
        }
    }

    public String getDisplayChar() {
        releaseIfExpired();
        switch (status) {
            case AVAILABLE:
                switch (seat.getSeatType()) {
                    case REGULAR: return "R" + seat.getCol();
                    case PREMIUM: return "P" + seat.getCol();
                    case RECLINER: return "V" + seat.getCol();
                }
            case LOCKED: return "LK";
            case BOOKED: return "XX";
            default: return "??";
        }
    }
}
