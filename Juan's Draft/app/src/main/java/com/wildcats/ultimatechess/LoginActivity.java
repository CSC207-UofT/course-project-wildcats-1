package com.wildcats.ultimatechess;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText eTxt_username;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        eTxt_username = findViewById(R.id.eTxt_username);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = eTxt_username.getText().toString();
                if (username.equals("user1") || username.equals("user2"))
                    openStartActivity(username);
            }
        });
    }

    private void openStartActivity(String username) {
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}