package org.designpattern.practiceQuestions.BlackjackGame.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int calculateValue() {
        int total = 0;
        int aceCount = 0;

        for (Card card : cards) {
            total += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                aceCount++;
            }
        }

        // Demote Aces from 11 to 1 while busting
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    public boolean isBusted() {
        return calculateValue() > 21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && calculateValue() == 21;
    }

    public List<Card> getCards() { return cards; }

    // Display: show all face-up cards, hide face-down
    public String display() {
        StringBuilder sb = new StringBuilder();
        int visibleTotal = 0;
        boolean hasHidden = false;

        for (Card card : cards) {
            sb.append(card).append(" ");
            if (card.isFaceUp()) {
                visibleTotal += card.getRank().getValue();
            } else {
                hasHidden = true;
            }
        }

        if (hasHidden) {
            sb.append(" (visible: ").append(visibleTotal).append(")");
        } else {
            sb.append(" (total: ").append(calculateValue()).append(")");
        }
        return sb.toString();
    }

    public void revealAll() {
        for (Card card : cards) {
            card.setFaceUp(true);
        }
    }

    public void clear() {
        cards.clear();
    }
}
