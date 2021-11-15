package UseCases;

/**
 * An interface for building a game and its GameManager.
 */
public interface GameBuilder {

    public void buildBoard();

    public GameManager getGame();

}