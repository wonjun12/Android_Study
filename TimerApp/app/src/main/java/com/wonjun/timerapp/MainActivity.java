package com.wonjun.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtTimer;
    TextView txtCount;
    Button button;
    int count;
    boolean isTimer = true;

    CountDownTimer timer;

        // 처음 앱을 누르는 순간 -> onCreate함수를 실행하고 화면이 나오는 순간 이미 함수는 끝난것이다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer = findViewById(R.id.txtTimer);
        txtCount = findViewById(R.id.txtCount);
        button = findViewById(R.id.button);

        //타이머를 만든다.
        timer = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long l) {
                //남은  시간을 화면에 표시한다.
                // 위 인터벌 마라미터의 간격마다 실행 되는 함수
                long remain = l / 1000;
                txtTimer.setText(remain + "초");
            }

            @Override
            public void onFinish() {
                //타이머가 종료될때 실행되는 함수
                // 알람? 처럼
                isTimer = false;
                
                //알러드 다이얼로그 띄어서 다시 시작 or 종료 넣을 예정
                showAlertDialog();
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTimer == true){
                    count++;
                    txtCount.setText(count + "번 큭킥!");
                }
            }
        });



        timer.start();
    }




    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //어디 엑티비티에서 보여줄것인지 지정해줘야함.
        builder.setTitle("시간 끝난맨");
        builder.setMessage("다시 도전 할래?");
        // 화면  바깥을 눌러도 사라진다.
        // 사라지는것을 막자.
        builder.setCancelable(false);

        builder.setPositiveButton("다시 도저어어언!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //퀴즈를 처음부터 다시 풀 수 있도록 하자.

                count = 0;
                isTimer = true;
                txtCount.setText("다시 시작!");

                timer.start();
                //recreate();
            }
        });
        builder.setNegativeButton("안해 똥겜", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish(); //액티비티 종료 명령어
            }
        });
        builder.show();
    }
}