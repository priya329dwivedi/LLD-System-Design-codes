package org.designpattern.practiceQuestions.TopKSongsAPI.strategy;

import org.designpattern.practiceQuestions.TopKSongsAPI.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingStrategy implements TopKStrategy {
    @Override
    public List<Song> getTopK(List<Song> songs, int k) {
        List<Song> sorted = new ArrayList<>(songs);
        sorted.sort(Comparator.comparingInt(Song::getPlayCount).reversed());
        return sorted.subList(0, Math.min(k, sorted.size()));
    }
}
