package org.designpattern.practiceQuestions.TopKSongsAPI;

import lombok.Getter;

@Getter
public class Song {
    String id;
    String name;
    String artist;
    int playCount;

    public Song(String id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.playCount = 0;
    }

    public void incrementPlayCount() {
        this.playCount++;
    }

    @Override
    public String toString() {
        return name + " by " + artist + " [plays: " + playCount + "]";
    }
}
