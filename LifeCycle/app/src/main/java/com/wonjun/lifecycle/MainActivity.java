package com.wonjun.lifecycle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;

    EditText editName;
    EditText editAge;

// 양방향 데이터 교환 액티비티 실행 => ActivityResultLauncher
ActivityResultLauncher<Intent> launcher = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //데이터를 받아오는 코드는 여기에 작성.
            Log.i("LIFE MAIN", "여기 리절트 나옴?" + result.getResultCode());
            if ( result.getResultCode() == 22){

                 int futureAge = result.getData().getIntExtra("data", 0);

                 txtResult.setText("미래의 나이는 " + futureAge);
            }
        }
    });
    TextView txtResult;

    //필수
    // 설정과 관련된 모든것을 여기서 한다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //메인 화면과 연결하는 함수
        Log.i("LIFE MAIN" , "onCreate 함수 실행");

        button = findViewById(R.id.button);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);

        txtResult = findViewById(R.id.txtResult);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 두번째 액티비티에 값을 넘기면서 실행시키고 싶다.
                String name = editName.getText().toString().trim();
                String strAge = editAge.getText().toString().trim();
                int age = Integer.parseInt(strAge);


// 두번째 액티비티를 실행시키고 싶다.
    //해당 엑티비티가 다른 액티비티를 실행시킨다의미로 파라미터를 설정해줘야한다.
Intent intent = new Intent(MainActivity.this, SecondActivity.class); //Intent 객체 생성
                                                            // 객체로 생성해 메모리에 올리라는 뜻
                                                            // 메모리에 생성한 적이 없기때문에 this를 사용할 수 가 없다.
// 데이터를 전달하기 위해 Intent에 담아주자.
intent.putExtra("name", name); //key : value로 저장됨
intent.putExtra("age", age);


// 단방향 데이터 전달 액티비티 실행
//startActivity(intent);

// 양방향 데이터 교환 액티비티 실행 => ActivityResultLauncher

launcher.launch(intent); // 실행
            }
        });
    }

    //잘 쓰이지는 않는다.
    @Override
    protected void onStart() {
        super.onStart();

        Log.i("LIFE MAIN" , "onStart 함수 실행");
    }

    //가장 많이 사용되는 함수
    // 다른 화면으로 넘어갈때 해당 함수가 많이 사용된다.
    @Override
    protected void onResume() {
        super.onResume();

        Log.i("LIFE MAIN" , "onResume 함수 실행");
    }

    //일시 정지 함수
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LIFE MAIN" , "onPause 함수 실행");
    }

    //정지 함수
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LIFE MAIN" , "onStop 함수 실행");
    }

    //메모리 삭제
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LIFE MAIN" , "onDestroy 함수 실행");
    }
}