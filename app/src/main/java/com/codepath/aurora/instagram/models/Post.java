package com.codepath.aurora.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/***
 * Class to bind queries with Parse
 */
@ParseClassName("Post") // This should match to the name of the entity in the DB
public class Post extends ParseObject {

    // Define the keys that exactly match with each column name that is in the entity
    public static final String KEY_DESCRIPTION = "description";
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
}
