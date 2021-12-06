package Entities;

abstract class PawnState {
    protected Pawn pawn;

    public PawnState(Pawn pawn){
        this.pawn = pawn;
    }

    abstract boolean getMovedTwice();

    abstract void movedTwice();

    abstract void clearMovedTwice();

    abstract boolean isPromoted();
}
