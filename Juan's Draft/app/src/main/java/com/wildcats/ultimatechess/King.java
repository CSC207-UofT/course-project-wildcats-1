package com.wildcats.ultimatechess;

public class King extends Piece {

    public King(String color, String location) {
        super(color, location);
    }

    @Override
    public String[] getPossibleMoves(Piece[][] board) {
        return new String[0];
    }

}