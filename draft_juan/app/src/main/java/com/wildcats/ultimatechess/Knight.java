package com.wildcats.ultimatechess;

public class Knight extends Piece {

    public Knight(String color, String location) {
        super(color, location);
    }

    @Override
    public String[] getPossibleMoves(Piece[][] board) {
        return new String[0];
    }

}