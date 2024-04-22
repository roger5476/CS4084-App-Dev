package com.example.superfit;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Activity to display the details of a diet plan for a selected day.
 */
public class DietPlanDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the activity
        setContentView(R.layout.activity_diet_plan_detail2);
        // Set up the toolbar and enable the Up (back) button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Retrieve the day selected by the user from the previous activity
        Bundle extras = getIntent().getExtras();
        String selectedDay = extras.getString("selected_day");
        // Determine the resource ID for the raw file corresponding to the selected day
        int resourceId = getResources().getIdentifier(selectedDay.toLowerCase() + "_diet", "raw", getPackageName());
        String dietDescription = loadDietPlanFromRawResource(resourceId);
        // Locate the TextViews in the layout and set their text to the selected day and its description
        TextView dietNameTextView = findViewById(R.id.dietNameTextView);
        TextView dietDescriptionTextView = findViewById(R.id.dietDescriptionTextView);
        dietNameTextView.setText(selectedDay);
        dietDescriptionTextView.setText(dietDescription);
    }
    /**
     * Load the content of a raw resource file into a String.
     * @param resourceId the resource identifier of the raw resource file.
     * @return the content of the file as a String.
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            // Respond to the action bar's Up/Home button
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
