package org.designpattern.practiceQuestions.LeaderboardSystem.Strategy;

import org.designpattern.practiceQuestions.LeaderboardSystem.UtilityClass.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SimpleSort implements LeaderBoard{
    @Override
    public List<Player> getTopKPlayers(List<Player> players, int k) {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparingInt(Player::getScore).reversed());
        List<Player> topK = sorted.subList(0, Math.min(k, sorted.size()));
        return topK;
    }
}
