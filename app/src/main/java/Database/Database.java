package Database;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public enum Collections {
        USERS, GAMES, MOVES
    }

    public interface IOnCompleteGetEntries {
        void onComplete(List<Document> entries);
    }

    public void Database() {}

    public static void getDocs(Collections collection, IOnCompleteGetEntries event) {

        CollectionReference firebaseCollection =
            FirebaseFirestore.getInstance().collection(getCollectionString(collection));

        firebaseCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                List<Document> docs = new ArrayList<>();
                List<DocumentSnapshot> docSnapshots = snapshot.getDocuments();
                for (DocumentSnapshot docSnapshot : docSnapshots) {
                    Document doc = new Document();
                    switch (collection) {
                        case USERS: doc = docSnapshot.toObject(User.class); break;
                        case GAMES: doc = docSnapshot.toObject(Game.class); break;
                        case MOVES: doc = docSnapshot.toObject(Move.class); break;
                    }
                    doc.setID(docSnapshot.getId());
                    docs.add(doc);
                }
                event.onComplete(docs);
            }
        });

    }

    public static boolean isInCollection(Object entry) {


        return false;
    }

    public static String getCollectionString(Collections collection) {
        switch (collection) {
            case USERS: return "users";
            case GAMES: return "games";
            case MOVES: return "moves";
        }
        return "";
    }

}