package org.designpattern.practiceQuestions.BlackjackGame.model;

public class Card {
    private final Suit suit;
    private final Rank rank;
    private boolean faceUp;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.faceUp = true;
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }
    public boolean isFaceUp() { return faceUp; }
    public void setFaceUp(boolean faceUp) { this.faceUp = faceUp; }

    @Override
    public String toString() {
        if (!faceUp) return "[??]";
        return "[" + rank + suit.getSymbol() + "]";
    }
}
