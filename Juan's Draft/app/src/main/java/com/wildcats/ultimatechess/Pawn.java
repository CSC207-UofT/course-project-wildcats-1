package com.wildcats.ultimatechess;

public class Pawn implements Piece {

    String color;
    String location;
    boolean eliminated;

    public String[] getPossibleMoves(Piece[][] board) {
        return new String[0];
    }

}