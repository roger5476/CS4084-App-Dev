package com.example.shapeshifter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private List<String> postsList;

    public PostsAdapter(List<String> postsList) {
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        String postContent = postsList.get(position);

        // Split the formatted post to separate content, username, and timestamp
        String[] parts = postContent.split(" \\(");
        String content = parts[0];
        String info = "(" + parts[1]; // This will include both username and timestamp

        // Set the content and info in the respective text views
        holder.tvContent.setText(content);
        holder.tvInfo.setText(info);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvInfo;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvPostContent);
            tvInfo = itemView.findViewById(R.id.tvPostInfo);
        }
    }
}