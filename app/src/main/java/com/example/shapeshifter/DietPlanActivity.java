package com.example.superfit;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Activity to display the list of diet plans for each day of the week.
 */
public class DietPlanActivity extends AppCompatActivity implements DietPlanAdapter.OnItemClickListener {
    private RecyclerView dietPlanRecyclerView; // RecyclerView to display diet plans
    private DietPlanAdapter dietPlanAdapter; // Adapter for the RecyclerView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan);
        // Set up the Toolbar as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Enable the back button in the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set up RecyclerView with LinearLayoutManager and Adapter
        dietPlanRecyclerView = findViewById(R.id.dietPlanRecyclerView);
        dietPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Generate sample data for the RecyclerView and set the adapter
        List<DietPlan> dietPlanList = generateSampleData();
        dietPlanAdapter = new DietPlanAdapter(dietPlanList, this);
        dietPlanRecyclerView.setAdapter(dietPlanAdapter);
    }
    /**
     * Generate sample diet plans for each day of the week.
     * @return List of DietPlan objects.
     */
    private List<DietPlan> generateSampleData() {
        List<DietPlan> dietPlanList = new ArrayList<>();
        // Add sample diet plans for each day
        dietPlanList.add(new DietPlan("Monday", loadDietPlanFromRawResource(R.raw.monday_diet)));
        dietPlanList.add(new DietPlan("Tuesday", loadDietPlanFromRawResource(R.raw.tuesday_diet)));
        dietPlanList.add(new DietPlan("Wednesday", loadDietPlanFromRawResource(R.raw.wednesday_diet)));
        dietPlanList.add(new DietPlan("Thursday", loadDietPlanFromRawResource(R.raw.thursday_diet)));
        dietPlanList.add(new DietPlan("Friday", loadDietPlanFromRawResource(R.raw.friday_diet)));
        dietPlanList.add(new DietPlan("Saturday", loadDietPlanFromRawResource(R.raw.saturday_diet)));
        dietPlanList.add(new DietPlan("Sunday", loadDietPlanFromRawResource(R.raw.sunday_diet)));
        return dietPlanList;
    }
    /**
     * Load the diet plan details from a raw resource file.
     * @param resourceId The resource ID of the raw file.
     * @return The content of the file as a string.
     */
    private String loadDietPlanFromRawResource(int resourceId) {
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
    public void onItemClick(DietPlan dietPlan) {
        // Start DietPlanDetailActivity when an item is clicked, passing the selected day
        Intent intent = new Intent(this, DietPlanDetailActivity.class);
        intent.putExtra("selected_day", dietPlan.getDay());
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        // Handle the action of the back button in the ActionBar
        onBackPressed();
        return true;
    }
    public void onClickExerciseButton(View view) {
        // Start ExerciseActivity when the "Go to exercise" button is clicked
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);
    }
}
