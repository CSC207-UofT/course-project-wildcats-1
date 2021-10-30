package com.wildcats.ultimatechess;

public class Board {

    private Piece[][] board;

    public Board(){
        Board board;
    }

    /**
     * Adds a piece onto the board.
     *
     * @param piece The Piece to be added to the Board
     * @param square The string name of the square to which piece is added
     */
    public void addPiece(Piece piece, String squareID){

        int rowNum = squareID.substring(1) - 1;
        int colNum;

        String colLetter = squareID.substring(0, 1);
        if (colLetter == "a") {
            colNum = 0;
        } else if(colLetter == "b") {
            colNum = 1;
        } else if(colLetter == "c") {
            colNum = 2;
        } else if(colLetter == "d") {
            colNum = 3;
        } else if(colLetter == "e") {
            colNum = 4;
        } else if(colLetter == "f") {
            colNum = 5;
        } else if(colLetter == "g") {
            colNum = 6;
        } else if(colLetter == "h") {
            colNum = 7;
        }

        this.board[colNum][rowNum] = piece;
    }

    /**
     * Moves a piece at a given square to another square.
     *
     * @param start The string name of the square containing a piece to be moved
     * @param end   The string name of the square to which the piece will be moved
     */
    public void movePiece(String start, String end){
        //TODO Implement
    }
    public boolean checkSquare(String squareID){

    }
}