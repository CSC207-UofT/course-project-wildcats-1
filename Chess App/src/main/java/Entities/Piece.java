package Entities;

/**
 * A Piece object with a color, location and elimination status.
 */
public class Piece {

    /**
     * The color of this Piece object.
     */
    String color;

    /**
     * The location of this Piece object.
     */
    String location;

    /**
     * The elimination status of this Piece object.
     */
    boolean eliminated;

    /**
     * A Boolean representing whether or not a Piece object has made its first move.
     */
    boolean unmoved;

    /**
     * A new Piece object with its own color and unique location.
     *
     * @param color The color of this Pawn Object.
     * @param location The location of this Pawn object on the chessboard as a coordinate.
     */
    public Piece(String color, String location) {
        this.color = color;
        this.location = location;
        this.eliminated = false;
        this.unmoved = true;
    }

    /** Return the color of this Piece object.
     * @return the color of this Piece object.
     */
    public String getColor() {
        return color;
    }

    /** Return the location of this Piece object as a chess coordinate.
     * @return the location of this Piece object.
     */
    public String getLocation() {
        return location;
    }

    /** Return whether True if this Piece object has moved, otherwise return False.
     * @return whether this Piece object has made its first move
     */
    public boolean getPlayStatus() {
        return this.unmoved;
    }

    /** Return the elimination status of this Piece object.
     * @return whether this Piece object has been eliminated.
     */
    public boolean isEliminated() {
        return eliminated;
    }

    /** Move this Piece object to a new location.
     * @param location The location to which this Piece object will move.
     */
    public void move(String location) {
        this.location = location;
    }

    /**
     * Eliminate this Piece object, setting its elimination status to be true, and assigning
     * an empty string as its location.
     */
    public void eliminate() {
        this.eliminated = true;
        this.location = "";
    }

}