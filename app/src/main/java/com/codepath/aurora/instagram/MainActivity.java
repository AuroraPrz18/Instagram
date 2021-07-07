package com.codepath.aurora.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}