package com.wonjun.training11_youtubeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wonjun.training11_youtubeapp.ImageActivity;
import com.wonjun.training11_youtubeapp.R;
import com.wonjun.training11_youtubeapp.model.Youtube;

import java.util.ArrayList;

public class YoutubeAdapter extends  RecyclerView.Adapter<YoutubeAdapter.ViewHolder>{

    Context context;
    ArrayList<Youtube> youtubeArrayList;

    public YoutubeAdapter(Context context, ArrayList<Youtube> youtubeArrayList) {
        this.context = context;
        this.youtubeArrayList = youtubeArrayList;
    }

    @NonNull
    @Override
    public YoutubeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_row, parent, false);
        return new YoutubeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Youtube youtube = youtubeArrayList.get(position);

        holder.txtTitle.setText(youtube.getTitle());
        holder.txtDescription.setText(youtube.getDescription());

        Glide.with(context)
                .load(youtube.getThumbnailUrl())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.imgThumbnail);
    }

    @Override
    public int getItemCount() {
        return youtubeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;

        TextView txtTitle;
        TextView txtDescription;
        ImageView imgThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle= itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            cardView = itemView.findViewById(R.id.cardView);

            imgThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();

                    Youtube youtube = youtubeArrayList.get(index);

                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("youtube", youtube);

                    context.startActivity(intent);
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();

                    Youtube youtube = youtubeArrayList.get(index);
                    String url = "https://www.youtube.com/watch?v=" + youtube.getVideoId();
                    openWebPage(url);
                }
            });

        }

        private void openWebPage(String url){
            Uri uri = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //intent.setPackage("com.google.android.youtube");
            context.startActivity(intent);
        }
    }
}
