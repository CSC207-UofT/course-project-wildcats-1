package Pieces;

public class Board {

    private Piece[][] board;

    public Board() {
        Board board;
    }

    /**
     * Adds a piece onto the board.
     *
     * @param piece The Piece to be added to the Board
     * @param squareID The string coordinate of the square to which piece is added
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
    public boolean checkSquareEmpty(String squareID) {

        int rowNum = this.getRowNum(squareID);
        int colNum = this.getColNum(squareID);

        return this.board[colNum][rowNum] == null;
    }

    /**
     * @param squareID The String representation of the square on the board.
     *
     * @return The piece at the specified square, or null.
     */
    public Piece checkSquare(String squareID) {
        return this.board[this.getColNum(squareID)][this.getRowNum(squareID)];
    }

    /**
     * Returns the row number of the square as an int that can be used in board methods
     *
     * @param squareID The String representation of the square on the board.
     */
    private int getRowNum(String squareID) {

        return Integer.parseInt(squareID.substring(1)) - 1;
    }

    /**
     * Returns the column number of the square as an int that can be used in board methods
     *
     * @param squareID The String representation of the square on the board.
     */
    private int getColNum(String squareID) {
        int colNum = -1;
        String colLetter = squareID.substring(0, 1);

        if (colLetter.equals("a")) {
            colNum = 0;
        } else if(colLetter.equals("b")) {
            colNum = 1;
        } else if(colLetter.equals("c")) {
            colNum = 2;
        } else if(colLetter.equals("d")) {
            colNum = 3;
        } else if(colLetter.equals("e")) {
            colNum = 4;
        } else if(colLetter.equals("f")) {
            colNum = 5;
        } else if(colLetter.equals("g")) {
            colNum = 6;
        } else if(colLetter.equals("h")) {
            colNum = 7;
        }
        return colNum;
    }
}