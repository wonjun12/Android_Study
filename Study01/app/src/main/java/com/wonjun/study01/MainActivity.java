package com.wonjun.study01;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnDice;
    ImageView imgDice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDice = findViewById(R.id.btnDice);
        imgDice = findViewById(R.id.imgDice);

        btnDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();

                int num = random.nextInt(5+1);

                switch (num){
                    case 0:
                        imgDice.setImageResource(R.drawable.dice1);
                        break;
                    case 1:
                        imgDice.setImageResource(R.drawable.dice2);
                        break;
                    case 2:
                        imgDice.setImageResource(R.drawable.dice3);
                        break;
                    case 3:
                        imgDice.setImageResource(R.drawable.dice4);
                        break;
                    case 4:
                        imgDice.setImageResource(R.drawable.dice5);
                        break;
                    case 5:
                        imgDice.setImageResource(R.drawable.dice6);
                        break;
                }
            }
        });
    }
}