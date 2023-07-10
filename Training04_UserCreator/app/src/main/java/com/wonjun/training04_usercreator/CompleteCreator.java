package com.wonjun.training04_usercreator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CompleteCreator extends AppCompatActivity {
    TextView txtComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_creator);

        txtComplete = findViewById(R.id.txtComplete);

        String email = getIntent().getStringExtra("email");

        txtComplete.setText(email + " 님\n 회원가입을 축하합니다.");

    }
}