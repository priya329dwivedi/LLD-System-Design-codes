package org.designpattern.practiceQuestions.BlackjackGame.strategy;

import org.designpattern.practiceQuestions.BlackjackGame.model.Hand;
import org.designpattern.practiceQuestions.BlackjackGame.model.Move;

public interface MoveStrategy {
    Move decideMove(Hand hand);
}
