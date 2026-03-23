package org.designpattern.practiceQuestions.LeaderboardSystem.Factory;

import org.designpattern.practiceQuestions.LeaderboardSystem.Game;
import org.designpattern.practiceQuestions.LeaderboardSystem.Strategy.MinHeap;

public class GameType {
    public static Game getGameType(String mode){
        if(mode.equals("Solo")){
            return new Solo(new MinHeap());
        }
        else if(mode.equals("Team")){
            return new Team(new MinHeap());
        }
        else if(mode.equals("Tournament")){
            return new Tournament(new MinHeap());
        }
        else {
            throw new IllegalArgumentException("Game Type does not exist");
        }
    }
}
