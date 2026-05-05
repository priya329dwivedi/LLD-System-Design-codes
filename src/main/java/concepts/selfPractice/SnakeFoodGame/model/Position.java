package concepts.selfPractice.SnakeFoodGame.model;

import lombok.Getter;

@Getter
public class Position {
    int row;
    int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position move(Direction direction){
        if(direction.equals(Direction.UP)) return new Position(row-1,col);
        else if(direction.equals(Direction.DOWN)) return new Position(row+1,col);
        else if(direction.equals(Direction.RIGHT)) return new Position(row,col+1);
        else return new Position(row,col-1);
    }

    public void equals(Position pos){
        if(pos.getRow())
    }
}
