package com.example.shapeshifter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shapeshifter.PostsAdapter;
import com.example.shapeshifter.data.UserDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.shapeshifter.data.UserContract;

public class PostsActivity extends AppCompatActivity {

    private EditText etPostContent;
    private Button btnPost;
    private Button btnBack;
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private PostsDataSource dataSource;
    private Button btnClearPosts;

    private List<String> postsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        etPostContent = findViewById(R.id.etPostContent);
        btnPost = findViewById(R.id.btnPost);
        btnBack = findViewById(R.id.btnBack);
        btnClearPosts = findViewById(R.id.btnClearPosts);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataSource = new PostsDataSource(this);
        dataSource.open();

        postsList = dataSource.getAllPosts();
        postsAdapter = new PostsAdapter(postsList);

        recyclerView.setAdapter(postsAdapter);

        // Set up character counter
        final TextView tvCharacterCount = findViewById(R.id.tvCharacterCount);

        etPostContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update character count
                int currentLength = s.length();
                tvCharacterCount.setText(currentLength + "/280");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });

        btnClearPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllPosts();
                refreshPostsList();
                Toast.makeText(PostsActivity.this, "All posts cleared!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostsActivity.this, Home.class);
                startActivity(intent);

                // Finish the current activity
                finish();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etPostContent.getText().toString();
                String username = getLoggedInUsername(); // Fetch the current username
                String timestamp = getCurrentTimestamp(); // Fetch the current timestamp

                long id = dataSource.addPost(content, username, timestamp);
                if (id != -1) {
                    // Post added successfully
                    Toast.makeText(PostsActivity.this, "Post added successfully!", Toast.LENGTH_SHORT).show();
                    etPostContent.setText(""); // Clear the EditText

                    // Refresh the RecyclerView
                    refreshPostsList();

                    // Log the number of posts after refreshing
                    Log.d("PostsActivity", "Number of posts: " + postsAdapter.getItemCount());
                } else {
                    // Failed to add post
                    Toast.makeText(PostsActivity.this, "Failed to add post.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshPostsList();
    }

    private void clearAllPosts() {
        dataSource.clearAllPosts();
    }

    private void refreshPostsList() {
        postsList.clear();
        postsList.addAll(dataSource.getAllPosts());
        postsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }

    private String getCurrentUsername() {
        // Fetch the current username from your User table or wherever it's stored
        // Assuming you have a method to get the current username from the database
        // You would replace 'getLoggedInUsername()' with the actual method you use to fetch the username
        return getLoggedInUsername();
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    // Add this method to your PostsDataSource class to fetch the logged-in username
    @SuppressLint("Range")
    private String getLoggedInUsername() {
        UserDbHelper dbHelper = new UserDbHelper(this); // Assuming UserDbHelper is the correct helper for the user table
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String username = "";

        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                new String[]{UserContract.UserEntry.COLUMN_NAME_USERNAME}, // Corrected column name
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_USERNAME)); // Corrected column name
            cursor.close();
        }

        return username;
    }
}