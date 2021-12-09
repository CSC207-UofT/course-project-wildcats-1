package UseCases;

/**
 * An interface for building a game and its GameManager.
 */
public interface GameBuilder {

    void buildBoard();

    GameManager getGame();

}