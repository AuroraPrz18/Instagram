package com.codepath.aurora.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.codepath.aurora.instagram.databinding.ActivityPostPhotoBinding;
import com.codepath.aurora.instagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class PostPhotoActivity extends AppCompatActivity {
    ActivityPostPhotoBinding binding;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 73;
    public static final String TAG = "PostPhotoActivity";
    private File photoFile;
    public String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the ActivityLoginBinding object
        binding = ActivityPostPhotoBinding.inflate(getLayoutInflater());
        // Set the layout to this class
        setContentView(binding.getRoot());
    }



    public void onClickCamera(View view) {
        launchCamera();
    }

    /***
     * Method that launch the camera, if there is an app that can handle it. Store the photo and return the control to the calling app
     */
    private void launchCamera() {
        // Intent to take a picture, then return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // Wrap File object into a content provider, which is required for API >= 24
        Uri fileProvider = FileProvider.getUriForFile(PostPhotoActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // if there is an app that can handle it
        if(intent.resolveActivity(getPackageManager()) != null){
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    /***
     * Method that returns the File for a photo stored on disk given the fileName
     */
    private File getPhotoFileUri(String photoFileName) {
        // Get safe storage directory for photos. Using 'getExternalFilesDir' on Context to access package-specific directories to don't request external read/write runtime permissions
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.e(TAG, "Filed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + photoFileName);
    }


    // This method will be invoked when child application return to the parent application
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // By this point the camera photo id on the disk
                // Decode the file of the photo that we just took into a bitmap
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // TODO: RESIZE BITMAP
                // Load the taken image into a preview
                binding.imPhoto.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /***
     * Method called when the Submit button is clicked. It check if the description is valid and save the post on the DB
     */
    public void onClickSubmit(View view) {
        String description = binding.etDescription.getText().toString();
        if(description.isEmpty()){
            Toast.makeText(PostPhotoActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(photoFile == null || binding.imPhoto.getDrawable() == null){ // Post has to have an image
            Toast.makeText(PostPhotoActivity.this, "There is no image!", Toast.LENGTH_SHORT).show();
            return;
        }
        ParseUser currentUser = ParseUser.getCurrentUser();
        savePost(description, currentUser, photoFile);
    }

    /***
     * Method that saves the new post in the DB
     */
    private void savePost(String description, ParseUser currentUser, File photoFile) {
        // Define the Post object with its attributes needed
        Post post = new Post();
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        // Try to save it in a background thread
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Toast.makeText(PostPhotoActivity.this, "Something went wrong with getting posts", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("POSTS", "Post save was successful");
                finish();
            }
        });
    }
}