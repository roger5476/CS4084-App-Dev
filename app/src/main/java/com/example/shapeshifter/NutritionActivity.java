package com.example.shapeshifter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import com.example.shapeshifter.R;

public class NutritionActivity extends AppCompatActivity {

    private EditText editTextBreakfast;
    private EditText editTextLunch;
    private EditText editTextDinner;
    private EditText editTextSnacks;
    private EditText editTextCaloriesBurned;
    private Button buttonCalculate;
    private Button buttonReset;
    private Button buttonBack;
    private TextView textViewTotalCalories;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        // Initialize EditText, Button, and TextView views
        editTextBreakfast = findViewById(R.id.editTextBreakfast);
        editTextLunch = findViewById(R.id.editTextLunch);
        editTextDinner = findViewById(R.id.editTextDinner);
        editTextSnacks = findViewById(R.id.editTextSnacks);
        editTextCaloriesBurned = findViewById(R.id.editTextCaloriesBurned);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);
        buttonBack = findViewById(R.id.buttonBack);
        textViewTotalCalories = findViewById(R.id.textViewTotalCalories);

        // Set click listener for Calculate button
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalCalories();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCalorieInputs();
            }
        });

        // Back Button Click Listener
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackToHome();
            }
        });
    }

    private void calculateTotalCalories() {
        // Get calorie inputs as strings from EditText fields
        String breakfastCaloriesStr = editTextBreakfast.getText().toString().trim();
        String lunchCaloriesStr = editTextLunch.getText().toString().trim();
        String dinnerCaloriesStr = editTextDinner.getText().toString().trim();
        String snacksCaloriesStr = editTextSnacks.getText().toString().trim();
        String caloriesBurnedStr = editTextCaloriesBurned.getText().toString().trim();

        // Convert string inputs to integers
        int breakfastCalories = parseCalories(breakfastCaloriesStr);
        int lunchCalories = parseCalories(lunchCaloriesStr);
        int dinnerCalories = parseCalories(dinnerCaloriesStr);
        int snacksCalories = parseCalories(snacksCaloriesStr);
        int caloriesBurned = parseCalories(caloriesBurnedStr);

        // Calculate total calories consumed
        int totalCaloriesConsumed = breakfastCalories + lunchCalories + dinnerCalories + snacksCalories;

        // Calculate net calories (total calories consumed - calories burned)
        int netCalories = totalCaloriesConsumed - caloriesBurned;

        // Display net calories on the screen
        textViewTotalCalories.setText("Net Calories: " + netCalories);
    }

    private void resetCalorieInputs() {
        // Clear all EditText fields
        editTextBreakfast.setText("");
        editTextLunch.setText("");
        editTextDinner.setText("");
        editTextSnacks.setText("");
        editTextCaloriesBurned.setText("");
        textViewTotalCalories.setText(""); // Clear total calories display
    }

    private void navigateBackToHome() {
        // Navigate back to Home activity
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish(); // Close NutritionActivity to prevent going back to it using back button
    }


    private int parseCalories(String caloriesStr) {
        try {
            return Integer.parseInt(caloriesStr);
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., empty or non-numeric)
            return 0; // Return 0 calories if input is invalid
        }
    }
}
