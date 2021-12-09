package UseCases;

import java.util.ArrayList;

import Entities.*;
import Entities.King;
import Entities.Pawn;
import Entities.Piece;
import Interfaces.Database;
import Interfaces.Move;

public class GameManager {

    private final Board board;

    private final String gameID;

    private final ArrayList<Piece> whitePiecesOut;
    private final ArrayList<Piece> blackPiecesOut;

    private int moveNumber = 1;

    /**
     * Letters representing the chess board columns.
     */
    private final String[] COLUMNS =
            new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
    /**
     * Integers representing the chess board rows.
     */
    private final int[] ROWS =
            new int[]{1, 2, 3, 4, 5, 6, 7, 8};


    /**
     * Boolean representing whether it is White's turn, or not (Black's turn).
     */
    private boolean playerWhiteInTurn;

    /**
     *
     * @param inputBoard The starting board containing all pieces needed to start the game.
     */
    public GameManager(Board inputBoard) {
        this.board = inputBoard;
        this.gameID = String.valueOf((int)(Math.random() * 999999));
        this.whitePiecesOut = new ArrayList<>();
        this.blackPiecesOut = new ArrayList<>();
        this.playerWhiteInTurn = true;

    }

    /**
     * @return This GameManager's board object.
     */
    public Board getBoard() {
        return board;
    }

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
        Piece movedPiece = board.checkSquare(currSpot);

        // Check that the move being made is by the right player
        if (movedPiece != null && movedPiece.getColor().equals("White") && playerWhiteInTurn ||
                movedPiece != null && movedPiece.getColor().equals("Black") && !playerWhiteInTurn){

            // removed piece
            Piece removedPiece = board.removePiece(currSpot);


            if (removedPiece instanceof Pawn) {
                //Check whether en passant occurred
                if (!currSpot.substring(0, 1).equals(newSpot.substring(0, 1))
                        && board.checkSquareEmpty(newSpot)) {
                    this.makeEnPassant(newSpot);
                }
                //Check whether pawn promotion occurred
                else if (playerWhiteInTurn && newSpot.substring(1).equals("8")
                        || !playerWhiteInTurn && newSpot.substring(1).equals("1") ) {
                    ((Pawn) removedPiece).promote();
                    //TODO Pawn Promotion
                }
            }
            //Check whether castling occurred
            else if (removedPiece instanceof King && removedPiece.getUnmoved()
                    && (newSpot.equals("c1") || newSpot.equals("c8")
                    || newSpot.equals("g1") || newSpot.equals("g8"))) {
                this.makeCastling(newSpot);
            }

            //Place movedPiece in its new spot and take removedPiece out of play
            if (!board.checkSquareEmpty(newSpot)) {
                Piece taken = board.removePiece(newSpot);
                taken.eliminate();
                if (playerWhiteInTurn){
                    blackPiecesOut.add(taken);
                } else {
                    whitePiecesOut.add(taken);
                }
            }
            board.addPiece(removedPiece, newSpot);

            removedPiece.move(newSpot);
            if (removedPiece instanceof Pawn && checkMovedTwice(currSpot, newSpot)) {
                ((Pawn) removedPiece).movedTwice();
            }
            //Clear enemy pieces' movedTwice status and switch player in turn
            this.clearMovedTwice(playerWhiteInTurn);
            playerWhiteInTurn = !playerWhiteInTurn;

            String code = currSpot + newSpot;
            Move move = new Move(gameID, code, moveNumber);
            moveNumber++;
            Database.insert(Database.Collections.MOVES, move, () -> {});
        }
    }

    /**
     * A helper method for makeMove. Removes the enemy piece in en passent.
     *
     * @param newSpot The target location of the piece being moved
     */
    private void makeEnPassant(String newSpot){
        String colLetter = newSpot.substring(0, 1);
        int rowNum = Integer.parseInt(newSpot.substring(1));
        if(playerWhiteInTurn){
            rowNum -= 1;
            String takenLoc = colLetter + rowNum;
            Piece removed = board.removePiece(takenLoc);
            removed.eliminate();
            this.blackPiecesOut.add(removed);
        }else{
            rowNum += 1;
            String takenLoc = colLetter + rowNum;
            Piece removed = board.removePiece(takenLoc);
            removed.eliminate();
            this.whitePiecesOut.add(removed);
        }
    }

    /**
     * A helper method for makeMove. Moves the rook used to castle.
     *
     * @param newSpot The target location of the piece being moved.
     */
    private void makeCastling(String newSpot){
        String colLetter = newSpot.substring(0, 1);
        if(playerWhiteInTurn){
            if(colLetter.equals("c")){
                Piece rook = board.removePiece("a1");
                rook.move("d1");
                board.addPiece(rook, "d1");
            }else{
                Piece rook = board.removePiece("h1");
                rook.move("f1");
                board.addPiece(rook, "f1");
            }
        }else{
            if(colLetter.equals("c")){
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
        for (int c = 0; c <= 7; c++) {
            for (int r = 0; r <= 7; r++) {
                String squareID = COLUMNS[c] + ROWS[r];
                Piece piece = board.checkSquare(squareID);
                if(piece instanceof Pawn && piece.getColor().equals(enemyColor)){
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


    /**
     *
     * @return string (00 00 00) to initialize display of white player's clock
     * on game window.
     */
    public static String initializeClockWhite(){
        Clock startClock = new Clock();
        return startClock.getStartTimeWhite();
    }

    /**
     *
     * @return string (00 00 00) to initialize display of black player's clock
     * on game window.
     */
    public static String initializeClockBlack(){
        Clock startClock = new Clock();
        return startClock.getStartTimeBlack();
    }

    /**
     *
     * @return string in format (00 00 00) to update display of clock on game window
     * when called in gameloop for White player.
     */
    public static String clockUpdatorWhite(int hours, int mins, int secs){
        Clock updateClock = new Clock();
        return updateClock.clockStringConverterWhite(hours, mins, secs);
    }

    /**
     *
     * @return string in format (00 00 00) to update display of clock on game window
     * when called in gameloop for Black player.
     */
    public static String clockUpdatorBlack(int hours, int mins, int secs){
        Clock updateClock = new Clock();
        return updateClock.clockStringConverterBlack(hours, mins, secs);
    }


    /*public ArrayList<Piece> getWhitePiecesOut(){
        return this.whitePiecesOut;
    }

    public ArrayList<Piece> getBlackPiecesOut(){
        return this.whitePiecesOut;
    }*/
}

