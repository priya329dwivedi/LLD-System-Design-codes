package org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.observer;

import org.designpattern.practiceQuestions.ClaudePractice.LeaderboardSystem.model.Player;

public interface LeaderboardObserver {
    void onTopRankAchieved(Player player, int rank);
    void onPersonalBest(Player player, int newScore);
}
