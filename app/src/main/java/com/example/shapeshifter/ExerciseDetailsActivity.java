
package com.example.superfit;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
public class ExerciseDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        // Set up the Toolbar as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back button in the ActionBar
        // Retrieve exercise name and description from Intent extras
        Bundle extras = getIntent().getExtras();
        String exerciseName = extras.getString("exercise_name");
        String exerciseDescription = extras.getString("exercise_description");
        // Initialize TextViews to display exercise name and description
        TextView exerciseNameTextView = findViewById(R.id.exerciseNameTextView);
        TextView exerciseDescriptionTextView = findViewById(R.id.exerciseDescriptionTextView);
        // Set exercise name and description in respective TextViews
        exerciseNameTextView.setText(exerciseName);
        exerciseDescriptionTextView.setText(exerciseDescription);
    }
    // Method to handle menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button click
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back to previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
