package com.wildcats.ultimatechess;

public class Board {

    private Piece[][] board;

    public Board(){
        board = new Piece[8][8];
    }

    /**
     * Adds a piece onto the board
     *
     * @param piece The Piece to be added to the Board
     * @param square The string name of the square to which piece is added
     */
    public addPiece(Piece piece, String square){
        //TODO Implement
    }

    /**
     * Moves a piece at a given square to another square
     *
     * @param start The string name of the square containing a piece to be moved
     * @param end   The string name of the square to which the piece will be moved
     *
     * Precondition: The starting square must contain a piece.
     */
    public movePiece(String start, String end){
        //TODO Implement
    }
}