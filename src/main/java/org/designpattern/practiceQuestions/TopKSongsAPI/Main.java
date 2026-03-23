package org.designpattern.practiceQuestions.TopKSongsAPI;

import org.designpattern.practiceQuestions.TopKSongsAPI.observer.AnalyticsSystem;
import org.designpattern.practiceQuestions.TopKSongsAPI.observer.LeaderboardUpdater;
import org.designpattern.practiceQuestions.TopKSongsAPI.observer.RecommendationEngine;
import org.designpattern.practiceQuestions.TopKSongsAPI.strategy.MinHeapStrategy;
import org.designpattern.practiceQuestions.TopKSongsAPI.strategy.SortingStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Singleton SongService with MinHeap strategy
        SongService songService = SongService.getInstance(new MinHeapStrategy());

        // Add observers
        songService.addObserver(new AnalyticsSystem());
        songService.addObserver(new LeaderboardUpdater());
        songService.addObserver(new RecommendationEngine());

        // Add songs
        songService.addSong(new Song("1", "Blinding Lights", "The Weeknd"));
        songService.addSong(new Song("2", "Shape of You", "Ed Sheeran"));
        songService.addSong(new Song("3", "Bohemian Rhapsody", "Queen"));
        songService.addSong(new Song("4", "Tum Hi Ho", "Arijit Singh"));
        songService.addSong(new Song("5", "Dandelions", "Ruth B"));

        // Simulate plays
        System.out.println("========== Playing Songs ==========");
        songService.playSong("1");
        songService.playSong("1");
        songService.playSong("1");
        songService.playSong("2");
        songService.playSong("2");
        songService.playSong("3");
        songService.playSong("4");
        songService.playSong("4");
        songService.playSong("4");
        songService.playSong("4");
        songService.playSong("5");

        // Fetch top 3 using MinHeap strategy
        System.out.println("\n========== Top 3 Songs (MinHeap) ==========");
        List<Song> top3 = songService.getTopKSongs(3);
        for(Song song : top3){
            System.out.println(song);
        }

        // Switch strategy to Sorting at runtime
        songService.setTopKStrategy(new SortingStrategy());
        System.out.println("\n========== Top 2 Songs (Sorting) ==========");
        List<Song> top2 = songService.getTopKSongs(2);
        for(Song song : top2){
            System.out.println(song);
        }
    }
}
