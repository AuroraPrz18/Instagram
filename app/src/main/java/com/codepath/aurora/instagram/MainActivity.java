package com.codepath.aurora.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.aurora.instagram.adapters.PostsAdapter;
import com.codepath.aurora.instagram.databinding.ActivityMainBinding;
import com.codepath.aurora.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the list
        allPosts = new ArrayList<>();
        // Initialize the adapter
        adapter = new PostsAdapter(this, allPosts);

        // Set up the RecyclerView
        binding.rvPosts.setAdapter(adapter);
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve post from Backend
        queryPosts();

        // Set up the SwipeRefreshLayout
        setUpSwipeRefreshLayout();
    }

    private void setUpSwipeRefreshLayout() {
        // When the layout is refreshed
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Clear old posts
                adapter.clear();
                // Retrieve post from the server
                queryPosts();
            }
        });
        // Configure the refreshing colors
        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    /***
     * Method called when the LogOut item inside de menu is clicked. It signed back out the user.
     * @param item
     */
    public void onClickLogOut(MenuItem item) {
        ParseUser.logOut(); // Log out the currently logged in user session
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /***
     * Method called when the NewPost button inside de menu is clicked. It open the PostPhoto Activity.
     * @param item
     */
    public void onClickNewPost(MenuItem item) {
        Intent intent = new Intent(this, PostPhotoActivity.class);
        startActivity(intent);
    }

    /***
     * Method to get Instagram posts back from the database
     */
    private void queryPosts() {
        // Specify that we are going to query the User class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // To include the author of the post information (access to information of other entity related with Post entity)
        query.include(Post.KEY_USER);
        // Set limit to the last 20 posts
        query.setLimit(20);
        // Set order them by creation date (desc)
        query.addDescendingOrder("createdAt");
        // Retrieve all the items from the back end
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Toast.makeText(MainActivity.this, "Something went wrong with getting posts", Toast.LENGTH_SHORT).show();
                    return;
                }
                // for debugging purposes let's print every post description to logcat
                for (Post post : posts) {
                    Log.i("POSTS", "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                // Save received posts
                allPosts.addAll(posts); // adapter.addAll();
                // Notify adapter of data change
                adapter.notifyDataSetChanged();
                // Now we call setRefreshing(false) to signal refresh has finished
                binding.swipeContainer.setRefreshing(false);
            }
        });
    }
}