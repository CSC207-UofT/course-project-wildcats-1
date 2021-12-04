package Entities;

public class Board {

    private Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
    }

    /**
     * Return a copy of this board as an array of array of pieces.
     *
     * @return A copy of this board object's pieces as an array of an array of pieces.
     */
    public Piece[][] getBoard() {
        Piece[][] copyBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            copyBoard[i] = this.board[i].clone();
        }
        return copyBoard;
    }

    /**
     * Set this Board's pieces to be those of <pieces>
     */
    public void setBoard (Piece[][] pieces) {
        this.board = pieces;
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
     * Removes and returns a piece on the board at a specified square.
     *
     * Precondition: The square is not empty.
     *
     * @param squareID The string name of the square in which a piece is removed
     *
     * @return The removed piece
     */
    public Piece removePiece(String squareID){

        int rowNum = this.getRowNum(squareID);
        int colNum = this.getColNum(squareID);

        Piece removedPiece = this.board[colNum][rowNum];
        this.board[colNum][rowNum] = null;
        return removedPiece;
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
     *
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
     * @return The piece at the specified square, or null if the square is empty.
     */
    public Piece checkSquare(String squareID) {
        return this.board[this.getColNum(squareID)][this.getRowNum(squareID)];
    }

    /**
     * Returns the row number of the square as an int that can be used in board methods
     *
     * @param squareID The String representation of the square on the board.
     */
    public int getRowNum(String squareID) {

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

        switch (colLetter) {
            case "a":
                colNum = 0;
                break;
            case "b":
                colNum = 1;
                break;
            case "c":
                colNum = 2;
                break;
            case "d":
                colNum = 3;
                break;
            case "e":
                colNum = 4;
                break;
            case "f":
                colNum = 5;
                break;
            case "g":
                colNum = 6;
                break;
            case "h":
                colNum = 7;
                break;
        }
        return colNum;
    }
}