package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.wildcats.ultimatechess.R;
import Database.User;
import Database.Document;
import Database.Database;

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

        Database.fetch(Database.Collections.USERS, docs -> {
            for (Document doc : docs) {
                User user = (User)doc;
                if (user.getName().equals(username) && user.getPassword().equals(password)) {
                    openStartActivity(username, user.getDocumentId());
                    return;
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