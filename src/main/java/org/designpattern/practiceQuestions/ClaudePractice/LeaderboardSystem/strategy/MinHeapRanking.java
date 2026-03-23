package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.strategy;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MinHeapRanking implements RankingStrategy {
    @Override
    public List<Player> getTopK(List<Player> players, int k) {
        PriorityQueue<Player> minHeap = new PriorityQueue<>(Comparator.comparingInt(Player::getScore));
        for (Player player : players) {
            minHeap.offer(player);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        List<Player> topK = new ArrayList<>(minHeap);
        topK.sort(Comparator.comparingInt(Player::getScore).reversed());
        return topK;
    }
}
