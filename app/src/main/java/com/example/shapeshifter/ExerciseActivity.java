package com.example.superfit;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Activity to display a list of exercises using a RecyclerView.
 */
public class ExerciseActivity extends AppCompatActivity implements ExerciseAdapter.OnItemClickListener {
    private RecyclerView exerciseRecyclerView;
    private ExerciseAdapter exerciseAdapter;
    /**
     * Loads exercise descriptions from a raw resource file.
     * @param resourceId The resource ID of the raw file containing the exercise description.
     * @return A string containing the entire contents of the file.
     */
    private String loadExerciseDescriptionFromRawResource(int resourceId) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = getResources().openRawResource(resourceId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        // Set up the Toolbar as the app's ActionBar and enable the back button.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Configure the RecyclerView for displaying exercises.
        exerciseRecyclerView = findViewById(R.id.exerciseRecyclerView);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Create and set an adapter with sample data.
        List<Exercise> exerciseList = generateSampleData();
        exerciseAdapter = new ExerciseAdapter(exerciseList, this);
        exerciseRecyclerView.setAdapter(exerciseAdapter);
    }
    /**
     * Generates sample data for exercises.
     * @return A list of Exercise objects.
     */
    private List<Exercise> generateSampleData() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Push-up", loadExerciseDescriptionFromRawResource(R.raw.push_up_description)));
        exerciseList.add(new Exercise("Squats", loadExerciseDescriptionFromRawResource(R.raw.squats_description)));
        exerciseList.add(new Exercise("Lunges", loadExerciseDescriptionFromRawResource(R.raw.lunges_description)));
        exerciseList.add(new Exercise("Plank", loadExerciseDescriptionFromRawResource(R.raw.plank_description)));
        exerciseList.add(new Exercise("Burpees", loadExerciseDescriptionFromRawResource(R.raw.burpees_description)));
        exerciseList.add(new Exercise("Mountain Climbers", loadExerciseDescriptionFromRawResource(R.raw.mountain_climbers_description)));
        return exerciseList;
    }
    @Override
    public void onItemClick(Exercise exercise) {
        // Handle item click by starting a detail activity and passing the selected exercise's data.
        Intent intent = new Intent(this, ExerciseDetailsActivity.class);
        intent.putExtra("exercise_name", exercise.getName());
        intent.putExtra("exercise_description", exercise.getDescription());
        startActivity(intent);
    }
    // Handle the action bar's back button to navigate up in the activity stack.
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    // Method to finish the current activity when "Go to exercise" button is clicked.
    public void onClickExerciseButton(View view) {
        finish();
    }
    // Start the DietPlanActivity when "Diet plan" button is clicked.
    public void onClickDietPlanButton(View view) {
        Intent intent = new Intent(this, DietPlanActivity.class);
        startActivity(intent);
    }
}
