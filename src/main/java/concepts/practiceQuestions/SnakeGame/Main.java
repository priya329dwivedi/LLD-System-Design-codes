package concepts.practiceQuestions.SnakeGame;

import concepts.practiceQuestions.SnakeGame.model.Direction;
import concepts.practiceQuestions.SnakeGame.model.Position;
import concepts.practiceQuestions.SnakeGame.strategy.FixedFoodSpawner;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ── Flow 1: Snake eats food and grows ────────────────────────────────
        // Board 10x10, snake starts at (5,5)
        // Food placed at (5,6) then (5,7) then (5,8) — all to the right
        separator("FLOW 1: Eat food, grow, score increases");

        SnakeGame game1 = new SnakeGame(10, 10,
            new FixedFoodSpawner(List.of(
                new Position(5, 6),
                new Position(5, 7),
                new Position(5, 8)
            )));

        game1.move(Direction.RIGHT); // eats (5,6) → size 2, score 1
        game1.move(Direction.RIGHT); // eats (5,7) → size 3, score 2
        game1.move(Direction.RIGHT); // eats (5,8) → size 4, score 3
        System.out.println("Final score: " + game1.getScore());

        // ── Flow 2: Snake hits the wall ───────────────────────────────────────
        separator("FLOW 2: Hit wall → GAME OVER");

        SnakeGame game2 = new SnakeGame(5, 5,
            new FixedFoodSpawner(List.of(new Position(0, 0))));

        // Snake starts at (2,2), keep moving right until wall
        game2.move(Direction.RIGHT); // (2,3)
        game2.move(Direction.RIGHT); // (2,4) — edge
        game2.move(Direction.RIGHT); // (2,5) — out of bounds → GAME OVER
        System.out.println("Status: " + game2.getStatus());

        // ── Flow 3: Snake hits itself ─────────────────────────────────────────
        separator("FLOW 3: Self collision → GAME OVER");

        // Snake starts at (5,5), grow it to size 4 then loop back into body
        SnakeGame game3 = new SnakeGame(10, 10,
            new FixedFoodSpawner(List.of(
                new Position(5, 6),
                new Position(5, 7),
                new Position(5, 8),
                new Position(0, 0)  // dummy — won't be reached
            )));

        game3.move(Direction.RIGHT); // eats (5,6) → size 2
        game3.move(Direction.RIGHT); // eats (5,7) → size 3
        game3.move(Direction.RIGHT); // eats (5,8) → size 4, body: [(5,8),(5,7),(5,6),(5,5)]
        game3.move(Direction.UP);    // (4,8)
        game3.move(Direction.LEFT);  // (4,7)
        game3.move(Direction.LEFT);  // (4,6)
        game3.move(Direction.LEFT);  // (4,5)
        game3.move(Direction.DOWN);  // (5,5) — back into original tail → GAME OVER
        System.out.println("Status: " + game3.getStatus());
    }

    private static void separator(String label) {
        System.out.println("\n── " + label + " " + "─".repeat(Math.max(0, 50 - label.length())));
    }
}
