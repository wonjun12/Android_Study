package com.wonjun.training02_cat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button dateButton;
    TextView dateInput;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateButton = findViewById(R.id.dateButton);
        dateInput = findViewById(R.id.dateInput);
        resultText = findViewById(R.id.resultText);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = dateInput.getText().toString().trim();

                if(dateString.length() != 4){
                    Snackbar.make(
                        dateButton,
                        "년도를 제대로 입력하셈",
                        Snackbar.LENGTH_LONG
                    ).show();
                    return;
                }

                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);

                int result = year - Integer.parseInt(dateString);

                resultText.setText(result + "살입니다.");


            }
        });
    }
}