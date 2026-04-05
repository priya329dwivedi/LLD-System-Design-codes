package org.designpattern.practiceQuestions.BlackjackGame.observer;

import org.designpattern.practiceQuestions.BlackjackGame.model.Card;
import org.designpattern.practiceQuestions.BlackjackGame.model.Player;

public class ConsoleLogger implements GameObserver {
    @Override
    public void onCardDealt(Player player, Card card) {
        if (card.isFaceUp()) {
            System.out.println("  " + player.getName() + " gets " + card);
        } else {
            System.out.println("  " + player.getName() + " gets [??] (face-down)");
        }
    }

    @Override
    public void onPlayerBust(Player player) {
        System.out.println("\n  " + player.getName() + " BUSTS with " + player.getHand().calculateValue() + "!");
    }

    @Override
    public void onBlackjack(Player player) {
        System.out.println("\n  BLACKJACK! " + player.getName() + " has 21!");
    }

    @Override
    public void onGameOver(String result) {
        System.out.println("\n========== " + result + " ==========");
    }
}
