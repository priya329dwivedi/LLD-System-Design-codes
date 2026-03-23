package org.designpattern.practiceQuestions.TopKSongsAPI.observer;

import org.designpattern.practiceQuestions.TopKSongsAPI.Song;

public class AnalyticsSystem implements PlayEventObserver {
    @Override
    public void onSongPlayed(Song song) {
        System.out.println("[Analytics] Logged play: " + song.getName() + " (total: " + song.getPlayCount() + ")");
    }
}
