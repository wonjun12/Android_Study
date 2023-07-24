package com.wonjun.training13_postingapi.post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wonjun.training13_postingapi.R;

public class PostCreateActivity extends AppCompatActivity {

    ImageView imgAdd;
    EditText editContent;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        imgAdd = findViewById(R.id.imgAdd);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}