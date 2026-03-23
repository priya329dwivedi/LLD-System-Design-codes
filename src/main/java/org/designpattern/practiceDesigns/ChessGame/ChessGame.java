/*******************************************************************************
 * Copyright © 2026, Planview, Inc. and its Affiliates.
 * All rights reserverd.
 * This program and the accompanying materials are made available under the
 * Planview Subscription Terms which accompany this distribution, and are
 * available at https://www.planview.com/legal/legal-terms/.
 *******************************************************************************/
package org.designpattern.practiceDesigns.ChessGame;

import org.designpattern.practiceDesigns.ChessGame.PeiceFactory.King;
import org.designpattern.practiceDesigns.ChessGame.PeiceFactory.Piece;
import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Board;
import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Cell;
import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Move;
import org.designpattern.practiceDesigns.ChessGame.UtilityClasses.Player;

import java.util.Scanner;

public class ChessGame {
    Player player1;
    Player player2;
    Board board;
    private boolean isWhiteTurn;
    private Status status;
    public ChessGame(Player player1,Player player2){
        this.player1=player1;
        this.player2=player2;
        this.board = Board.getInstance();
        this.status= Status.ACTIVE;
    }
    public void startGame(){
        Scanner sc = new Scanner(System.in);
        while(this.status.equals(Status.ACTIVE)){
            Player activePlayer= isWhiteTurn? player1:player2;
            int row = sc.nextInt();
            int col = sc.nextInt();
            Cell startCell= board.getCell(row,col);
            if(startCell==null || startCell.getPeice()==null){
                System.out.println("Invalid Cell");
            }
            int row1 = sc.nextInt();
            int col1 = sc.nextInt();
            Cell endCell= board.getCell(row1,col1);
            if(endCell==null ){
                System.out.println("Invalid Cell");
            }
            makeMove(new Move(startCell,endCell));
        }
    }

    private void makeMove(Move move) {
        if(move.isValid()){
            Piece sourcePiece= move.getStartCell().getPeice();
            if(sourcePiece.canMove(board,move.getStartCell(),move.getEndCell())){
                Piece destinationPiece= move.getEndCell().getPeice();
                if(destinationPiece !=null ){
                    if(destinationPiece instanceof King && isWhiteTurn){
                        this.status= Status.WHITE_WIN;
                    }
                    if(destinationPiece instanceof King && !isWhiteTurn){
                        this.status= Status.BLACK_WIN;
                    }
                }
                destinationPiece.setKilled(true);
            }
            move.getEndCell().setPeice(sourcePiece);
            move.getStartCell().setPeice(null);
            this.isWhiteTurn = !isWhiteTurn;
        }

    }
}
