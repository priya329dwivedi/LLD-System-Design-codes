/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame.PeiceFactory;

public class PeiceFactory {
    public static Piece createPeiceFactory(String piece,boolean isWhite){
        if(piece.equals("king")){
            return new King(isWhite);
        }
        else if(piece.equals("queen")){
            return new Queen(isWhite);
        }
        else if(piece.equals("pawn")){
            return  new Pawn(isWhite);
        }
        else if(piece.equals(("bishop"))){
            return new Bishop(isWhite);
        }
        else if(piece.equals(("knight"))){
            return new Knight(isWhite);
        }
        else if(piece.equals("rook")){
            return new Rook(isWhite);
        }
        else{
            throw new IllegalArgumentException("wrong type of piece entered");
        }
    }
}
