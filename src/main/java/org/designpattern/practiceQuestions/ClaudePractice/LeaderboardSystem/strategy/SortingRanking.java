package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingRanking implements RankingStrategy {
    @Override
    public List<Player> getTopK(List<Player> players, int k) {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparingInt(Player::getScore).reversed());
        return sorted.subList(0, Math.min(k, sorted.size()));
    }
}
