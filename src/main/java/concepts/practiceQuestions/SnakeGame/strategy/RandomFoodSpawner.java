package concepts.practiceQuestions.SnakeGame.strategy;

import concepts.practiceQuestions.SnakeGame.model.Position;

import java.util.Random;
import java.util.Set;

public class RandomFoodSpawner implements FoodSpawner {
    private final Random random = new Random();

    @Override
    public Position spawn(int rows, int cols, Set<Position> occupied) {
        Position pos;
        do {
            pos = new Position(random.nextInt(rows), random.nextInt(cols));
        } while (occupied.contains(pos));
        return pos;
    }
}
