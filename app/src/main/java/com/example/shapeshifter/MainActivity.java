package com.example.shapeshifter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shapeshifter.data.UserContract;
import com.example.shapeshifter.data.UserDbHelper;

public class MainActivity extends AppCompatActivity {

    // Declare EditText fields for username and password
    private EditText usernameEditText;
    private EditText passwordEditText;
    //private RecyclerView exerciseRecyclerView;
    //private ExerciseAdapter exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Find EditText fields
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform login logic
                login();
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        // Get username and password entered by the user
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the username and password match an existing user in the database
        if (isLoginSuccessful(username, password)) {

            SharedPreferences.Editor editor = getSharedPreferences("user_session", MODE_PRIVATE).edit();
            editor.putString("username", username);
            editor.apply();
            // If login is successful, navigate to SuccessActivity
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        } else {
            // If login fails (incorrect username or password), display an error message to the user
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isLoginSuccessful(String username, String password) {
        // Retrieve user information based on the entered username
        UserDbHelper dbHelper = new UserDbHelper(this);
        Cursor cursor = dbHelper.getUserByUsername(username);

        // Check if the cursor contains any rows (i.e., if the username exists)
        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve the password from the database
            @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD));

            // Check if the entered password matches the stored password
            if (password.equals(storedPassword)) {
                // Passwords match, login successful
                cursor.close();
                return true;
            }
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }

        // Login failed
        return false;
    }

    /*
    private String loadExerciseDescriptionFromRawResource(int resourceId) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // Open the resource file as an InputStream
            InputStream inputStream = getResources().openRawResource(resourceId);
            // Wrap the InputStream with a BufferedReader for efficiency
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            // Read the file line by line and append to StringBuilder
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            // Close the BufferedReader and InputStream to release resources
            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            // Log an error message if there is a problem reading the file
            e.printStackTrace();
            Log.e("ExerciseDescription", "Error reading description file from raw resource: " + resourceId);
        }
        return stringBuilder.toString();
    }
    */

    /*
    public void onClickExerciseButton(View view) {
        // Create an Intent to start the ExerciseActivity
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);
    }
    */

    /*
    @Override
    public void onItemClick(Exercise exercise) {
        // Respond to item clicks in the ExerciseAdapter
        // Start ExerciseDetailsActivity, passing it details about the clicked exercise
        Intent intent = new Intent(this, ExerciseDetailsActivity.class);
        intent.putExtra("exercise_name", exercise.getName());
        intent.putExtra("exercise_description", exercise.getDescription());
        startActivity(intent);
    }
    */

    /*
    public void onClickGoToDietPlanButton(View view) {
        // Start the DietPlanActivity when the "Diet Plan" button is clicked
        Intent intent = new Intent(this, DietPlanActivity.class);
        startActivity(intent);
    }
    */
}