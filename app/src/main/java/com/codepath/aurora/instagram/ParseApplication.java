package com.codepath.aurora.instagram;

import android.app.Application;

import com.codepath.aurora.instagram.models.Comment;
import com.codepath.aurora.instagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 *  Class to initialize the Parse SDK with the Parse application ID, client key and server.
 */
public class ParseApplication extends Application { // Add the android:name is needed

    @Override
    public void onCreate() {
        super.onCreate();

        // Register Post parse model - This is needed to query or set data on the Post model
        ParseObject.registerSubclass(Post.class);
        // Register Comment parse model
        ParseObject.registerSubclass(Comment.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.application_id))
                .clientKey(getString(R.string.client_key))
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
