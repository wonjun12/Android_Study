package com.wonjun.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txtContent;

    TextView txtFuture;

    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.i("LIFE Second" , "두번째 onCreate 함수 실행");

        txtContent = findViewById(R.id.txtContent);
        txtFuture = findViewById(R.id.txtFuture);

        // 받아오는 데이터가 있으면, 데이터를 받아온다.
        String name = getIntent().getStringExtra("name");
        age = getIntent().getIntExtra("age", 0);

        txtContent.setText("이름 : "  + name +" | 나이 : "+ age);

        age = age + 10;
        txtFuture.setText("10년후 나이는 : " + age);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LIFE Second" , "두번째 onStart 함수 실행");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LIFE Second" , "두번째 onResume 함수 실행");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LIFE Second" , "두번째 onPause 함수 실행");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LIFE Second" , "두번째 onStop 함수 실행");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LIFE Second" , "두번째 onDestroy 함수 실행");
    }

    
    // 백버튼 누를시 실행되는 함수
    @Override
    public void onBackPressed() {
        // 해당 함수를 실행시키는 순간, 함수는 끝난다는 뜻이다.
            // 왜냐면, stop - destroy 까지 실행되는 함수이다 보니 모든게 끝난다는 뜻이다.
            // 백버튼 기능을 눌렀을대 기능을 찾는 것뿐...
        // super.onBackPressed();

        Log.i("LIFE Second" , "두번째 onBackPressed 함수 실행");

        // 나이를 10 더한 후 , 이 액티비티를 실행한 액티비티에게 데이터를 전달하자.
        int data = age;

        // 데이터를 보내기위해 intent가 필요하다.
        Intent intent = new Intent(); // 새로 다른 액티비티를 생성하는 것이 아니기때문에 적을 필요 없다.
        intent.putExtra("data", data);

        setResult(22, intent);
        // resultCode : 기준 상태 코드 (구분을 위한)
        // data : 보낼 데이터

        super.onBackPressed();
    }
}