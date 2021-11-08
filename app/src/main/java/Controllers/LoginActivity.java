package Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wildcats.ultimatechess.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText eTxt_username, eTxt_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        eTxt_username = findViewById(R.id.eTxt_username);
        eTxt_password = findViewById(R.id.eTxt_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onLoginClicked(); }
        });
    }

    private void onLoginClicked() {
        String username = eTxt_username.getText().toString();
        String password = eTxt_password.getText().toString();

        CollectionReference firebaseUsers = FirebaseFirestore.getInstance().collection("users");
        firebaseUsers.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                String id = "";
                List<DocumentSnapshot> users = snapshot.getDocuments();
                for (DocumentSnapshot user : users) {
                    if (user.getString("name").equals(username)
                            && user.getString("password").equals(password)) {
                        id = user.getId();
                        openStartActivity(username, id);
                    }
                }
            }
        });
    }

    private void openStartActivity(String username, String id) {
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userid", id);
        startActivity(intent);
    }

}