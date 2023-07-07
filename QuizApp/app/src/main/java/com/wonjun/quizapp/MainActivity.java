package com.wonjun.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wonjun.quizapp.model.Quiz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtQuiz;
    TextView txtResult;
    Button btnTrue;
    Button btnFalse;
    ProgressBar progressBar;

    //퀴즈 저장 용 변수
    //ArrayList<String> quizArrayList;
        //클래스를 만든후 다른 것으로 만들자.
    ArrayList<Quiz> quizArrayList = new ArrayList<>();

    Quiz quiz;

    int nextCount;
    int resultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtQuiz = findViewById(R.id.txtQuiz);
        txtResult = findViewById(R.id.txtResult);
        btnFalse = findViewById(R.id.btnFalse);
        btnTrue = findViewById(R.id.btnTrue);
        progressBar = findViewById(R.id.progressBar);

        // 퀴즈를 내야 한다.
        // 퀴즈를 먼저 메모리에 저장해야한다.
        // 퀴즈가 여러개이니, 하나의 변수로 처리하자.
        // 데이터 스트럭처가 필요하며, 자바에서 ArrayList를 사용하자.


        //quizArrayList.add(R.string.q1);
        //에러가 발생한다. 들어가는 값은 String인데 가져오는 값은 id인 int라서 사용할 수가없다.
        //상황에 맞는 id와 텍스트를 만들어야하는데 class를 만들자.

        //이후 퀴즈를 만들어 주자. 너무 길다. 함수로 만들자.
//        Quiz q1 = new Quiz(R.string.q1, true);
//        quizArrayList.add(q1);
//        Quiz q2 = new Quiz(R.string.q2, false);
//        quizArrayList.add(q2);
//        Quiz q3 = new Quiz(R.string.q3, true);
//        quizArrayList.add(q3);
//        Quiz q4 = new Quiz(R.string.q4, false);
//        quizArrayList.add(q4);
//        Quiz q5 = new Quiz(R.string.q5, true);
//        quizArrayList.add(q5);
//        Quiz q6 = new Quiz(R.string.q6, false);
//        quizArrayList.add(q6);
//        Quiz q7 = new Quiz(R.string.q7, true);
//        quizArrayList.add(q7);
//        Quiz q8 = new Quiz(R.string.q8, false);
//        quizArrayList.add(q8);
//        Quiz q9 = new Quiz(R.string.q9, true);
//        quizArrayList.add(q9);
//        Quiz q10 = new Quiz(R.string.q10, false);
//        quizArrayList.add(q10);

        this.setQuiz();

        //프로그래스 바의 활성화 길이를 설정함
        progressBar.setProgress(nextCount+1, true);

        // 문제를 출제한다.
        //Quiz quiz = quizArrayList.get(0);
        quiz = quizArrayList.get(nextCount);
        // 화면에 표시
        txtQuiz.setText(quiz.question);

        //
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!getQuiz()){
                    return;
                }
                //함수 안에 변수를 사용 할 수 없다.
                //멤버 변수로 변경해서 사용하자.
                if(quiz.answer == true){
                    txtResult.setText("정답맨!");
                    resultCount++;
                }else{
                    txtResult.setText("틀림맨!");
                }


            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!getQuiz()){
                    return;
                }

                if(quiz.answer == false){
                    txtResult.setText("정답맨!");
                    resultCount++;
                }else{
                    txtResult.setText("틀림맨!");
                }

            }
        });



    }

    void setQuiz(){
        Quiz q1 = new Quiz(R.string.q1, true);
        quizArrayList.add(q1);
        Quiz q2 = new Quiz(R.string.q2, false);
        quizArrayList.add(q2);
        Quiz q3 = new Quiz(R.string.q3, true);
        quizArrayList.add(q3);
        Quiz q4 = new Quiz(R.string.q4, false);
        quizArrayList.add(q4);
        Quiz q5 = new Quiz(R.string.q5, true);
        quizArrayList.add(q5);
        Quiz q6 = new Quiz(R.string.q6, false);
        quizArrayList.add(q6);
        Quiz q7 = new Quiz(R.string.q7, true);
        quizArrayList.add(q7);
        Quiz q8 = new Quiz(R.string.q8, false);
        quizArrayList.add(q8);
        Quiz q9 = new Quiz(R.string.q9, true);
        quizArrayList.add(q9);
        Quiz q10 = new Quiz(R.string.q10, false);
        quizArrayList.add(q10);
    }

    boolean getQuiz(){



        if (nextCount >= quizArrayList.size()){
            txtResult.setText(resultCount + "개 맞췄음!");

            //팝업을 띄우자.
            showAlertDialog();
            return false;
        }

        quiz = quizArrayList.get(nextCount);
        txtQuiz.setText(quiz.question);
        progressBar.setProgress(nextCount+1, true);

        nextCount++;

        return true;
    }


    // AlertDiaLog 알러트 다이얼로그 만들기 (팝업)
    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //어디 엑티비티에서 보여줄것인지 지정해줘야함.
        builder.setTitle("퀴즈 끝낫네 이거?");
        builder.setMessage(resultCount + "개 맞췄음!");
        // 화면  바깥을 눌러도 사라진다.
            // 사라지는것을 막자.
        builder.setCancelable(false);

        builder.setPositiveButton("화긴", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //퀴즈를 처음부터 다시 풀 수 있도록 하자.
                nextCount = 0;
                Quiz quiz = quizArrayList.get(nextCount);
                txtQuiz.setText(quiz.question);
                txtResult.setText("결과");
                progressBar.setProgress(nextCount+1, true);
                resultCount = 0;
            }
        });
        builder.setNegativeButton("시러", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish(); //액티비티 종료 명령어
            }
        });
        builder.show();
    }
}