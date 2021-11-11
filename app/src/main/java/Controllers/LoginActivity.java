package Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.wildcats.ultimatechess.R;
import Interface.User;
import Interface.Document;
import Interface.Database;

// Controls user input for the Login page.
public class LoginActivity extends AppCompatActivity {

    private EditText eTxt_username, eTxt_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind xml containing the GUI.
        setContentView(R.layout.activity_login);

        // Bind Java variables to xml elements.
        eTxt_username = findViewById(R.id.eTxt_username);
        eTxt_password = findViewById(R.id.eTxt_password);
        btn_login = findViewById(R.id.btn_login);

        // Run onLoginClicked() when the login button is clicked.
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onLoginClicked(); }
        });

    }

    private void onLoginClicked() {

        // Get username and password from the GUI.
        String username = eTxt_username.getText().toString();
        String password = eTxt_password.getText().toString();

        // "fetch" gets documents from a collection in the database.
        // Once the docs are fetched, the method specified as a second parameter runs.
        // Notice it is a lambda function and the docs are a parameter.
        Database.fetch(Database.Collections.USERS, docs -> {
            // Go through all the docs and if a name and password match the ones specified
            // by the user, open the start activity.
            for (Document doc : docs) {
                User user = (User)doc;
                if (user.getName().equals(username) && user.getPassword().equals(password)) {
                    openStartActivity(username, user.getDocumentId());
                    return;
                }
            }
        });

    }

    // Closes the login page and open the start page.
    private void openStartActivity(String username, String id) {
        // An "intent" allows us to pass variables between activities.
        // Here we are passing the username and id (they will come in handy in the other activities).
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userid", id);
        startActivity(intent);
    }

}