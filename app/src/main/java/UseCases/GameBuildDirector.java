package UseCases;

/**
 * GameBuildDirector constructs the GameManager when given a specific builder to use.
 */
public class GameBuildDirector {

    private GameBuilder builder;

    public GameBuildDirector(GameBuilder gb){
        builder = gb;
    }

    public void Construct(){
        builder.buildBoard();
    }

    public GameManager getGame(){
        return this.builder.getGame();
    }
}
