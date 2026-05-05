package concepts.practiceQuestions.SnakeGame;

import concepts.practiceQuestions.SnakeGame.model.Position;

public class Board {
    private final int rows;
    private final int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public boolean isWithinBounds(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < rows
            && pos.getCol() >= 0 && pos.getCol() < cols;
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
}
