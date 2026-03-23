package org.ChessGame.src.FactoryDesignPattern.Pieces;

import org.ChessGame.src.MovementStrategyPattern.MovementStrategies.QueenMovementStrategy;
import org.ChessGame.src.FactoryDesignPattern.Piece;
import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite, new QueenMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return super.canMove(board, startCell, endCell);
    }
}
