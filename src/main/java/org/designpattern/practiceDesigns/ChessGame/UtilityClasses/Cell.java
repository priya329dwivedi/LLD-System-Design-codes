/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame.UtilityClasses;

import lombok.Getter;
import lombok.Setter;
import org.designpattern.practiceDesigns.ChessGame.PeiceFactory.Piece;

public class Cell {
    int row;
    int col;
    @Getter
    @Setter
    Piece peice;
    public Cell(int row, int col, Piece peice){
        this.row=row;
        this.col=col;
        this.peice=peice;
    }
}
