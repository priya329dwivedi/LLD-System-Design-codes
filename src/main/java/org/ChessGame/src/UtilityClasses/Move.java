package org.ChessGame.src.UtilityClasses;

import lombok.Getter;

@Getter
public class Move {
    private Cell startCell;
    private Cell endCell;
    public Move(Cell startCell, Cell endCell){
        this.startCell= startCell;
        this.endCell= endCell;
    }
    public boolean isValidMove(){
        if(startCell == null || endCell == null) return false;
        if(startCell.getPiece() == null) return false;
        return endCell.getPiece() == null || endCell.getPiece().isWhite() != startCell.getPiece().isWhite();
    }
}
