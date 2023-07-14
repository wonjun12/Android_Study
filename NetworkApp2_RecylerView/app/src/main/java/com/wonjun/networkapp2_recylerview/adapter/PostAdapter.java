package com.wonjun.networkapp2_recylerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonjun.networkapp2_recylerview.R;
import com.wonjun.networkapp2_recylerview.model.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    Context context;
    ArrayList<Post> postArrayList;

    public PostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postArrayList.get(position);

        holder.txtTitle.setText(post.getTitle());
        holder.txtBody.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtBody = itemView.findViewById(R.id.txtBody);
        }
    }
}
