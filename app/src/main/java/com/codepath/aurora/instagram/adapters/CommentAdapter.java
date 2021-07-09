package com.codepath.aurora.instagram.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.aurora.instagram.R;
import com.codepath.aurora.instagram.databinding.ItemCommentBinding;
import com.codepath.aurora.instagram.models.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    List<Comment> comments;
    Context context;

    /**
     * Constructor for the CommentAdapter, which extends from RecyclerView.Adapter
     * @param context
     * @param comments
     * @return
     */
    public CommentAdapter(Context context, List<Comment> comments) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemCommentBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCommentBinding.bind(itemView);
        }

        /***
         * Bind the information of a given Comment to a view that will appear in the RecyclerView
         * @param comment
         */
        public void bind(Comment comment){
            // Set the user name and description to the item post
            String description = "<b>" + comment.getUser().getUsername() + "</b> " +comment.getDescription();
            binding.tvComment.setText(Html.fromHtml(description));
            binding.tvTimestamp.setText(Comment.getTimestamp(comment.getCreatedAt()));
        }

    }
}
