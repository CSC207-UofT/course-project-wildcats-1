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

        int rowNum = this.getRowNum(squareID);
        int colNum = this.getColNum(squareID);

        this.board[colNum][rowNum] = piece;
    }

    /**
     * Removes a piece on the board at a specified square.
     *
     * Precondition: The square is not empty
     *
     * @param squareID The string name of the square in which a piece is removed
     */
    public void removePiece(String squareID){

        int rowNum = this.getRowNum(squareID);
        int colNum = this.getColNum(squareID);

        this.board[colNum][rowNum] = null;
    }

    /**
     * Moves a piece at a given location to another location.
     *
     * Preconditions:
     * - There is a Piece at the start location
     * - The end location is empty
     *
     * @param start The string name of the square containing a piece to be moved
     * @param end   The string name of the square to which the piece will be moved
     */
    public void movePiece(String start, String end){
        int startRowNum = this.getRowNum(start);
        int startColNum = this.getColNum(start);
        int endRowNum = this.getRowNum(end);
        int endColNum = this.getColNum(end);

        board[endColNum][endRowNum] = board[startColNum][startRowNum];
        board[startColNum][startRowNum] = null;

    }

    /**
     *
     * @param squareID The String representation of the square being checked
     * @return True if the square is empty and False otherwise
     */
    public boolean checkSquareEmpty(String squareID){

        int rowNum = this.getRowNum(squareID);
        int colNum = this.getColNum(squareID);

        return this.board[colNum][rowNum] == null;
    }

    /**
     * Returns the row number of the square as an int that can be used in board methods
     *
     * @param squareID The String representation of the square
     */
    private int getRowNum(String squareID) {

        int rowNum = squareID.substring(1) - 1;
        return rowNum;
    }

    /**
     * Returns the column number of the square as an int that can be used in board methods
     *
     * @param squareID The String representation of the square
     */
    private int getColNum(String squareID) {

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
        return colNum;
    }
}