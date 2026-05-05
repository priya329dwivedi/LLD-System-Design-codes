package concepts.practiceQuestions.SnakeGame.strategy;

import concepts.practiceQuestions.SnakeGame.model.Position;

import java.util.Set;

public interface FoodSpawner {
    Position spawn(int rows, int cols, Set<Position> occupied);
}
