package org.ChessGame.src.FactoryDesignPattern.Pieces;

import org.ChessGame.src.MovementStrategyPattern.MovementStrategies.BishopMovementStrategy;
import org.ChessGame.src.FactoryDesignPattern.Piece;
import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite, new BishopMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return super.canMove(board, startCell, endCell);
    }
}
