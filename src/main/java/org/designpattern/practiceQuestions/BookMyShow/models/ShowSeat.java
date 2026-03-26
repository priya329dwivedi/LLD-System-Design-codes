package org.designpattern.practiceQuestions.BookMyShow.models;

public class ShowSeat {
    Seat seat;
    SeatType seatType;
    SeatStatus seatStatus;

    public ShowSeat(Seat seat, SeatType seatType) {
        this.seat = seat;
        this.seatType = seatType;
        this.seatStatus = SeatStatus.AVAILABLE;
    }
}
