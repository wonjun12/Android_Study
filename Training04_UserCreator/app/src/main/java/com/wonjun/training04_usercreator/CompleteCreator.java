package com.wonjun.training04_usercreator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class CompleteCreator extends AppCompatActivity {
    TextView txtComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_creator);

        txtComplete = findViewById(R.id.txtComplete);

        //String email = getIntent().getStringExtra("email");
        // SharedPerference로 저장된 값에 대해 바로 가져올수있다.
        SharedPreferences sp = getSharedPreferences("Register App", MODE_PRIVATE); //이전에 사용했던 저장소와 동일하게 작성해야함.
        String email = sp.getString("email", ""); //있을수도 있고 없을수도 있기때문에, default value도 넣어야한다.


        txtComplete.setText(email + " 님\n 회원가입을 축하합니다.");

    }
}