package com.wildcats.ultimatechess;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private TextView txt_playerWhite, txt_playerBlack;
    private TextView txt_chat;
    private Button btn_test;

    private DocumentReference firebaseDocRef;
    private String username;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseDocRef = FirebaseFirestore.getInstance().document("chats/chat1");
        firebaseDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
                if (snapshot != null) {
                    String chat = snapshot.getString("text");
                    txt_chat.setText(chat);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        txt_playerWhite = findViewById(R.id.txt_playerWhite);
        txt_playerBlack = findViewById(R.id.txt_playerBlack);
        txt_chat = findViewById(R.id.txt_chat);
        btn_test = findViewById(R.id.btn_test);

        txt_playerWhite.setText("user1");
        txt_playerBlack.setText("user2");

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot snapshot) {
                        String newChatInput = username + "\n";
                        String chat = snapshot.getString("text");
                        Map<String, Object> data = new HashMap<String, Object>();
                        data.put("text", chat + newChatInput);
                        firebaseDocRef.set(data);
                    }
                });
            }
        });
    }

}