package com.example.shapeshifter;
/**
 * Represents a diet plan for a specific day.
 */
public class DietPlan {
    // Variables to store the day of the week and the diet description
    private String day;
    private String description;
    /**
     * Constructor to initialize the DietPlan object with day and description.
     *
     * @param day the day of the week for the diet plan
     * @param description the detailed diet plan for the day
     */
    public DietPlan(String day, String description) {
        this.day = day;
        this.description = description;
    }
    /**
     * Gets the day of the week for this diet plan.
     *
     * @return the day of the week
     */
    public String getDay() {
        return day;
    }
    /**
     * Sets the day of the week for this diet plan.
     *
     * @param day the day of the week to set
     */
    public void setDay(String day) {
        this.day = day;
    }
    /**
     * Gets the description of the diet plan.
     *
     * @return the description of the diet plan
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description of the diet plan.
     *
     * @param description the new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
