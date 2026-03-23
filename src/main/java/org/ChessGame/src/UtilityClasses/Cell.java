package org.ChessGame.src.UtilityClasses;

import lombok.Getter;
import lombok.Setter;
import org.ChessGame.src.FactoryDesignPattern.Piece;

@Getter
@Setter
public class Cell {
    private int row;
    private int column;
    private Piece piece;

    public Cell(int row, int column, Piece piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }
}
