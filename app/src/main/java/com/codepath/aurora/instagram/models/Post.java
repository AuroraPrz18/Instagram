package com.codepath.aurora.instagram.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/***
 * Class to bind queries with Parse
 */
@ParseClassName("Post") // This should match to the name of the entity in the DB
public class Post extends ParseObject {

    // Define the keys that exactly match with each column name that is in the entity
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LIKE = "likesCount";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";

    public String getDescription(){
        // Call the getString() method already defined in the ParseObject class
        // Return the description
        return getString( KEY_DESCRIPTION );
    }

    public void setDescription(String description){
        // Associate a key description with its description
        put( KEY_DESCRIPTION, description );
    }

    public int getLike(){
        // Call the getString() method already defined in the ParseObject class
        // Return the description
        return (int)getNumber( KEY_DESCRIPTION );
    }

    public void setLike(int likes){
        // Associate a key description with its description
        put( KEY_LIKE, (Number) likes );
    }

    public ParseFile getImage(){
        // Return the image in a ParseFile format
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put( KEY_IMAGE, parseFile );
    }

    public ParseUser getUser(){
        // Return the user in a ParseUser format
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put( KEY_USER, user );
    }

    /**
     * Method that converts a Data object into a String, calculating the time ago.
     * @return Time ago
     */
    public static String getTimestamp(Date createdAt){
        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m ago";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h ago";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d ago";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }
        return "";
    }
}
