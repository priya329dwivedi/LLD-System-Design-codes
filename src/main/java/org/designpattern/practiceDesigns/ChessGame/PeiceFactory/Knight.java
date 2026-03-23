/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame.PeiceFactory;

import org.designpattern.practiceDesigns.ChessGame.MovementStrategy.KnightMovementStrategy;
import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Board;
import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Cell;

public class Knight extends Piece{
    public Knight(boolean isWhite) {
        super(new KnightMovementStrategy(), isWhite);
    }
    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return super.canMove(board, startCell, endCell);
    }
}
