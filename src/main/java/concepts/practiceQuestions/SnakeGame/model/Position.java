package concepts.practiceQuestions.SnakeGame.model;

import java.util.Objects;

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position move(Direction dir) {
        return switch (dir) {
            case UP    -> new Position(row - 1, col);
            case DOWN  -> new Position(row + 1, col);
            case LEFT  -> new Position(row, col - 1);
            case RIGHT -> new Position(row, col + 1);
        };
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    // equals + hashCode needed for HashSet collision detection
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position p)) return false;
        return row == p.row && col == p.col;
    }

    @Override public int hashCode() { return Objects.hash(row, col); }
    @Override public String toString() { return "(" + row + "," + col + ")"; }
}
