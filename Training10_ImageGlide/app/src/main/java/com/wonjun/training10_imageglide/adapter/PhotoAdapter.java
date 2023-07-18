package com.wonjun.training10_imageglide.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wonjun.training10_imageglide.ImageActivity;
import com.wonjun.training10_imageglide.R;
import com.wonjun.training10_imageglide.model.Photo;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

    Context context;
    ArrayList<Photo> photoArrayList;

    public PhotoAdapter(Context context, ArrayList<Photo> photoArrayList) {
        this.context = context;
        this.photoArrayList = photoArrayList;
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row, parent, false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photoArrayList.get(position);

        holder.txtTitle.setText(photo.getTitle());
        holder.txtId.setText("id : " + photo.getId());
        holder.txtAlbumId.setText("albumId : " + photo.getAlbumId());

        // TODO: 2023-07-18 Glide 캐시 적용

        Glide.with(context).load(photo.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(holder.imgProfile);

    }

    @Override
    public int getItemCount() {
        return photoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtAlbumId;
        TextView txtId;
        ImageView imgProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtId = itemView.findViewById(R.id.txtId);
            txtAlbumId = itemView.findViewById(R.id.txtAlbumId);
            imgProfile = itemView.findViewById(R.id.imgProfile);

            imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();

                    Photo photo = photoArrayList.get(index);

                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("photo", photo);

                    context.startActivity(intent);
                }
            });
        }
    }
}
