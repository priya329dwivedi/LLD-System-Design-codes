package org.designpattern.practiceQuestions.TopKSongsAPI.observer;

import org.designpattern.practiceQuestions.TopKSongsAPI.Song;

public class RecommendationEngine implements PlayEventObserver {
    @Override
    public void onSongPlayed(Song song) {
        System.out.println("[Recommendation] Updating suggestions based on: " + song.getName());
    }
}
