package UseCases;

/**
 * An interface for building a game and its GameManager.
 */
interface GameBuilder {

    public void buildBoard();

    public GameManager getGame();

}