package UseCases;

import android.os.Handler;
import java.util.concurrent.ThreadLocalRandom;
import Database.Database;
import Database.Document;
import Database.Game;
import Database.User;
import Database.User.UserRef;

public class Matchmaker {

    public interface IOnGameJoined {
        void onJoin();
    }

    public static void findOpponentAndJoinGame(String userId, IOnGameJoined event) {

        Database.fetch(Database.Collections.GAMES, gameDocs -> {
            boolean userInGame = false;
            for (Document doc : gameDocs) {
                Game game = (Game)doc;
                if (game.getPlayerBlackID().equals(userId)) {
                    userInGame = true;
                }
            }
            if (userInGame) {
                event.onJoin();
            }
            else {
                Database.fetch(Database.Collections.USERS_MATCHMAKING, matchmakingDocs -> {
                    for (Document doc : matchmakingDocs) {
                        String opponentId = ((UserRef) doc).getUserID();
                        if (!opponentId.equals(userId)) {
                            Database.delete(Database.Collections.USERS_MATCHMAKING, userId, ()->{});
                            Database.delete(Database.Collections.USERS_MATCHMAKING, opponentId, ()->{});
                            Game game = new Game(userId, opponentId);
                            Database.insert(Database.Collections.GAMES, game, ()->{});
                            event.onJoin();
                        }
                    }
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