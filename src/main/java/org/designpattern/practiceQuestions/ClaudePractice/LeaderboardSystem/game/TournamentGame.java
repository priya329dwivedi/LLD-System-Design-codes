package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy.RankingStrategy;

public class TournamentGame extends Game {
    public TournamentGame(RankingStrategy rankingStrategy) {
        super("Tournament", rankingStrategy);
    }
}
