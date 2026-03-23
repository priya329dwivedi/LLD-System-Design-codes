/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame.UtilityClasses;

import lombok.Getter;

@Getter
public class Move {
    Cell startCell;
    Cell endCell;

    public Move(Cell startCell, Cell endCell) {
        this.startCell = startCell;
        this.endCell = endCell;
    }
    public boolean isValid(){
        if(startCell==null || endCell==null) return false;
        return endCell.getPeice()==null || startCell.getPeice().isWhite()!=endCell.getPeice().isWhite();
    }
}
