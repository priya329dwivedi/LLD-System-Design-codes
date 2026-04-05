package org.designpattern.practiceQuestions.BlackjackGame.strategy;

import org.designpattern.practiceQuestions.BlackjackGame.model.Hand;
import org.designpattern.practiceQuestions.BlackjackGame.model.Move;

// Dealer MUST hit if total < 17, MUST stand if >= 17
public class DealerStrategy implements MoveStrategy {
    @Override
    public Move decideMove(Hand hand) {
        return hand.calculateValue() < 17 ? Move.HIT : Move.STAND;
    }
}
