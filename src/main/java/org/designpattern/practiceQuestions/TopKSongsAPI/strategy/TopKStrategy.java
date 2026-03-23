package org.designpattern.practiceQuestions.TopKSongsAPI.strategy;

import org.designpattern.practiceQuestions.TopKSongsAPI.Song;

import java.util.List;

public interface TopKStrategy {
    List<Song> getTopK(List<Song> songs, int k);
}
