package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.game;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy.RankingStrategy;

public class SoloGame extends Game {
    public SoloGame(RankingStrategy rankingStrategy) {
        super("Solo", rankingStrategy);
    }
}
