package Entities;

class PromotedPawnState extends PawnState {

    public PromotedPawnState(Pawn pawn) {
        super(pawn);
    }

    @Override
    boolean getMovedTwice() {
        return false;
    }

    @Override
    void movedTwice() { }

    @Override
    void clearMovedTwice() { }

    @Override
    boolean isPromoted() {
        return true;
    }
}
