package org.designpattern.practiceQuestions.TopKSongsAPI;

import lombok.Setter;
import org.designpattern.practiceQuestions.TopKSongsAPI.observer.PlayEventObserver;
import org.designpattern.practiceQuestions.TopKSongsAPI.strategy.TopKStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongService {
    private static SongService instance;
    private Map<String, Song> songs;
    private final List<PlayEventObserver> observers;
    @Setter
    private TopKStrategy topKStrategy;

    private SongService(TopKStrategy topKStrategy) {
        this.songs = new HashMap<>();
        this.observers = new ArrayList<>();
        this.topKStrategy = topKStrategy;
    }

    public static SongService getInstance(TopKStrategy topKStrategy) {
        if(instance == null){
            instance = new SongService(topKStrategy);
        }
        return instance;
    }

    public void addSong(Song song) {
        songs.put(song.getId(), song);
    }

    public void addObserver(PlayEventObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PlayEventObserver observer) {
        observers.remove(observer);
    }

    public void playSong(String songId) {
        Song song = songs.get(songId);
        if(song == null){
            System.out.println("Song not found: " + songId);
            return;
        }
        song.incrementPlayCount();
        notifyObservers(song);
    }

    private void notifyObservers(Song song) {
        for(PlayEventObserver observer : observers){
            observer.onSongPlayed(song);
        }
    }

    public List<Song> getTopKSongs(int k) {
        List<Song> allSongs = new ArrayList<>(songs.values());
        return topKStrategy.getTopK(allSongs, k);
    }
}
