package com.wildcats.ultimatechess;

public interface Piece {

    String color = "White";
    String location = "a1";
    boolean eliminated = false;

    public String[] getPossibleMoves(Piece[][] board);

}