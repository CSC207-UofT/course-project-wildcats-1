package Entities;
import com.wildcats.ultimatechess.R;


public class Pawn extends Piece {


    /**
     * A PawnState representing whether or not the pawn is promoted.
     */
    PawnState pawnState;

    /**
     * A new Pawn object with its own color and unique location.
     *
     * @param color The color of this Pawn Object.
     * @param location The location of this Pawn object on the chessboard as a coordinate.
     */
    public Pawn(String color, String location) {
        super(color, location);
        if (this.color.equals("White")){
            this.image = R.drawable.pawn_white;
        }
        else{
            this.image = R.drawable.pawn_black;
        }
        this.pawnState = new NormalPawnState(this);
    }

    /** Return True if this Pawn object has moved two units, otherwise return False.
     * @return whether this Pawn object has moved two units.
     */
    public boolean getMovedTwice() {
        return this.pawnState.getMovedTwice();
    }

    /**
     * Mutate the movedTwice attribute to true, to indicate that this Pawn object has double-stepped.
     */
    public void movedTwice(){
        this.pawnState.movedTwice();
    }

    /**
     * Mutate the movedTwice attribute to false, to indicate that this pawn cannot be taken by en passent.
     */
    public void clearMovedTwice(){
        this.pawnState.clearMovedTwice();
    }

    @Override
    /**
     * @return a deep copy of this Piece object.
     */
    Piece getCopy() {
        return new Pawn(this.color, this.location);
    }

    /**
     * Promotes this pawn.
     */
    public void promote(){
        this.pawnState = new PromotedPawnState(this);
        if (this.color.equals("White")){
            this.image = R.drawable.queen_white;
        }
        else{
            this.image = R.drawable.queen_black;
        }
    }

    /**
     *
     * @return true if this pawn is promoted and false otherwise.
     */
    public boolean isPromoted() {
        return this.pawnState.isPromoted();
    }
}