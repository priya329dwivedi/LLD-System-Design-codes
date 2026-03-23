package org.ChessGame.src.FactoryDesignPattern;

import org.ChessGame.src.FactoryDesignPattern.Pieces.*;

public class PieceFactory {
    public static Piece createPiece(String pieceType, boolean isWhite){
        switch(pieceType.toLowerCase()){
            case "king":
                return new King(isWhite);
            case "queen":
                return new Queen(isWhite);
            case "bishop":
                return new Bishop(isWhite);
            case "rook":
                return new Rook(isWhite);
            case "pawn":
                return new Pawn(isWhite);
            case "knight":
                return new Knight(isWhite);
            default:
                throw new IllegalArgumentException("Unknown Piece Type "+ pieceType);
        }
    }
}
