package org.designpattern.practiceQuestions.BlackjackGame.strategy;

import org.designpattern.practiceQuestions.BlackjackGame.model.Hand;
import org.designpattern.practiceQuestions.BlackjackGame.model.Move;

import java.util.Scanner;

// Human player chooses HIT or STAND via console input
public class HumanStrategy implements MoveStrategy {
    private final Scanner scanner;

    public HumanStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Move decideMove(Hand hand) {
        while (true) {
            System.out.print("Your hand: " + hand.display() + "\nHIT or STAND? > ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("HIT") || input.equals("H")) return Move.HIT;
            if (input.equals("STAND") || input.equals("S")) return Move.STAND;
            System.out.println("Invalid input. Type HIT (H) or STAND (S).");
        }
    }
}
