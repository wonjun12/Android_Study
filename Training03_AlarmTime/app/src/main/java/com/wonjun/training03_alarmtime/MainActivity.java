package com.wonjun.training03_alarmtime;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {
    TextView txtTimer;
    TextView inputTimer;

    Button resetTimerButton;
    Button startTimerButton;

    ImageView alarmImage;

    CountDownTimer timer;

    int saveTimer;

    MediaPlayer mp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer = findViewById(R.id.txtTimer);
        inputTimer = findViewById(R.id.inputTimer);
        alarmImage = findViewById(R.id.alarmImage);
        resetTimerButton = findViewById(R.id.resetTimerButton);
        startTimerButton = findViewById(R.id.startTimerButton);
        mp = MediaPlayer.create(MainActivity.this, R.raw.alarm);

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputTimerString = inputTimer.getText().toString().trim();

                if(inputTimerString.isEmpty()){
                    return;
                }

                saveTimer = Integer.parseInt(inputTimerString);

                if(saveTimer == 0){
                    return;
                }

                timer = new CountDownTimer(saveTimer * 1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        long timer = l / 1000;
                        txtTimer.setText(timer +" 초");
                    }

                    @Override
                    public void onFinish() {
                        YoYo.with(Techniques.Shake)
                                        .duration(300)
                                                .repeat(5).playOn(alarmImage);
                        mp.start();
                    }
                };

                timer.start();

            }
        });

        resetTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timer == null){
                    return;
                }
                txtTimer.setText(saveTimer +" 초");
                timer.cancel();
            }
        });
    }
}