package org.ChessGame.src.MovementStrategyPattern.MovementStrategies;

import org.ChessGame.src.MovementStrategyPattern.MovementStrategy;
import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;

public class PawnMovementStrategy implements MovementStrategy {
    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return false;
    }
}
