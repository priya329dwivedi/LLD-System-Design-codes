package org.ChessGame.src.MovementStrategyPattern;

import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;

public interface MovementStrategy {
     boolean canMove(Board board, Cell startCell, Cell endCell);
}
