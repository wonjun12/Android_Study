package com.wonjun.diceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //변수이름과 화면아이디의 이름을 동일하게 하면 쉽게 찾을 수 있다.
    Button button; //Button이 클래스 이름이다.
    ImageView imgDice1;
    ImageView imgDice2;

    //동영상 코드 (에니메이션 효과 넣는 클래스)
    MediaPlayer mp;

    //화면에서 유저와 인터렉티브(변화되는 부분)되는 뷰들은 멤버 변수로 만든다.

    @Override
    protected void onCreate(Bundle savedInstanceState) { //CPU가 여기로 들어옴
        super.onCreate(savedInstanceState);

        //자바랑 XML 화면을 연결하는 코드
        setContentView(R.layout.activity_main);

        //화면의 뷰를 자바의 변수와 연결시키는 코드.
        button = findViewById(R.id.button); //해당 변수에 화면의 id를 연결 시킴
        imgDice1 = findViewById(R.id.imgDice1);
        imgDice2 = findViewById(R.id.imgDice2);

        // 변수 셋팅 코드 (동영상 코드도 초기화를 시켜줘야한다.)
        mp = MediaPlayer.create(MainActivity.this, R.raw.dice_sound); //사운드 준비시켜 달라



        // 유저가 탭하는 것을 유저의 이벤트라고 한다.

        //유저가 버튼을 누르면, 작성하는 코드
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //테스트 코드 작성
                // 로그를 찍어보자
                System.out.println("버튼 클리"); //
                Log.i("DICE MAIN", "버튼 클릭"); //tag 이름을 변경해서 할 수 있음

                // 1. 주사위 굴리는 소리
                    mp.start(); //음악 재생

                // 2. 랜덤으로 숫자 2개를 가져온다. (주사위 2개니깐)
                Random random = new Random();
                int num1 = random.nextInt(5+1); // 0 ~ 5까지 가져오는 랜덤함수
                int num2 = random.nextInt(5+1);
                Log.i("DICE NUM", "랜덤 숫자1 : " + num1 + "/ 랜덤 숫자2 : " + num2);

                // 3. 해당 숫자에 맞는 주사위 이미지를 보여준다.
                if(num1 == 0){
                    // 주사위 1을 화면에 표시한다.
                    imgDice1.setImageResource(R.drawable.dice1);
                }else if(num1 == 1){
                    //주사위 2를 화면에 표시한다.
                    imgDice1.setImageResource(R.drawable.dice2);
                }else if(num1 == 2){
                    //주사위 3을 화면에 표시한다.
                    imgDice1.setImageResource(R.drawable.dice3);
                }else if(num1 == 3){
                    imgDice1.setImageResource(R.drawable.dice4);
                }else if(num1 == 4){
                    imgDice1.setImageResource(R.drawable.dice5);
                }else if(num1 == 5){
                    imgDice1.setImageResource(R.drawable.dice6);
                }


                switch (num2){
                    case 0:
                        imgDice2.setImageResource(R.drawable.dice1);
                        break;
                    case 1:
                        imgDice2.setImageResource(R.drawable.dice2);
                        break;
                    case 2:
                        imgDice2.setImageResource(R.drawable.dice3);
                        break;
                    case 3:
                        imgDice2.setImageResource(R.drawable.dice4);
                        break;
                    case 4:
                        imgDice2.setImageResource(R.drawable.dice5);
                        break;
                    case 5:
                        imgDice2.setImageResource(R.drawable.dice6);
                        break;
                }


                // 4. 애니메이션 효과를 준다.
                YoYo.with(Techniques.Tada) // 에니메이션 종류
                        .duration(300) //몇 밀리초에 걸쳐서 에니메이션 재생
                        .repeat(1) //몇번 반복할 것인지
                        .playOn(imgDice1); // 어떤거에 적용할 것인지

                YoYo.with(Techniques.Tada)
                        .duration(600)
                        .repeat(0)
                        .playOn(imgDice2);
            }
        });
    }
}