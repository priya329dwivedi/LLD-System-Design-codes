package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model.Player;

import java.util.List;

public interface RankingStrategy {
    List<Player> getTopK(List<Player> players, int k);
}
