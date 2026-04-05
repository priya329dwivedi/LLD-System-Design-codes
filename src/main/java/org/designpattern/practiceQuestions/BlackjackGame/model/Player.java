package org.designpattern.practiceQuestions.BlackjackGame.model;

import org.designpattern.practiceQuestions.BlackjackGame.strategy.MoveStrategy;

public class Player {
    private final String name;
    private final Hand hand;
    private final MoveStrategy strategy;
    private final boolean isDealer;

    public Player(String name, MoveStrategy strategy, boolean isDealer) {
        this.name = name;
        this.hand = new Hand();
        this.strategy = strategy;
        this.isDealer = isDealer;
    }

    public Move decideMove() {
        return strategy.decideMove(hand);
    }

    public String getName() { return name; }
    public Hand getHand() { return hand; }
    public boolean isDealer() { return isDealer; }

    @Override
    public String toString() { return name; }
}
