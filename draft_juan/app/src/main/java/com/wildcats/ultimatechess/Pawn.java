package com.wildcats.ultimatechess;

public class Pawn extends Piece {

    public Pawn(String color, String location) {
        super(color, location);
    }

    @Override
    public String[] getPossibleMoves(Piece[][] board) {
        return new String[0];
    }

}