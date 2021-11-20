package Interfaces;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import Interfaces.User.UserRef;

/* Bridge between the Java code and the Firebase database.
 * Enables the basics database operations: insert, update, delete and fetch.
 *
 * This class serves as a wrapper of the Firebase API.
 * No Firebase specific code should be used outside of the "Database" class.
 *
 * Database model:
 * Collections containing documents.
 * Every document in a collection follows the same format and they all have a unique ids.
 * The format of a document is specified with a Java class that inherits from "Document".
 * For example, the collection "users" contains documents created with the class "User".
 */
public class Database {

    // Enum storing the collections that make up the database.
    public enum Collections {
        USERS, GAMES, MOVES, USERS_MATCHMAKING
    }

    // IOnComplete and IOnCompleteFetch are interfaces that allow methods to use lambda params.
    // These lambdas can be run anywhere in inside the method.
    // More info on lambdas: https://www.w3schools.com/java/java_lambda.asp

    public interface IOnComplete {
        void onComplete();
    }

    public interface IOnCompleteFetch {
        void onComplete(List<Document> entries);
    }

    // Insert a new document in a collection.
    public static void insert(Collections collection, Document document, IOnComplete event) {
        // Get a reference of the Firebase collection.
        CollectionReference firebaseCollection =
            FirebaseFirestore.getInstance().collection(getCollectionString(collection));
        // Add the document to the collection and run "event" when action completed.
        firebaseCollection.add(document).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                event.onComplete();
            }
        });
    }

    // Update a document from a collection.
    public static void update(Collections collection, String id, Document document, IOnComplete event) {
        // Create the document path using the collection and the document id.
        String docPath = getCollectionString(collection) + "/" + id;
        // Update the document and run "event" when action completed.
        FirebaseFirestore.getInstance().document(docPath).set(document).addOnSuccessListener(
                new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                event.onComplete();
            }
        });
    }

    // Delete a document from a collection.
    public static void delete(Collections collection, String id, IOnComplete event) {
        // Create the document path using the collection and the document id.
        String docPath = getCollectionString(collection) + "/" + id;
        // Delete the document and run "event" when action completed.
        FirebaseFirestore.getInstance().document(docPath).delete().addOnSuccessListener(
                new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                event.onComplete();
            }
        });
    }

    public static void fetch(Collections collection, IOnCompleteFetch event) {
        // Get a reference of the Firebase collection.
        CollectionReference firebaseCollection =
            FirebaseFirestore.getInstance().collection(getCollectionString(collection));
        // Get a "snapshot" of the collection.
        firebaseCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                // The following code runs when the snapshot was successfully captured.
                List<Document> docs = new ArrayList<>();
                List<DocumentSnapshot> docSnapshots = snapshot.getDocuments();
                // Go through all the documents in the snapshot and depending on the collection,
                // create the appropriate subclass of "Document" and add it to the docs list.
                for (DocumentSnapshot docSnapshot : docSnapshots) {
                    switch (collection) {
                        // "toObject" allows to create a subclass of "Document" using the data
                        // coming from the database.
                        case USERS: docs.add(docSnapshot.toObject(User.class)); break;
                        case GAMES: docs.add(docSnapshot.toObject(Game.class)); break;
                        case MOVES: docs.add(docSnapshot.toObject(Move.class)); break;
                        case USERS_MATCHMAKING: docs.add(docSnapshot.toObject(UserRef.class)); break;
                    }
                }
                // Finally, run the lambda specifying the docs list as a parameter.
                // The docs can now be accessed inside the lambda.
                event.onComplete(docs);
            }
        });
    }

    // Convert Collection enum element to a string.
    // Useful to create database paths.
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