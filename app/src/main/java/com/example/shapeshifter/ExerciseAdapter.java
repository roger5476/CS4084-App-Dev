
package com.example.shapeshifter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exerciseList; // List to hold exercise data
    private OnItemClickListener listener; // Listener interface for item click events
    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }
    // Constructor to initialize the adapter with a list of exercises and a click listener
    public ExerciseAdapter(List<Exercise> exerciseList, OnItemClickListener listener) {
        this.exerciseList = exerciseList;
        this.listener = listener;
    }
    // ViewHolder class to hold the layout for each exercise item
    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseNameTextView; // TextView to display exercise name
        // Constructor to initialize the ViewHolder with the item view
        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exerciseNameTextView);
        }
        // Method to bind exercise data to the ViewHolder
        void bind(final Exercise exercise, final OnItemClickListener listener) {
            exerciseNameTextView.setText(exercise.getName()); // Set exercise name
            // Set click listener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(exercise); // Notify listener of item click event
                }
            });
        }
    }
    // Method to create ViewHolders
    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the exercise item layout and create a ViewHolder for it
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }
    // Method to bind exercise data to ViewHolders
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position); // Get exercise at the current position
        holder.bind(exercise, listener); // Bind exercise data to the ViewHolder
    }
    // Method to get the total number of exercises in the list
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
