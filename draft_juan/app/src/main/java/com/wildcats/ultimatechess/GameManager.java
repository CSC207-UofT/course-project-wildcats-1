package com.wildcats.ultimatechess;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {

    private Board board;
    private ArrayList<Piece> whitePiecesOut, blackPiecesOut;
    private User playerWhite, playerBlack;
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

    public void setUpWhite(User white) {
        playerWhite = white;
    }

    public void setUpBlack(User black) {
        playerBlack = black;
    }

    public void endGame() {

    }

    public Board getBoard() {
        return board;
    }

    public void makeMove(Piece piece, String location) {

    }

    public boolean checkValidPawnMove(Pawn pawn, String loc) {

        int colDiff = Arrays.asList(LETTER_COORDINATES).indexOf(loc.substring(0,1)) -
                Arrays.asList(LETTER_COORDINATES).indexOf(pawn.location.substring(0,1));
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(pawn.location.substring(1));

        if (colDiff == 0 && rowDiff == 1 && pawn.color.equals("white") &&
                this.board.checkSquareEmpty(loc)) {
            return true;
        } else if (colDiff == 0 && rowDiff == 2 && pawn.color.equals("white") &&
                pawn.firstMove && this.board.checkSquareEmpty(loc)) {
            return true;
        } else if (colDiff == 0 && rowDiff == -1 && pawn.color.equals("black") &&
                this.board.checkSquareEmpty(loc)) {
            return true;
        } else if (colDiff == 0 && rowDiff == -2 && pawn.color.equals("black") &&
                pawn.firstMove && this.board.checkSquareEmpty(loc)) {
            return true;
        } else if (Math.abs(colDiff) == 1 && rowDiff == 1 && pawn.color.equals("white") &&
                pawn.movedTwo && this.board.checkSquareEmpty(loc)) {
            String enemy_loc = loc.charAt(0) + pawn.location.substring(1);
            Piece poss_pawn = this.board.checkSquare(enemy_loc);
            return (poss_pawn != null && poss_pawn.color.equals("black") &&
                    poss_pawn instanceof Pawn);
        } else if (Math.abs(colDiff) == 1 && rowDiff == -1 && pawn.color.equals("black") &&
                pawn.movedTwo && this.board.checkSquareEmpty(loc)) {
            String enemy_loc = loc.charAt(0) + pawn.location.substring(1);
            Piece poss_pawn = this.board.checkSquare(enemy_loc);
            return (poss_pawn != null && poss_pawn.color.equals("white") &&
                    poss_pawn instanceof Pawn);
        }

        return false;
    }

}