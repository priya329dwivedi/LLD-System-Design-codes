package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model;

import lombok.Getter;

@Getter
public class Seat {
    private final String id;
    private final String row;
    private final int col;
    private final SeatType seatType;

    public Seat(String id, String row, int col, SeatType seatType) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.seatType = seatType;
    }

    public String getLabel() {
        return row + col;
    }
}
