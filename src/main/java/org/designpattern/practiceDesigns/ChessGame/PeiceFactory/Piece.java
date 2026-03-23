/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame.PeiceFactory;


import lombok.Getter;
import lombok.Setter;
import org.designpattern.practiceDesigns.ChessGame.MovementStrategy.MovementStrategy;
import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Board;
import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Cell;

public abstract class Piece {
    @Getter
    boolean isWhite;
    @Setter
    boolean isKilled =false;
    MovementStrategy movementStrategy;
    public Piece(MovementStrategy movementStrategy,boolean isWhite){
        this.movementStrategy = movementStrategy;
        this.isWhite = isWhite;
    }
    public boolean canMove(Board board, Cell startCell, Cell endCell){
        return movementStrategy.canMove(board,startCell,endCell);
    }
}
