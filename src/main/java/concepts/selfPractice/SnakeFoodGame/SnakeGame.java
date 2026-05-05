package concepts.selfPractice.SnakeFoodGame;

import concepts.selfPractice.SnakeFoodGame.Strategy.FoodSpawnStrategy;
import concepts.selfPractice.SnakeFoodGame.model.*;

public class SnakeGame {

    private final Board board;
    private final FoodSpawnStrategy strategy;
    private final Snake snake;
    private  Position food;
    private  GameStatus status;
    private int score;

    public SnakeGame(int row,int col,FoodSpawnStrategy strategy,Position food) {
        this.board = new Board(row, col);
        this.strategy= strategy;
        this.snake= new Snake(new Position(row/2,col/2));
        this.food= food;
        this.status= GameStatus.RUNNING;
        this.score=0;
        spawnFood();
    }

    private void spawnFood() {
        Position food= strategy.foodSpawn(board.getRow(),board.getCol());
        System.out.println("Food spawned at " + food);
    }

    public void move(Direction direction){
        Position newHead= snake.getHead().move(direction);
        if(!board.isWithinBoundary(newHead.getRow(), newHead.getCol())){
            System.out.println("----Snake hit the wall---- ");
            this.status= GameStatus.OVER;
        }
        if(snake.selfHit(newHead)){
            System.out.println("--- Snake self HIT");
            this.status= GameStatus.OVER;
        }
        if(){

        }
    }

}
