package org.designpattern.practiceQuestions.BlackjackGame;

import org.designpattern.practiceQuestions.BlackjackGame.model.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Player player = new Player("Priya", false);
        Player dealer = new Player("Dealer", true);
        BlackjackService service = new BlackjackService(player, dealer);

        String playAgain = "Y";
        while (playAgain.equalsIgnoreCase("Y")) {
            service.startRound();

            System.out.println("\n========== New Round ==========");
            System.out.println("Dealer shows: " + dealer.getHand().display());
            System.out.println("Your hand   : " + player.getHand().display());

            // Check immediate blackjack
            if (player.getHand().isBlackjack() || dealer.getHand().isBlackjack()) {
                dealer.getHand().revealAll();
                System.out.println("Dealer hand : " + dealer.getHand().display());
                System.out.println("\n" + service.determineWinner());
            } else {
                // Player turn
                while (!player.getHand().isBusted()) {
                    System.out.print("HIT or STAND? > ");
                    String input = scanner.nextLine().trim().toUpperCase();
                    if (input.equals("STAND") || input.equals("S")) break;
                    if (input.equals("HIT") || input.equals("H")) {
                        service.hit(player);
                        System.out.println("Your hand   : " + player.getHand().display());
                    }
                }

                // Dealer turn (only if player didn't bust)
                if (!player.getHand().isBusted()) {
                    service.dealerTurn();
                    System.out.println("Dealer hand : " + dealer.getHand().display());
                }

                System.out.println("\n" + service.determineWinner());
            }

            System.out.print("\nPlay again? (Y/N) > ");
            playAgain = scanner.nextLine().trim();
        }

        System.out.println("\nThanks for playing!");
        scanner.close();
    }
}
