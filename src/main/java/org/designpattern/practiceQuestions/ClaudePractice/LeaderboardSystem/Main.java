package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.factory.GameFactory;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game.Game;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model.Player;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.observer.InAppNotifier;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.observer.SmsNotifier;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy.SortingRanking;

public class Main {
    public static void main(String[] args) {
        // Create players
        Player priya = new Player("Priya");
        Player haotami = new Player("Haotami");
        Player yogita = new Player("Yogita");
        Player akuta = new Player("Akuta");

        // ========== Game 1: Solo (MinHeap strategy) ==========
        Game soloGame = GameFactory.createGame("Solo");
        soloGame.addPlayer(priya);
        soloGame.addPlayer(haotami);
        soloGame.addPlayer(yogita);

        // Add observers to the game (not to individual players)
        soloGame.addObserver(new SmsNotifier("7905398325"));
        soloGame.addObserver(new InAppNotifier("device-001"));

        // Submit scores — observers get notified on personal best
        soloGame.submitScore(priya, 100);
        soloGame.submitScore(haotami, 200);
        soloGame.submitScore(yogita, 600);
        soloGame.submitScore(priya, 150);  // Priya now has 250, new personal best

        // Get top 2 — observers get notified on top rank
        System.out.println();
        soloGame.printTopK(2);

        // Get specific player's rank
        System.out.println("\nPriya's rank: " + soloGame.getPlayerRank(priya));

        // ========== Game 2: Team (MinHeap, then switch to Sorting) ==========
        System.out.println("\n========== Game 2 ==========\n");

        Game teamGame = GameFactory.createGame("Team");
        teamGame.addPlayer(haotami);
        teamGame.addPlayer(yogita);
        teamGame.addPlayer(akuta);

        teamGame.addObserver(new SmsNotifier("8905398325"));
        teamGame.addObserver(new InAppNotifier("device-002"));

        teamGame.submitScore(haotami, 500);
        teamGame.submitScore(yogita, 300);
        teamGame.submitScore(akuta, 800);

        System.out.println();
        teamGame.printTopK(2);
        System.out.println("\nAkuta's rank: " + teamGame.getPlayerRank(akuta));

        // Switch strategy at runtime
        System.out.println("\n--- Switching to SortingRanking ---\n");
        teamGame.setRankingStrategy(new SortingRanking());
        teamGame.printTopK(3);
    }
}
