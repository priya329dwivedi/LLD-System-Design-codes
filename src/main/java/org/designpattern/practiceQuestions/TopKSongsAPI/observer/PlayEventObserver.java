package org.designpattern.practiceQuestions.TopKSongsAPI.observer;

import org.designpattern.practiceQuestions.TopKSongsAPI.Song;

public interface PlayEventObserver {
    void onSongPlayed(Song song);
}
