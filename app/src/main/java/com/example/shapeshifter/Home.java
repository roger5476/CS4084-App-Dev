package com.example.shapeshifter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.widget.TextView;

import com.example.shapeshifter.data.Map;
import com.example.shapeshifter.data.UserDbHelper;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = prefs.getString("username", "");

        // Use the username to retrieve the first name from the database
        UserDbHelper dbHelper = new UserDbHelper(this);
        String firstName = dbHelper.getUserFirstName(loggedInUsername);

        // Display the first name in the greeting message
        TextView greetingTextView = findViewById(R.id.greeting_text_view);
        greetingTextView.setText("Hello " + firstName + "! What would you like to do today?");

        // Find the Sign Out button
        Button signOutButton = findViewById(R.id.sign_out_button);

        Button mapsButton = findViewById(R.id.maps_button);

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Map.class);
                startActivity(intent);
            }
        });

// Set OnClickListener for the Sign Out button
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start MainActivity
                Intent intent = new Intent(Home.this, MainActivity.class);
                // Add flags to clear the back stack and start the new activity as a new task
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                // Start MainActivity
                startActivity(intent);
                // Finish the current activity (Home)
                finish();
            }
        });
    }
}