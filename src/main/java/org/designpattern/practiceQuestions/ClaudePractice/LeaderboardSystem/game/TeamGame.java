package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy.RankingStrategy;

public class TeamGame extends Game {
    public TeamGame(RankingStrategy rankingStrategy) {
        super("Team", rankingStrategy);
    }
}
