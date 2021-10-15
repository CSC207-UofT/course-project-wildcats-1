package com.wildcats.ultimatechess;

public class Rook extends Piece {

    public Rook(String color, String location) {
        super(color, location);
    }

    @Override
    public String[] getPossibleMoves(Piece[][] board) {
        return new String[0];
    }

}