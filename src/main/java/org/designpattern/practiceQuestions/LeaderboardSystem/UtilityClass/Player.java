package org.designpattern.practiceQuestions.LeaderboardSystem.UtilityClass;

import lombok.Getter;
import lombok.Setter;
import org.designpattern.practiceQuestions.LeaderboardSystem.ObservorPattern.Observor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {
    String name;
    int score=0;
    @Setter
    int rank;
    int highScoreTillNow;
    List<Observor> observers;

    public Player(String name){
        this.name=name;
        this.observers= new ArrayList<>();
    }
    public void setScore(int score) {
        if(this.score<score){
            this.highScoreTillNow = score;
            notifyObservers("Personal Best Score "+ score);
        }
        this.score = score;
    }
    public void addObservor(Observor observor){
        observers.add(observor);
    }
    public void notifyObservers(String msg){
        for(Observor observor:observers){
            observor.sendMsg(msg);
        }
    }
}
