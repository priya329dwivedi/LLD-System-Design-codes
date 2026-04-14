package org.designpattern.practiceQuestions.BlackjackGame.model;

public class Player {
    private String name;
    private Hand hand;
    private boolean isDealer;

    public Player(String name, boolean isDealer) {
        this.name = name;
        this.hand = new Hand();
        this.isDealer = isDealer;
    }

    public String getName() { return name; }
    public Hand getHand() { return hand; }
    public boolean isDealer() { return isDealer; }
}
