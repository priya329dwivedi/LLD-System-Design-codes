package org.designpattern.practiceQuestions.BlackjackGame.game;

import org.designpattern.practiceQuestions.BlackjackGame.model.Card;
import org.designpattern.practiceQuestions.BlackjackGame.model.Deck;
import org.designpattern.practiceQuestions.BlackjackGame.model.Move;
import org.designpattern.practiceQuestions.BlackjackGame.model.Player;
import org.designpattern.practiceQuestions.BlackjackGame.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private Deck deck;
    private final Player player;
    private final Player dealer;
    private final List<GameObserver> observers;

    public BlackjackGame(Player player, Player dealer) {
        this.player = player;
        this.dealer = dealer;
        this.observers = new ArrayList<>();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void playRound() {
        // 1. Fresh deck + shuffle
        deck = new Deck();
        deck.shuffle();
        player.getHand().clear();
        dealer.getHand().clear();

        System.out.println("\n========== New Round ==========\n");

        // 2. Deal: player 2, dealer 2 (second card face-down)
        System.out.println("--- Dealing ---");
        dealCard(player, true);
        dealCard(dealer, true);
        dealCard(player, true);
        dealCard(dealer, false);  // dealer's hidden card

        System.out.println("\nDealer shows: " + dealer.getHand().display());
        System.out.println("Player shows: " + player.getHand().display());

        // 3. Check for blackjack
        if (player.getHand().isBlackjack()) {
            notifyBlackjack(player);
            dealer.getHand().revealAll();
            if (dealer.getHand().isBlackjack()) {
                notifyBlackjack(dealer);
                notifyGameOver("It's a TIE! Both have Blackjack!");
            } else {
                notifyGameOver(player.getName() + " WINS with Blackjack!");
            }
            return;
        }
        if (dealer.getHand().isBlackjack()) {
            dealer.getHand().revealAll();
            notifyBlackjack(dealer);
            notifyGameOver(dealer.getName() + " WINS with Blackjack!");
            return;
        }

        // 4. Player's turn
        System.out.println("\n--- " + player.getName() + "'s Turn ---");
        playerTurn(player);
        if (player.getHand().isBusted()) {
            notifyBust(player);
            notifyGameOver(dealer.getName() + " WINS! " + player.getName() + " busted.");
            return;
        }

        // 5. Dealer's turn — reveal hidden card first
        System.out.println("\n--- " + dealer.getName() + "'s Turn ---");
        dealer.getHand().revealAll();
        System.out.println("Dealer reveals: " + dealer.getHand().display());
        playerTurn(dealer);
        if (dealer.getHand().isBusted()) {
            notifyBust(dealer);
            notifyGameOver(player.getName() + " WINS! " + dealer.getName() + " busted.");
            return;
        }

        // 6. Compare hands
        determineWinner();
    }

    private void playerTurn(Player p) {
        while (true) {
            Move move = p.decideMove();
            if (move == Move.STAND) {
                System.out.println("  " + p.getName() + " STANDS at " + p.getHand().calculateValue());
                break;
            }
            // HIT
            dealCard(p, true);
            System.out.println("  " + p.getName() + "'s hand: " + p.getHand().display());
            if (p.getHand().isBusted()) {
                break;
            }
        }
    }

    private void dealCard(Player p, boolean faceUp) {
        Card card = deck.dealCard();
        card.setFaceUp(faceUp);
        p.getHand().addCard(card);
        notifyCardDealt(p, card);
    }

    private void determineWinner() {
        int playerTotal = player.getHand().calculateValue();
        int dealerTotal = dealer.getHand().calculateValue();

        System.out.println("\n--- Final Hands ---");
        System.out.println("  " + player.getName() + ": " + player.getHand().display());
        System.out.println("  " + dealer.getName() + ": " + dealer.getHand().display());

        if (playerTotal > dealerTotal) {
            notifyGameOver(player.getName() + " WINS! (" + playerTotal + " vs " + dealerTotal + ")");
        } else if (dealerTotal > playerTotal) {
            notifyGameOver(dealer.getName() + " WINS! (" + dealerTotal + " vs " + playerTotal + ")");
        } else {
            notifyGameOver("It's a TIE! Both have " + playerTotal);
        }
    }

    // ========== Observer notifications ==========
    private void notifyCardDealt(Player p, Card card) {
        for (GameObserver o : observers) o.onCardDealt(p, card);
    }

    private void notifyBust(Player p) {
        for (GameObserver o : observers) o.onPlayerBust(p);
    }

    private void notifyBlackjack(Player p) {
        for (GameObserver o : observers) o.onBlackjack(p);
    }

    private void notifyGameOver(String result) {
        for (GameObserver o : observers) o.onGameOver(result);
    }
}
