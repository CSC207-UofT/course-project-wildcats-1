package com.wildcats.ultimatechess;

class NormalGameBuilder implements GameBuilder {

    private GameManager gm;
    private Board board = new Board();
    private User whitePlayer;
    private User blackPlayer;

    /**
     * Creates Pieces to add to the board for a normal chess game.
     */
    public void buildBoard(){
        //TODO Create pieces for each player and place on board
        this.buildWhiteBoard();
        this.buildBlackBoard();
    }

    /**
     * A helper method for buildBoard, sets up White side.
     */
    private buildWhiteBoard(){

        board.addPiece(new Rook("White", "a1"), "a1");
        board.addPiece(new Knight("White", "b1"), "b1");
        board.addPiece(new Bishop("White", "c1"), "c1");
        board.addPiece(new Queen("White", "d1"), "d1");
        board.addPiece(new King("White", "e1"), "e1");
        board.addPiece(new Bishop("White", "f1"), "f1");
        board.addPiece(new Knight("White", "g1"), "g1");
        board.addPiece(new Rook("White", "h1"), "h1");
        board.addPiece(new Pawn("White", "a2"), "a2");
        board.addPiece(new Pawn("White", "b2"), "b2");
        board.addPiece(new Pawn("White", "c2"), "c2");
        board.addPiece(new Pawn("White", "d2"), "d2");
        board.addPiece(new Pawn("White", "e2"), "e2");
        board.addPiece(new Pawn("White", "f2"), "f2");
        board.addPiece(new Pawn("White", "g2"), "g2");
        board.addPiece(new Pawn("White", "h2"), "h2");

    }

    /**
     * A helper method for buildBoard, sets up Black side.
     */
    private buildBlackBoard(){

        board.addPiece(new Rook("Black", "a8"), "a8");
        board.addPiece(new Knight("Black", "b8"), "b8");
        board.addPiece(new Bishop("Black", "c8"), "c8");
        board.addPiece(new Queen("Black", "d8"), "d8");
        board.addPiece(new King("Black", "e8"), "e8");
        board.addPiece(new Bishop("Black", "f8"), "f8");
        board.addPiece(new Knight("Black", "g8"), "g8");
        board.addPiece(new Rook("Black", "h8"), "h8");
        board.addPiece(new Pawn("Black", "a7"), "a7");
        board.addPiece(new Pawn("Black", "b7"), "b7");
        board.addPiece(new Pawn("Black", "c7"), "c7");
        board.addPiece(new Pawn("Black", "d7"), "d7");
        board.addPiece(new Pawn("Black", "e7"), "e7");
        board.addPiece(new Pawn("Black", "f7"), "f7");
        board.addPiece(new Pawn("Black", "g7"), "g7");
        board.addPiece(new Pawn("Black", "h7"), "h7");


    }

    //TODO How to collect info from database?
    public void buildPlayers();

    public GameManager getGame(){

        gm = new GameManager(board);
        gm.setUpWhite(this.whitePlayer);
        gm.setUpBlack(this.blackPlayer);

        return gm;
    }

}