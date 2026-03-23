package org.designpattern.practiceQuestions.TopKSongsAPI.strategy;

import org.designpattern.practiceQuestions.TopKSongsAPI.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MinHeapStrategy implements TopKStrategy {
    @Override
    public List<Song> getTopK(List<Song> songs, int k) {
        PriorityQueue<Song> minHeap = new PriorityQueue<>(Comparator.comparingInt(Song::getPlayCount));
        for(Song song : songs){
            minHeap.offer(song);
            if(minHeap.size() > k){
                minHeap.poll();
            }
        }
        List<Song> result = new ArrayList<>(minHeap);
        result.sort(Comparator.comparingInt(Song::getPlayCount).reversed());
        return result;
    }
}
