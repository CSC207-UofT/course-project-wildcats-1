package com.wildcats.ultimatechess;

public class Bishop extends Piece {

    public Bishop(String color, String location) {
        super(color, location);
    }

    @Override
    public String[] getPossibleMoves(Piece[][] board) {
        return new String[0];
    }

}