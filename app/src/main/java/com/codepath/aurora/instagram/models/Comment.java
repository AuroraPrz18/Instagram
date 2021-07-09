package com.codepath.aurora.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/***
 * Class to bind queries with Parse
 */
@ParseClassName("Comment") // This should match to the name of the entity in the DB
public class Comment extends ParseObject {
    // Define the keys that exactly match with each column name that is in the entity
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "user";
    public static final String KEY_POST = "post";

    public String getDescription(){
        // Call the getString() method already defined in the ParseObject class
        // Return the description
        return getString( KEY_DESCRIPTION );
    }

    public void setDescription(String description){
        // Associate a key description with its description
        put( KEY_DESCRIPTION, description );
    }

    public ParseUser getUser(){
        // Return the user in a ParseUser format
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put( KEY_USER, user );
    }

    public void setPost(Post post){
        // NOTE: getPost is not needed because we only use it to get the reference to a POST
        put( KEY_POST, post);
    }

    /**
     * Method that converts a Data object into a String, calculating the time ago.
     * @return Time ago
     */
    public static String getTimestamp(Date createdAt){
        return Post.getTimestamp(createdAt);
    }

}
