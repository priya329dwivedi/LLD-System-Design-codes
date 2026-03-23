package org.ChessGame.src.FactoryDesignPattern;

import lombok.Getter;
import lombok.Setter;
import org.ChessGame.src.MovementStrategyPattern.MovementStrategy;
import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;


@Getter
public abstract class Piece {
    private final boolean isWhite;
    @Setter
    private boolean isKilled=false;
    private final MovementStrategy movementStrategy;
    public Piece(boolean isWhite, MovementStrategy movementStrategy) {
        this.isWhite = isWhite;
        this.movementStrategy = movementStrategy;
    }

    public boolean canMove(Board board,Cell startCell, Cell endCell){
        return movementStrategy.canMove(board,startCell, endCell);
    }
}
