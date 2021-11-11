package Entities;

public class Pawn extends Piece {

    boolean firstMove;
    boolean movedTwo;

    public Pawn(String color, String location) {
        super(color, location);
        this.firstMove = true;
        this.movedTwo = false;

    }

}