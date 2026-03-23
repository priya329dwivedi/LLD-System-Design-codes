package org.designpattern.practiceQuestions.LeaderboardSystem.Strategy;

import org.designpattern.practiceQuestions.LeaderboardSystem.UtilityClass.Player;

import java.util.List;

public interface LeaderBoard {
    List<Player> getTopKPlayers(List<Player> players,int k);
}
