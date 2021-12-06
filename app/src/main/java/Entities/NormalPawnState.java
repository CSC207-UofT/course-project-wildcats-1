package Entities;

class NormalPawnState extends PawnState {

    /**
     * A Boolean representing whether or not a Pawn object has made a 2 unit move.
     */
    boolean movedTwo;

    public NormalPawnState(Pawn pawn) {
        super(pawn);
    }

    @Override
    boolean getMovedTwice() {
        return movedTwo;
    }

    @Override
    void movedTwice() {
        movedTwo = true;
    }

    @Override
    void clearMovedTwice() {
        movedTwo = false;
    }

    @Override
    boolean isPromoted() {
        return false;
    }
}
