package com.wonjun.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wonjun.memoapp.config.Config;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인 한 유저인지 아닌지 체크를 해야한다.
        // 로그인을 안했으면, 회원가입 or 로그인 액티비티를 실행한다.
            // 확인하기 위해서는 따로 저장이 되어있어야한다. 파일에 저장하는 방법을 사용하자.
        //SharedPreferences sp = getSharedPreferences("MemoApp", MODE_PRIVATE); 타이밍해서 오타날수도 있으니 Config에 넣자.
        SharedPreferences sp = getSharedPreferences(Config.PREFFERENCE_NAME, MODE_PRIVATE);
            // 저장되어 있는 것이 있는지 확인
        //String accessToken = sp.getString("accessToken", "");
        String accessToken = sp.getString(Config.ACCESS_TOKEN, "");
        if(accessToken.isEmpty()){ //비어 있냐? 여부 확인
            //비어있다면 로그인을 한번도 한적 없다는 뜻이다.
                // 회원가입으로 보내자.
            Intent intent = new Intent(MainActivity.this, UserRegisterActivity.class);
            startActivity(intent);

            finish(); // 더이상 진행되면 안되니 종료하자.
            return; //리턴을 하지 않으면 아래의 코드를 실행함
                    // 왜? onCreate는 아직 끝나지 않았기 때문에
        }
        


        // 했으면, 아래의 코드를 실행


        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAdd);
    }
}