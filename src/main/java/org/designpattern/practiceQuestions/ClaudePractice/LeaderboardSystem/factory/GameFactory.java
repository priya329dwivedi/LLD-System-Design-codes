package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.factory;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game.Game;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game.SoloGame;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game.TeamGame;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game.TournamentGame;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy.MinHeapRanking;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy.RankingStrategy;
import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy.SortingRanking;

public class GameFactory {
    public static Game createGame(String mode) {
        switch (mode) {
            case "Solo":
                return new SoloGame(new MinHeapRanking());
            case "Team":
                return new TeamGame(new MinHeapRanking());
            case "Tournament":
                return new TournamentGame(new SortingRanking());
            default:
                throw new IllegalArgumentException("Unknown game mode: " + mode);
        }
    }
}
