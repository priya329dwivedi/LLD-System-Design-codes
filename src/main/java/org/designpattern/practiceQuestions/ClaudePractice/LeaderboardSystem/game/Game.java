package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model.Player;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.observer.LeaderboardObserver;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy.RankingStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Game {
    private final String name;
    private final List<Player> players;
    private final List<LeaderboardObserver> observers;
    private RankingStrategy rankingStrategy;

    public Game(String name, RankingStrategy rankingStrategy) {
        this.name = name;
        this.players = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.rankingStrategy = rankingStrategy;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addObserver(LeaderboardObserver observer) {
        observers.add(observer);
    }

    public void setRankingStrategy(RankingStrategy rankingStrategy) {
        this.rankingStrategy = rankingStrategy;
    }

    public void submitScore(Player player, int points) {
        int oldHighScore = player.getHighScore();
        player.addScore(points);
        if (player.getScore() > oldHighScore) {
            notifyPersonalBest(player, player.getScore());
        }
    }

    public List<Player> getTopK(int k) {
        List<Player> topK = rankingStrategy.getTopK(players, k);
        int rank = 1;
        for (Player player : topK) {
            if (rank <= 10) {
                notifyTopRank(player, rank);
            }
            rank++;
        }
        return topK;
    }

    public int getPlayerRank(Player player) {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparingInt(Player::getScore).reversed());
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).equals(player)) {
                return i + 1;
            }
        }
        return -1;
    }

    public void printTopK(int k) {
        System.out.println("=== " + name + " Leaderboard (Top " + k + ") ===");
        List<Player> topK = getTopK(k);
        int rank = 1;
        for (Player player : topK) {
            System.out.println("  Rank " + rank + ": " + player);
            rank++;
        }
    }

    private void notifyTopRank(Player player, int rank) {
        for (LeaderboardObserver observer : observers) {
            observer.onTopRankAchieved(player, rank);
        }
    }

    private void notifyPersonalBest(Player player, int newScore) {
        for (LeaderboardObserver observer : observers) {
            observer.onPersonalBest(player, newScore);
        }
    }

    public String getName() {
        return name;
    }
}
