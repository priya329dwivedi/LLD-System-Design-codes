package concepts.selfPractice.SnakeFoodGame.Strategy;

import concepts.selfPractice.SnakeFoodGame.model.Position;

import java.util.List;

public interface FoodSpawnStrategy {
    Position foodSpawn(int row,int col);
}
