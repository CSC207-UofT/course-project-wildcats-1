package com.wildcats.ultimatechess;

public class Queen extends Piece {

    public Queen(String color, String location) {
        super(color, location);
    }

    @Override
    public String[] getPossibleMoves(Piece[][] board) {
        return new String[0];
    }

}