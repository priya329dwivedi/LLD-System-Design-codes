package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.observer;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model.Player;

public class InAppNotifier implements LeaderboardObserver {
    private final String deviceId;

    public InAppNotifier(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void onTopRankAchieved(Player player, int rank) {
        System.out.println("[InApp -> " + deviceId + "] " + player.getName() + " entered Top Chart at rank " + rank);
    }

    @Override
    public void onPersonalBest(Player player, int newScore) {
        System.out.println("[InApp -> " + deviceId + "] " + player.getName() + " set a new personal best: " + newScore);
    }
}
