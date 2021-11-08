package Entities;

public class Piece {

    String color;
    String location;
    boolean eliminated;

    public Piece(String color, String location) {
        this.color = color;
        this.location = location;
        this.eliminated = false;
    }

    public String getColor() { return color; }
    public String getLocation() { return location; }
    public boolean isEliminated() { return eliminated; }

    public void move(String location) {
        this.location = location;
    }

    public void eliminate() {
        this.eliminated = true;
        this.location = "";
    }

}