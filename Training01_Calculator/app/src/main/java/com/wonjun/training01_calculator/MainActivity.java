package com.wonjun.training01_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    Button CalculateTheResultButton;

    EditText EnterPercentNumber;
    EditText EnterYourNumber;

    TextView ResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EnterPercentNumber = findViewById(R.id.EnterPercentNumber);
        EnterYourNumber = findViewById(R.id.EnterYourNumber);
        CalculateTheResultButton = findViewById(R.id.CalculateTheResultButton);
        ResultText = findViewById(R.id.ResultText);

        CalculateTheResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String percentNumberString = EnterPercentNumber.getText().toString().trim();
                String yourNumberString = EnterYourNumber.getText().toString().trim();

                if( percentNumberString.isEmpty() || yourNumberString.isEmpty()){
                    Snackbar.make(
                            CalculateTheResultButton,
                            "필수 항목 넣으셈",
                            Snackbar.LENGTH_SHORT
                    ).show();

                    return;
                }
                int percentNumber= Integer.parseInt(percentNumberString);
                double yourNumber = Double.parseDouble(yourNumberString);

                double result = yourNumber * percentNumber / 100;

                ResultText.setText(result+"");


            }
        });




    }
}