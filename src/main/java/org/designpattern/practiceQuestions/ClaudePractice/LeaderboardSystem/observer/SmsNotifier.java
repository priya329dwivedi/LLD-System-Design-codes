package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.observer;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model.Player;

public class SmsNotifier implements LeaderboardObserver {
    private final String phoneNumber;

    public SmsNotifier(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void onTopRankAchieved(Player player, int rank) {
        System.out.println("[SMS -> " + phoneNumber + "] " + player.getName() + " entered Top Chart at rank " + rank);
    }

    @Override
    public void onPersonalBest(Player player, int newScore) {
        System.out.println("[SMS -> " + phoneNumber + "] " + player.getName() + " set a new personal best: " + newScore);
    }
}
