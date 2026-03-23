package org.designpattern.practiceQuestions.ClaudePractice.BookMyShow.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Theatre {
    private final String id;
    private final String name;
    private final String city;
    private final List<Screen> screens;

    public Theatre(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.screens = new ArrayList<>();
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }
}
