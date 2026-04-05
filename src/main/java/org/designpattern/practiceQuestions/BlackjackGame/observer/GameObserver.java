package org.designpattern.practiceQuestions.BlackjackGame.observer;

import org.designpattern.practiceQuestions.BlackjackGame.model.Card;
import org.designpattern.practiceQuestions.BlackjackGame.model.Player;

public interface GameObserver {
    void onCardDealt(Player player, Card card);
    void onPlayerBust(Player player);
    void onBlackjack(Player player);
    void onGameOver(String result);
}
