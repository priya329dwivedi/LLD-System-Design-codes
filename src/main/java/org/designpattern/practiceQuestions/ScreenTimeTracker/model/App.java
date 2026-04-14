package org.designpattern.practiceQuestions.ScreenTimeTracker.model;

public class App {
    public String id;
    public String name;
    public AppCategory category;

    public App(String id, String name, AppCategory category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
