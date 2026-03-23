package org.designpattern.practiceQuestions.LeaderboardSystem.Strategy;

import org.designpattern.practiceQuestions.LeaderboardSystem.UtilityClass.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MinHeap implements LeaderBoard{
    @Override
    public List<Player> getTopKPlayers(List<Player> players,int k) {
        PriorityQueue<Player> pq = new PriorityQueue<>(Comparator.comparingInt(Player::getScore));
        for (Player player:players){
            pq.offer(player);
            if(pq.size() > k){
                pq.poll();
            }
        }
        List<Player> finalPlayers = new ArrayList<>(pq);
        finalPlayers.sort(Comparator.comparingInt(Player::getScore).reversed());
        return finalPlayers;
    }
}
