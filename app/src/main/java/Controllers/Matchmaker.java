package Controllers;

import android.os.Handler;
import java.util.concurrent.ThreadLocalRandom;
import Interface.Database;
import Interface.Document;
import Interface.Game;
import Interface.User.UserRef;

// Responsible to connect players together to play a game.
public class Matchmaker {

    // Allows to specify a lambda function as a parameter of "findOpponentAndJoinGame"
    // and run it inside the method.
    public interface IOnGameJoined {
        void onJoin();
    }

    public static void findOpponentAndJoinGame(String userId, IOnGameJoined event) {

        // (1) Get all the games in the database.
        Database.fetch(Database.Collections.GAMES, gameDocs -> {
            // (2) Check if the user is already in a game.
            boolean userInGame = false;
            for (Document doc : gameDocs) {
                Game game = (Game)doc;
                if (game.getPlayerBlackID().equals(userId)) {
                    userInGame = true;
                }
            }
            if (userInGame) {
                // (2.1) If user in game, join it!
                event.onJoin();
            }
            else {
                // (2.2) If user not in game, find an opponent and create the game.
                // (3) Get all the users matchmaking.
                Database.fetch(Database.Collections.USERS_MATCHMAKING, matchmakingDocs -> {
                    // Find the id of the document storing the current user.
                    // (The collection users_matchmaking stores the ids of the users matchmaking
                    // and every document also has a different id.)
                    String userRefId = "";
                    for (Document doc : matchmakingDocs) {
                        if (((UserRef) doc).getUserID().equals(userId)) {
                            userRefId = doc.getDocumentId();
                        }
                    }
                    // (4) Go through all the users matchmaking.
                    for (Document doc : matchmakingDocs) {
                        String opponentId = ((UserRef) doc).getUserID();
                        // (5) If user not the current user, create a game together.
                        if (!opponentId.equals(userId)) {
                            // (6) Delete the users from the matchmaking collection.
                            // (Both are now in a game so they are not matchmaking anymore.)
                            Database.delete(Database.Collections.USERS_MATCHMAKING, userRefId, ()->{});
                            Database.delete(Database.Collections.USERS_MATCHMAKING, doc.getDocumentId(), ()->{});
                            // (7) Create a game in the database with the two user ids.
                            Game game = new Game(userId, opponentId);
                            Database.insert(Database.Collections.GAMES, game, ()->{});
                            // (8) Run the lambda function.
                            event.onJoin();
                        }
                    }
                    // If a player if not found matchmaking,
                    // run this method recursively after a delay of 1000ms-10000ms.
                    // (A random range in the delay is created to avoid users creating a game
                    // at the same time and not being able to connect together.)
                    int min = 1000;
                    int max = 10000;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findOpponentAndJoinGame(userId, event);
                        }
                    }, ThreadLocalRandom.current().nextInt(min, max + 1));
                });
            }
        });
    }

}