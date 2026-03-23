package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Booking {
    private final String id;
    private final User user;
    private final Show show;
    private final List<ShowSeat> seats;
    private final double totalAmount;
    private BookingStatus status;

    public Booking(String id, User user, Show show, List<ShowSeat> seats, double totalAmount) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.status = BookingStatus.PENDING;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking " + id + " | " + show.getMovie().getName() + " | Rs " + totalAmount + " | " + status;
    }
}
