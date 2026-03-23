/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame.UtilityClasses;

import org.designpattern.practiceDesigns.ChessGame.PeiceFactory.PeiceFactory;

public class Board {
    private Cell[][] board;
    private static Board instance;
    public static Board getInstance(){
        if(instance==null) return new Board();
        else return instance;
    }
    public Board(){
        initialiseBoard();
    }

    private void initialiseBoard() {
        board = new Cell[8][8];
        setPeiceRow(0,false);
        setPawnRow(1,false);
        setPawnRow(6,true);
        setPeiceRow(7,true);

    }

    private void setPawnRow(int row, boolean isWhite) {
        for(int i=0;i<8;i++){
            board[row][i]= new Cell(row,i,PeiceFactory.createPeiceFactory("pawn",isWhite));
        }

    }

    private void setPeiceRow(int row,boolean isWhite) {
        board[row][0]= new Cell(row,0,(PeiceFactory.createPeiceFactory("rook",isWhite)));
        board[row][1]= new Cell(row,1,(PeiceFactory.createPeiceFactory("knight",isWhite)));
        board[row][2]= new Cell(row,2,(PeiceFactory.createPeiceFactory("bishop",isWhite)));
        board[row][3]= new Cell(row,3,(PeiceFactory.createPeiceFactory("queen",isWhite)));
        board[row][4]= new Cell(row,4,(PeiceFactory.createPeiceFactory("king",isWhite)));
        board[row][5]= new Cell(row,5,(PeiceFactory.createPeiceFactory("bishop",isWhite)));
        board[row][6]= new Cell(row,6,(PeiceFactory.createPeiceFactory("knight",isWhite)));
        board[row][7]= new Cell(row,4,(PeiceFactory.createPeiceFactory("rook",isWhite)));
    }

    public Cell getCell(int row, int col){
        if(row<0 || col<0 || row>8 || col>8) return null;
        return board[row][col];
    }

}
