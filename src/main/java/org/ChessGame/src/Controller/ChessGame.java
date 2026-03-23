package org.ChessGame.src.Controller;

import org.ChessGame.src.CommonEnum.Status;
import org.ChessGame.src.FactoryDesignPattern.Piece;
import org.ChessGame.src.FactoryDesignPattern.Pieces.King;
import org.ChessGame.src.UtilityClasses.Board;
import org.ChessGame.src.UtilityClasses.Cell;
import org.ChessGame.src.UtilityClasses.Move;
import org.ChessGame.src.UtilityClasses.Player;

import java.util.Scanner;

public class ChessGame {
    private Board board;
    private Player player1;
    private Player player2;
    private Status status;
    private boolean isWhiteTurn=false;

    public ChessGame(Player player1,Player player2){
        this.player1=player1;
        this.player2=player2;
        this.board= Board.getInstance(8);
        this.status= Status.ACTIVE;
        this.isWhiteTurn=true;
    }

    public void startGame(){
        Scanner scanner = new Scanner(System.in);
        while(this.status.equals(Status.ACTIVE)){
           Player activePlayer= isWhiteTurn? player1:player2;
            System.out.print("Enter source row and column (e.g., 6 4): ");
            int startRow = scanner.nextInt();
            int startCol = scanner.nextInt();
            Cell startCell= board.getCell(startRow,startCol);

            System.out.print("Enter destination row and column (e.g., 6 4): ");
            int endRow = scanner.nextInt();
            int endCol = scanner.nextInt();

            Cell endCell= board.getCell(endRow,endCol);
            if(startCell==null || startCell.getPiece()==null){
                System.out.println("Invalid Move");
            }
            makeMove(new Move(startCell,endCell),activePlayer);
        }
    }

    private void makeMove(Move move,Player activePlayer) {
        if(move.isValidMove()){
            Piece sourcePiece= move.getStartCell().getPiece();
            if(sourcePiece.canMove(board,move.getStartCell(),move.getEndCell())){
                Piece destinationPiece= move.getEndCell().getPiece();
                if(destinationPiece!=null){
                    if(destinationPiece instanceof King && isWhiteTurn){
                        this.status= Status.WHITE_WIN;
                    }
                    if(destinationPiece instanceof King && !isWhiteTurn){
                        this.status= Status.BLACK_WIN;
                    }
                    destinationPiece.setKilled(true);
                }
                move.getEndCell().setPiece(sourcePiece);
                move.getStartCell().setPiece(null);
                this.isWhiteTurn=!isWhiteTurn;
            }
        }
    }
}
