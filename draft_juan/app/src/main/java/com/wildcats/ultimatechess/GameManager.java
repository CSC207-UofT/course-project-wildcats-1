package com.wildcats.ultimatechess;

public class GameManager {

    private Piece[][] board;
    private Piece[] whitePiecesOut, blackPiecesOut;
    private User playerWhite, playerBlack;
    private boolean playerWhiteInTurn;

    public GameManager() {
        board = new Piece[8][8];
    }

    public Piece[][] getBoard() { return board; }

    public void startGame() {
        /**
         * Need to input players, place pieces, set white first
         */
        playerWhiteInTurn = true
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