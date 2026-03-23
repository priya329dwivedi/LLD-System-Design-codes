package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final String startTime;
    private final String date;
    private final Map<String, ShowSeat> showSeats;  // seatId → ShowSeat

    public Show(String id, Movie movie, Screen screen, String startTime, String date) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.date = date;
        this.showSeats = new LinkedHashMap<>();
        for (Seat seat : screen.getSeats()) {
            showSeats.put(seat.getId(), new ShowSeat(seat));
        }
    }

    public ShowSeat getShowSeat(String seatId) {
        return showSeats.get(seatId);
    }

    public void displaySeatLayout() {
        System.out.println("\n  === " + movie.getName() + " | " + date + " " + startTime + " ===");
        System.out.println("  ────────── SCREEN ──────────\n");

        String currentRow = "";
        for (ShowSeat showSeat : showSeats.values()) {
            String row = showSeat.getSeat().getRow();
            if (!row.equals(currentRow)) {
                if (!currentRow.isEmpty()) System.out.println();
                System.out.print("  Row " + row + "  ");
                currentRow = row;
            }
            System.out.printf("[%2s]", showSeat.getDisplayChar());
        }
        System.out.println("\n");
        System.out.println("  [R]=Regular  [P]=Premium  [V]=Recliner  [XX]=Booked  [LK]=Locked");
    }

    public List<ShowSeat> getAvailableSeats() {
        List<ShowSeat> available = new ArrayList<>();
        for (ShowSeat showSeat : showSeats.values()) {
            showSeat.releaseIfExpired();
            if (showSeat.getStatus() == SeatStatus.AVAILABLE) {
                available.add(showSeat);
            }
        }
        return available;
    }
}
