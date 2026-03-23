package org.designpattern.practiceQuestions.TopKSongsAPI.observer;

import org.designpattern.practiceQuestions.TopKSongsAPI.Song;

public class LeaderboardUpdater implements PlayEventObserver {
    @Override
    public void onSongPlayed(Song song) {
        System.out.println("[Leaderboard] Updated ranking for: " + song.getName());
    }
}
