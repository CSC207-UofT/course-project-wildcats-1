package Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.wildcats.ultimatechess.R;

public class GameActivity extends AppCompatActivity {

    private TextView txt_playerWhite, txt_playerBlack;
    private TextView txt_chat;
    private Button btn_test;

    private String username, userid;

    @Override
    protected void onStart() {
        super.onStart();

        /*firebaseDocRef = FirebaseFirestore.getInstance().document("chats/chat1");
        firebaseDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
                if (snapshot != null) {
                    String chat = snapshot.getString("text");
                    txt_chat.setText(chat);
                }
            }
        });*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);


    }

}