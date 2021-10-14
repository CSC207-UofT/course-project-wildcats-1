package com.wildcats.ultimatechess;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    private Button btn_singleplayer;
    private Button btn_multiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        btn_singleplayer = findViewById(R.id.btn_singleplayer);
        btn_singleplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // openGameActivity();
            }
        });

        btn_multiplayer = findViewById(R.id.btn_multiplayer);
        btn_multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameActivity();
            }
        });
    }

    private void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle extras = getIntent().getExtras();
        intent.putExtra("username", extras.getString("username"));
        startActivity(intent);
    }

}