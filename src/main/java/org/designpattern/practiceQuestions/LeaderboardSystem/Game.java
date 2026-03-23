package org.designpattern.practiceQuestions.LeaderboardSystem;

import org.designpattern.practiceQuestions.LeaderboardSystem.Strategy.LeaderBoard;
import org.designpattern.practiceQuestions.LeaderboardSystem.UtilityClass.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    List<Player> players;
    private LeaderBoard leaderBoard;
    public Game(LeaderBoard leaderBoardStrategy){
        this.players= new ArrayList<>();
        this.leaderBoard=leaderBoardStrategy;

    }
    public void setLeaderBoardStrategy(LeaderBoard leaderBoardStrategy){
        this.leaderBoard=leaderBoardStrategy;
    }
    public void addPlayer(Player player1){
        players.add(player1);
    }
    public List<Player> calculateLeaderBoard(List<Player> players,int k){
        List<Player> shortListedPlayer=leaderBoard.getTopKPlayers(players,k);
        int rank = 1;
        for (Player player : shortListedPlayer) {
            player.setRank(rank);
            if (rank <= 10) {
                player.notifyObservers("Entered Top Chart with rank: " + rank);
            }
            rank++;
        }
        return shortListedPlayer;
    }
}
