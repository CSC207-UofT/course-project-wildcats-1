package Database;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import Database.User.UserRef;

public class Database {

    public enum Collections {
        USERS, GAMES, MOVES, USERS_MATCHMAKING
    }

    public interface IOnComplete {
        void onComplete();
    }

    public interface IOnCompleteFetch {
        void onComplete(List<Document> entries);
    }

    public static void insert(Collections collection, Document document, IOnComplete event) {
        CollectionReference firebaseCollection =
            FirebaseFirestore.getInstance().collection(getCollectionString(collection));
        firebaseCollection.add(document).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                event.onComplete();
            }
        });
    }

    public static void update(Collections collection, String id, Document document, IOnComplete event) {
        String docPath = getCollectionString(collection) + "/" + id;
        FirebaseFirestore.getInstance().document(docPath).set(document).addOnSuccessListener(
                new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                event.onComplete();
            }
        });
    }

    public static void delete(Collections collection, String id, IOnComplete event) {
        String docPath = getCollectionString(collection) + "/" + id;
        FirebaseFirestore.getInstance().document(docPath).delete().addOnSuccessListener(
                new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                event.onComplete();
            }
        });
    }

    public static void fetch(Collections collection, IOnCompleteFetch event) {
        CollectionReference firebaseCollection =
            FirebaseFirestore.getInstance().collection(getCollectionString(collection));
        firebaseCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                List<Document> docs = new ArrayList<>();
                List<DocumentSnapshot> docSnapshots = snapshot.getDocuments();
                for (DocumentSnapshot docSnapshot : docSnapshots) {
                    switch (collection) {
                        case USERS: docs.add(docSnapshot.toObject(User.class)); break;
                        case GAMES: docs.add(docSnapshot.toObject(Game.class)); break;
                        case MOVES: docs.add(docSnapshot.toObject(Move.class)); break;
                        case USERS_MATCHMAKING: docs.add(docSnapshot.toObject(UserRef.class)); break;
                    }
                }
                event.onComplete(docs);
            }
        });
    }

    private static String getCollectionString(Collections collection) {
        switch (collection) {
            case USERS: return "users";
            case GAMES: return "games";
            case MOVES: return "moves";
            case USERS_MATCHMAKING: return "users_matchmaking";
        }
        return "";
    }

}