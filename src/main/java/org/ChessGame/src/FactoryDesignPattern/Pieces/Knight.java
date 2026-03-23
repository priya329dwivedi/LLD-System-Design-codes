package org.ChessGame.src.FactoryDesignPattern.Pieces;

import org.ChessGame.src.MovementStrategyPattern.MovementStrategies.KingMovementStrategy;
import org.ChessGame.src.FactoryDesignPattern.Piece;
import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite, new KingMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return super.canMove(board, startCell, endCell);
    }
}
