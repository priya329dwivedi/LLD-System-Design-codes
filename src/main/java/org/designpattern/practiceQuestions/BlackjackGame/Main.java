package org.designpattern.practiceQuestions.BlackjackGame;

import org.designpattern.practiceQuestions.BlackjackGame.game.BlackjackGame;
import org.designpattern.practiceQuestions.BlackjackGame.model.Player;
import org.designpattern.practiceQuestions.BlackjackGame.observer.ConsoleLogger;
import org.designpattern.practiceQuestions.BlackjackGame.strategy.DealerStrategy;
import org.designpattern.practiceQuestions.BlackjackGame.strategy.HumanStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ========== Create Players ==========
        // Player uses HumanStrategy (console input: HIT/STAND)
        // Dealer uses DealerStrategy (auto-hit if < 17)
        Player player = new Player("Priya", new HumanStrategy(scanner), false);
        Player dealer = new Player("Dealer", new DealerStrategy(), true);

        // ========== Create Game + Observer ==========
        BlackjackGame game = new BlackjackGame(player, dealer);
        game.addObserver(new ConsoleLogger());

        // ========== Play rounds ==========
        String playAgain = "Y";
        while (playAgain.equalsIgnoreCase("Y")) {
            game.playRound();
            System.out.print("\nPlay again? (Y/N) > ");
            playAgain = scanner.nextLine().trim();
        }

        System.out.println("\nThanks for playing!");
        scanner.close();
    }
}
