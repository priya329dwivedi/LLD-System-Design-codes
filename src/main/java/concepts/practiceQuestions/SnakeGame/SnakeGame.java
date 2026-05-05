package concepts.practiceQuestions.SnakeGame;

import concepts.practiceQuestions.SnakeGame.model.Direction;
import concepts.practiceQuestions.SnakeGame.model.GameStatus;
import concepts.practiceQuestions.SnakeGame.model.Position;
import concepts.practiceQuestions.SnakeGame.strategy.FoodSpawner;

public class SnakeGame {
    private final Board      board;
    private final Snake      snake;
    private final FoodSpawner foodSpawner;
    private       Position   food;
    private       GameStatus status;
    private       int        score;

    public SnakeGame(int rows, int cols, FoodSpawner foodSpawner) {
        this.board       = new Board(rows, cols);
        this.snake       = new Snake(new Position(rows / 2, cols / 2));
        this.foodSpawner = foodSpawner;
        this.status      = GameStatus.RUNNING;
        this.score       = 0;
        spawnFood();
    }

    public GameStatus move(Direction dir) {
        if (status != GameStatus.RUNNING) return status;

        Position newHead = snake.getHead().move(dir);

        if (!board.isWithinBounds(newHead)) {
            status = GameStatus.GAME_OVER;
            System.out.println("GAME OVER — hit wall at " + newHead);
            return status;
        }

        if (snake.occupies(newHead)) {
            status = GameStatus.GAME_OVER;
            System.out.println("GAME OVER — hit itself at " + newHead);
            return status;
        }

        if (newHead.equals(food)) {
            snake.grow(newHead);
            score++;
            System.out.println("Food eaten at " + food + " | score=" + score + " | size=" + snake.size());
            spawnFood();
        } else {
            snake.move(newHead);
            System.out.println("Moved to " + newHead + " | " + snake);
        }

        return status;
    }

    private void spawnFood() {
        food = foodSpawner.spawn(board.getRows(), board.getCols(), snake.getBodySet());
        System.out.println("Food spawned at " + food);
    }

    public GameStatus getStatus() { return status; }
    public int        getScore()  { return score; }
    public Position   getFood()   { return food; }
    public Snake      getSnake()  { return snake; }
}
