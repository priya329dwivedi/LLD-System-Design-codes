package org.ChessGame.src.UtilityClasses;

import org.ChessGame.src.FactoryDesignPattern.PieceFactory;

public class Board {
    private Cell[][] board;
    private static Board instance;

    public static Board getInstance(int rows){
        if(instance==null){
            instance= new Board(rows);
        }
        return instance;
    }

    public Board(int rows){
        initializeBoard(rows);
    }

    private void initializeBoard(int rows) {
        board = new Cell[rows][rows];
        setPieceRow(0,true);
        setPawnRow(1,true);
        setPawnRow(rows-1,false);
        setPawnRow(rows-2,false);
        for(int i=2;i<rows-2;i++){
            for(int j=0;j<rows;j++){
                board[i][j]=new Cell(i,j,null);
            }
        }
    }

    private void setPieceRow(int row, boolean isWhite) {
        board[row][0]= new Cell(row,0,PieceFactory.createPiece("rook", isWhite));
        board[row][1]= new Cell(row,1,PieceFactory.createPiece("knight",isWhite));
        board[row][2]= new Cell(row,2, PieceFactory.createPiece("bishop",isWhite));
        board[row][3]= new Cell(row,3,PieceFactory.createPiece("queen",isWhite));
        board[row][4]= new Cell(row,4,PieceFactory.createPiece("king",isWhite));
        board[row][5]= new Cell(row,0,PieceFactory.createPiece("rook", isWhite));
        board[row][6]= new Cell(row,1,PieceFactory.createPiece("knight",isWhite));
        board[row][7]= new Cell(row,2, PieceFactory.createPiece("bishop",isWhite));
    }

    private void setPawnRow(int row, boolean isWhite) {
        for(int j=0;j<8;j++){
            board[row][j]= new Cell(row,j,PieceFactory.createPiece("pawn",isWhite));
        }
    }

    public Cell getCell(int row,int col){
        if(row<0 || row<board.length || col<0 || col< board.length){
            return null;
        }
        return board[row][col];
    }
}
