package concepts.selfPractice.SnakeFoodGame.model;

import lombok.Getter;

@Getter
public class Board {
    private final int row;
    private final int col;

    public Board(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isWithinBoundary(int r, int c){
        return this.row<r && this.col<c && r>=0 && c>=0;
    }
}
