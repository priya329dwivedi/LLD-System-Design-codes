package concepts.practiceQuestions.SnakeGame.strategy;

import concepts.practiceQuestions.SnakeGame.model.Position;

import java.util.List;
import java.util.Set;

// Spawns food at a pre-defined sequence of positions — useful for demos and tests.
public class FixedFoodSpawner implements FoodSpawner {
    private final List<Position> positions;
    private int index = 0;

    public FixedFoodSpawner(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public Position spawn(int rows, int cols, Set<Position> occupied) {
        return positions.get(index++ % positions.size());
    }
}
