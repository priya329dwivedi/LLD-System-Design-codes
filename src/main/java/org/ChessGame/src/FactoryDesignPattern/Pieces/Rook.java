package org.ChessGame.src.FactoryDesignPattern.Pieces;

import org.ChessGame.src.MovementStrategyPattern.MovementStrategies.RookMovementStrategy;
import org.ChessGame.src.FactoryDesignPattern.Piece;
import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite, new RookMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return super.canMove(board, startCell, endCell);
    }
}
