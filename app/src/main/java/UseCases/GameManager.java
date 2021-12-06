package UseCases;

import java.util.ArrayList;

import Entities.*;
import Interfaces.Database;
import Interfaces.Move;
import Interfaces.User;

public class GameManager {

    private Board board;
    private ArrayList<Piece> whitePiecesOut, blackPiecesOut;

    private int moveNumber = 1;


//    private User playerWhite, playerBlack;
    private boolean playerWhiteInTurn;
    private final String[] LETTER_COORDINATES;

    /**
     *
     * @param inputBoard The starting board containing all pieces needed to start the game.
     */
    public GameManager(Board inputBoard) {
        this.board = inputBoard;
        this.LETTER_COORDINATES = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};


    }

    public void startGame() {
//        Need to input players, place pieces, set white first

        this.playerWhiteInTurn = true;
    }

//    public void setUpWhite(User white) {
//        playerWhite = white;
//    }
//
//    public void setUpBlack(User black) {
//        playerBlack = black;
//    }

    public void endGame() {

    }

    public Board getBoard() {
        return board;
    }

//    public void makeMove(String currSpot, String newSpot) {
//        Piece movingPiece = board.checkSquare(currSpot);
//        Object potentialPiece = board.checkSquare(newSpot);
//
//        MoveChecker checker = new MoveChecker();
//        Boolean condition1 = true;
//        // Boolean condition1 = checker.checkValidMove(board, currSpot, newSpot);
//        Boolean condition2 = true;
//        // condition2 = !Player.isChecked
//        if(condition1 && condition2){
//            board.movePiece(currSpot, newSpot);
//            // promote pawn
//            if (board.checkSquare(newSpot) instanceof Pawn && (board.getRowNum(newSpot) == 1
//                    || board.getRowNum(newSpot) == 8)){
//                String color = board.checkSquare(newSpot).getColor();
//                board.removePiece(newSpot);
//                board.addPiece(new Queen(color, newSpot), newSpot);
//            }
//            // castling
//            if(board.checkSquare(newSpot) instanceof King && ){
//
//            }
//
//        }
//
//    }

    /**
     * Moves a piece from currSpot to newSpot. Includes castling and en passent.
     *
     * Preconditions:
     * - There is a piece at the location represented by currSpot
     * - The move is valid
     *
     * @param currSpot The current location of the piece being moved
     * @param newSpot  The target location of the piece being moved
     */
    public void makeMove(String currSpot, String newSpot){
        Piece movedPiece = board.removePiece(currSpot);
        if (movedPiece == null) return;

        //Check whether en passent was made
        if(movedPiece instanceof Pawn
                && !currSpot.substring(0, 1).equals(newSpot.substring(0, 1))
                && board.checkSquareEmpty(newSpot)){
            makeEnPassent(newSpot);
        //Check whether castling was made
        }else if(movedPiece instanceof King
                && movedPiece.getUnmoved()
                && (newSpot.equals("c1") || newSpot.equals("c8") || newSpot.equals("g1") || newSpot.equals("g8"))){

            this.makeCastle(newSpot);
        }
        //Place movedPiece in its new spot
        if(!board.checkSquareEmpty(newSpot)){
            Piece taken = board.removePiece(newSpot);
            taken.eliminate();
            if(playerWhiteInTurn){
                blackPiecesOut.add(taken);
            }else{
                whitePiecesOut.add(taken);
            }
        }
        board.addPiece(movedPiece, newSpot);
        movedPiece.move(newSpot);
        if (movedPiece instanceof Pawn && checkMovedTwice(currSpot, newSpot)){
            ((Pawn) movedPiece).movedTwice();
        }
        //Clear enemy pieces' movedTwice status and switch player in turn
        this.clearMovedTwice(playerWhiteInTurn);
        if(playerWhiteInTurn){
            playerWhiteInTurn = false;
        }else{
            playerWhiteInTurn = true;
        }

        String code = currSpot + newSpot;
        Move move = new Move("", code, moveNumber);
        moveNumber++;
        Database.insert(Database.Collections.MOVES, move, ()->{});
    }

    /**
     * A helper method for makeMove. Removes the enemy piece in en passent.
     *
     * @param newSpot The target location of the piece being moved
     */
    private void makeEnPassent(String newSpot){
        String colLetter = newSpot.substring(0, 1);
        int rowNum = Integer.parseInt(newSpot.substring(1, 2));
        if(playerWhiteInTurn){
            rowNum -= 1;
            String takenLoc = colLetter + String.valueOf(rowNum);
            Piece removed = board.removePiece(takenLoc);
            //removed.eliminate();
            this.blackPiecesOut.add(removed);
        }else{
            rowNum += 1;
            String takenLoc = colLetter + String.valueOf(rowNum);
            Piece removed = board.removePiece(takenLoc);
            //removed.eliminate();
            this.whitePiecesOut.add(removed);
        }
    }

    /**
     * A helper method for makeMove. Moves the rook used to castle.
     *
     * @param newSpot The target location of the piece being moved.
     */
    private void makeCastle(String newSpot){
        String colLetter = newSpot.substring(0, 1);
        if(playerWhiteInTurn){
            if(colLetter == "c"){
                Piece rook = board.removePiece("a1");
                rook.move("d1");
                board.addPiece(rook, "d1");
            }else{
                Piece rook = board.removePiece("h1");
                rook.move("f1");
                board.addPiece(rook, "f1");
            }
        }else{
            if(colLetter == "c"){
                Piece rook = board.removePiece("a8");
                rook.move("d8");
                board.addPiece(rook, "d8");
            }else{
                Piece rook = board.removePiece("h8");
                rook.move("f8");
                board.addPiece(rook, "f8");
            }
        }
    }

    /**
     * A helper method for makeMove. Returns whether the piece being moved is double-stepping.
     *
     * @param currSpot the current location of the piece being moved.
     * @param newSpot  The target location of the piece being moved.
     * @return true if the move is a double-step and false otherwise.
     */
    private boolean checkMovedTwice(String currSpot, String newSpot){
        int currRowNum = Integer.parseInt(currSpot.substring(1, 2));
        int newRowNum = Integer.parseInt(newSpot.substring(1, 2));
        int difference = Math.abs(currRowNum - newRowNum);
        return difference == 2;
    }

    /**
     * A helper method for makeMove. Clears the double-stepped status from all enemy pawns on board.
     *
     * @param PlayerWhiteInTurn A boolean that is true when the current player is white and false otherwise.
     */
    private void clearMovedTwice(boolean PlayerWhiteInTurn){
        String enemyColor;
        if(PlayerWhiteInTurn){
            enemyColor = "Black";
        }else{
            enemyColor = "White";
        }
        for(String column : LETTER_COORDINATES){
            String colLetter = column;
            for(int i = 1; i < 9; ++i){
                String squareToCheck = colLetter + String.valueOf(i);
                Piece piece = board.checkSquare(squareToCheck);
                if(piece instanceof Pawn && piece.getColor() == enemyColor){
                    ((Pawn) piece).clearMovedTwice();
                }
            }
        }
    }

    /**
     *
     * @return an array list of eliminated white pieces.
     */
    public ArrayList<Piece> getWhitePiecesOut(){
        return this.whitePiecesOut;
    }

    /**
     *
     * @return an array list of eliminated black pieces.
     */
    public ArrayList<Piece> getBlackPiecesOut(){
        return this.blackPiecesOut;
    }

    /**
     *
     * @return true if it is white player's turn and false otherwise.
     */
    public boolean isPlayerWhiteInTurn(){
        return this.playerWhiteInTurn;
    }



    public static String updateClock(){
        Clock egclock = new Clock(0,0,0);
        return egclock.getTime();
    }

    /*public ArrayList<Piece> getWhitePiecesOut(){
        return this.whitePiecesOut;
    }

    public ArrayList<Piece> getBlackPiecesOut(){
        return this.whitePiecesOut;
    }*/
}

