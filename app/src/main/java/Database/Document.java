package Database;

import com.google.firebase.firestore.DocumentId;

public class Document {

    @DocumentId
    private String documentId;

    public Document() {}
    public String getDocumentId() { return documentId; }

}