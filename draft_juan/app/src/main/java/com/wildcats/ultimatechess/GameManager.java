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

    public void startGame() {}
    public void endGame() {}

    public void makeMove() {}

}