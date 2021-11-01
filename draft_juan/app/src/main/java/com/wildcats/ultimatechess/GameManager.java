package com.wildcats.ultimatechess;

import java.util.ArrayList;

public class GameManager {

    private Piece[][] board;
    private ArrayList<Piece> whitePiecesOut, blackPiecesOut;
    private User playerWhite, playerBlack;
    private boolean playerWhiteInTurn;

    /**
     *
     * @param inputBoard The starting board containing all pieces needed to start the game.
     */
    public GameManager(Board inputBoard) {
        board = inputBoard;
    }

    public Piece[][] getBoard() { return board; }

    public void startGame() {
        /**
         * Need to input players, place pieces, set white first
         */
        playerWhiteInTurn = true;
    }

    public void setUpWhite(User white) {
        playerWhite = white;
    }

    public void setUpBlack(User black) {
        playerBlack = black;
    }
    public void endGame() {}

    public void makeMove(Piece piece, String location) {

    }

}