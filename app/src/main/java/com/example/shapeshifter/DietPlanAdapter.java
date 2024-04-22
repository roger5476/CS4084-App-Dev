package com.example.superfit;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
/**
 * Adapter class for handling a RecyclerView that displays a list of DietPlan objects.
 */
public class DietPlanAdapter extends RecyclerView.Adapter<DietPlanAdapter.ViewHolder> {
    private List<DietPlan> dietPlanList; // List of diet plans to be displayed
    private OnItemClickListener listener; // Listener for click events on diet plan items
    /**
     * Constructor for the DietPlanAdapter.
     * @param dietPlanList A list containing the diet plans.
     * @param listener A listener to handle item click events.
     */
    public DietPlanAdapter(List<DietPlan> dietPlanList, OnItemClickListener listener) {
        this.dietPlanList = dietPlanList;
        this.listener = listener;
    }
    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diet_plan, parent, false);
        return new ViewHolder(view);
    }
    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietPlan dietPlan = dietPlanList.get(position);
        holder.bind(dietPlan, listener);
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return dietPlanList.size();
    }
    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dayTextView; // TextView to display the day of the diet plan
        /**
         * Constructor for the ViewHolder, referencing the TextViews within the item layout.
         * @param itemView The View that you inflated in onCreateViewHolder.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
        }
        /**
         * Bind the DietPlan data to the ViewHolder.
         * @param dietPlan The DietPlan data to be displayed.
         * @param listener The click listener that handles interactions.
         */
        public void bind(final DietPlan dietPlan, final OnItemClickListener listener) {
            dayTextView.setText(dietPlan.getDay()); // Set the day from the diet plan
            // Set a click listener on the entire row, and pass the clicked diet plan to the interface
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dietPlan);
                }
            });
        }
    }
    /**
     * Interface for receiving click events from cells in the RecyclerView.
     */
    public interface OnItemClickListener {
        void onItemClick(DietPlan dietPlan);
    }
}
