package org.ChessGame.src.FactoryDesignPattern.Pieces;

import org.ChessGame.src.MovementStrategyPattern.MovementStrategies.PawnMovementStrategy;
import org.ChessGame.src.FactoryDesignPattern.Piece;
import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite, new PawnMovementStrategy());
    }

    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return super.canMove(board, startCell, endCell);
    }
}
