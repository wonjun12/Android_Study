package com.wonjun.training10_imageglide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wonjun.training10_imageglide.model.Photo;

public class ImageActivity extends AppCompatActivity {

    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imgProfile = findViewById(R.id.imgProfile);

        Photo photo = (Photo) getIntent().getSerializableExtra("photo");

        // TODO: 2023-07-18 이미지 로드에 성공했을때, 사진의 타입을 변경하고 싶다. Listener
        Glide.with(ImageActivity.this)
                .load(photo.getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imgProfile.setScaleType(ImageView.ScaleType.CENTER);
                        return false;
                    }
                })
                .into(imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}