package com.codepath.aurora.instagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.aurora.instagram.PostDetails;
import com.codepath.aurora.instagram.R;
import com.codepath.aurora.instagram.databinding.ItemPostBinding;
import com.codepath.aurora.instagram.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    Context context;
    List<Post> posts;

    /**
     * Constructor for the PostAdapter, which extends from RecyclerView.Adapter
     * @param context
     * @param posts
     * @return
     */
    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view for each item
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        // Delegate binding to ViewHolder
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    /**
     * Method to clean all the elements of the recycler view.
     */
    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    /**
     * Add a new list of items
     * @param list
     */
    public void addAll(List<Post> list){
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ItemPostBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemPostBinding.bind(itemView);
        }

        /***
         * Bind the information of a given Post to a view that will appear in the RecyclerView
         * @param post
         */
        public void bind(Post post) {
            // Set the user name and description to the item post
            binding.tvUserName.setText(post.getUser().getUsername());
            String description = "<b>" + post.getUser().getUsername() + "</b> " +post.getDescription();
            binding.tvDescription.setText(Html.fromHtml(description));
            binding.tvTimestamp.setText(Post.getTimestamp(post.getCreatedAt()));
            // Set the image of the Post if it is not null
            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context).load(image.getUrl()).into(binding.ivPost);
            }else{
                Glide.with(context).load("");
            }
            // Set onClickListener to the item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostDetails.class);
                    intent.putExtra("Post", Parcels.wrap(post));
                    context.startActivity(intent);
                }
            });
        }
    }
}
