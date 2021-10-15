package com.wildcats.ultimatechess;

import java.util.ArrayList

public class Pawn extends Piece {

    private boolean unmoved;

    public Pawn(String color, String location) {
        super(color, location);
        unmoved = true;
    }

    @Override
    public String[] getPossibleMoves(Piece[][] board) {
        String[] movelist;
        String col = ((Piece) this).getLocation().substring(0, 1);
        String row = ((Piece) this).getLocation().substring(1);
        int rowNum = Integer.parseInt(row);

        if(unmoved){
            movelist = new String[2];
            if(((Piece) this).getColor() == "White"){
                movelist[0] = col + String.valueOf(rowNum + 1);
                movelist[1] = col + String.valueOf(rowNum + 2);
            } else{
                movelist[0] = col + String.valueOf(rowNum - 1);
                movelist[1] = col + String.valueOf(rowNum - 2);
            }
        } else{
            if(((Piece) this).getColor() == "White"){
                if (rowNum == 8){
                    movelist = new String[0];
                } else{
                    movelist = new String[1];
                    movelist[0] = col + String.valueOf(rowNum + 1);
                }
            } else{
                if (rowNum == 0){
                    movelist = new String[0];
                } else{
                    movelist = new String[1];
                    movelist[0] = col + String.valueOf(rowNum - 1);
                }
            }
        }
        return(movelist);
    }

}
