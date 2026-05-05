package concepts.selfPractice.SnakeFoodGame.Strategy;

import concepts.selfPractice.SnakeFoodGame.model.Position;

import java.util.List;

public class FixFoodSpawnStrategy implements FoodSpawnStrategy{
    List<Position> positions;
    int index=0;

    @Override
    public Position foodSpawn(int row, int col) {
        return positions.get(index++ % positions.size());
    }
}
