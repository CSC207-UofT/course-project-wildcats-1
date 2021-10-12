package com.wildcats.ultimatechess;

public class GameRules {

    private Piece[][] board;  // Temporary copy of GameManager board.

    public GameRules() {}

    public String[] getPossibleMoves(Piece[][] board, Piece piece, String pieceLocation) {
        String[] moves = new String[0];
        this.board = board;
        if (piece instanceof Pawn) {
            moves = getPossibleMovesPawn(pieceLocation);
        }
        // else if (piece instanceof Rook) {}
        // ...
        return moves;
    }

    private String[] getPossibleMovesPawn(String pieceLocation) {
        return new String[0];
    }

}