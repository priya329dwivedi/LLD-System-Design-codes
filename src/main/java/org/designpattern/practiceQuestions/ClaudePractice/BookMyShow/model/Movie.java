package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model;

import lombok.Getter;

@Getter
public class Movie {
    private final String id;
    private final String name;
    private final String genre;
    private final int durationMinutes;

    public Movie(String id, String name, String genre, int durationMinutes) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return name + " (" + genre + ", " + durationMinutes + " min)";
    }
}
