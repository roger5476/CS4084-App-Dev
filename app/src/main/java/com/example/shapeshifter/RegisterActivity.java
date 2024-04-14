package com.example.shapeshifter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shapeshifter.data.UserDbHelper;

public class RegisterActivity extends AppCompatActivity {

    // Declare EditText fields
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Find EditText fields
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform registration logic here
                registerUser();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and return to the previous one (MainActivity)
                finish();
            }
        });
    }

    private void registerUser() {
        // Get user input
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate user input
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create UserDbHelper instance
        UserDbHelper dbHelper = new UserDbHelper(getApplicationContext());

        // Insert user into the database
        long newRowId = dbHelper.insertUser(firstName, lastName, username, password);

        // Handle registration result
        if (newRowId != -1) {
            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
            // Optionally, navigate to another activity or perform further actions
        } else {
            Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
        }
    }
}