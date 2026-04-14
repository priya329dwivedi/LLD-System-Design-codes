package org.designpattern.practiceQuestions.BlackjackGame;

import org.designpattern.practiceQuestions.BlackjackGame.model.Card;
import org.designpattern.practiceQuestions.BlackjackGame.model.Deck;
import org.designpattern.practiceQuestions.BlackjackGame.model.Player;

public class BlackjackService {
    private Deck deck;
    private Player player;
    private Player dealer;

    public BlackjackService(Player player, Player dealer) {
        this.player = player;
        this.dealer = dealer;
    }

    public void startRound() {
        deck = new Deck();
        deck.shuffle();
        player.getHand().clear();
        dealer.getHand().clear();

        player.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());
        player.getHand().addCard(deck.dealCard());

        Card hiddenCard = deck.dealCard();
        hiddenCard.setFaceUp(false);
        dealer.getHand().addCard(hiddenCard);
    }

    public void hit(Player p) {
        p.getHand().addCard(deck.dealCard());
    }

    public void dealerTurn() {
        dealer.getHand().revealAll();
        while (dealer.getHand().calculateValue() < 17) {
            dealer.getHand().addCard(deck.dealCard());
        }
    }

    public String determineWinner() {
        int playerVal = player.getHand().calculateValue();
        int dealerVal = dealer.getHand().calculateValue();

        if (player.getHand().isBlackjack()) return player.getName() + " wins with Blackjack!";
        if (dealer.getHand().isBlackjack()) return dealer.getName() + " wins with Blackjack!";
        if (player.getHand().isBusted())    return dealer.getName() + " wins — " + player.getName() + " busted!";
        if (dealer.getHand().isBusted())    return player.getName() + " wins — Dealer busted!";
        if (playerVal > dealerVal)          return player.getName() + " wins! (" + playerVal + " vs " + dealerVal + ")";
        if (dealerVal > playerVal)          return dealer.getName() + " wins! (" + dealerVal + " vs " + playerVal + ")";
        return "It's a tie! Both have " + playerVal;
    }
}
