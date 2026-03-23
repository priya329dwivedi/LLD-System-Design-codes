package org.designpattern.practiceQuestions.LeaderboardSystem.Factory;

import org.designpattern.practiceQuestions.LeaderboardSystem.Game;
import org.designpattern.practiceQuestions.LeaderboardSystem.Strategy.LeaderBoard;
import org.designpattern.practiceQuestions.LeaderboardSystem.UtilityClass.Player;

import java.util.List;

public class Team extends Game {

    public Team( LeaderBoard leaderBoardStrategy) {
        super(leaderBoardStrategy);
    }

    @Override
    public List<Player> calculateLeaderBoard(List<Player> players, int k) {
        return super.calculateLeaderBoard(players, k);
    }
}
