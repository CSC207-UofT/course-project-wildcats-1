package com.wildcats.ultimatechess;

/**
 * An interface for building a game and its GameManager.
 */
interface GameBuilder {

    public void constructBoard();

    public void addPlayers();

    public GameManager getGame();

}