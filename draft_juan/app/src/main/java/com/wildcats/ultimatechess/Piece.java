package com.wildcats.ultimatechess;

public class Piece {

    String color;
    String location;
    boolean eliminated;

    public Piece(String color, String location) {
        this.color = color;
        this.location = location;
        this.eliminated = false;
    }

    String getColor() { return color; }
    String getLocation() { return location; }
    boolean isEliminated() { return eliminated; }

    public void move(String location) {
        this.location = location;
    }

    public void eliminate() {
        this.eliminated = true;
        this.location = "";
    }

}