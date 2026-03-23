package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model;

import lombok.Getter;

@Getter
public class Player {
    private final String name;
    private int score;
    private int highScore;

    public Player(String name) {
        this.name = name;
    }

    public void addScore(int points) {
        this.score += points;
        if (this.score > this.highScore) {
            this.highScore = this.score;
        }
    }

    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }
}
