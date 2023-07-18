package com.wonjun.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView img1;
    ImageView img2;
    ImageView img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        // 기본적으로 이미지를 불러오는 함수
        Glide.with(MainActivity.this)
                .load("https://block-yh-test2.s3.ap-northeast-2.amazonaws.com/2023-01-12T15_46_28.062874.jpg")
                .into(img1);
        Glide.with(MainActivity.this)
                .load("https://block-yh-test2.s3.ap-northeast-2.amazonaws.com/2023-01-13T03_31_12.564141.jpeg")
                .placeholder(R.drawable.baseline_person_24)
                .into(img2);
        Glide.with(MainActivity.this)
                .load("https://block-yh-test2.s3.ap-northeast-2.amazonaws.com/2023-01-13T03_46_46.079772.jpg")
                //.error()
                .into(img3);
    }
}