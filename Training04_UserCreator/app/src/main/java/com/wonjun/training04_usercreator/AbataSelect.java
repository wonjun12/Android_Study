package com.wonjun.training04_usercreator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class AbataSelect extends AppCompatActivity {

    ImageView imageSelect;

    Button toggiButton;
    Button tuttleButton;
    Button completeButton;

    Boolean isAbatarSelect = false;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abata_select);

        toggiButton = findViewById(R.id.toggiButton);
        tuttleButton = findViewById(R.id.tuttleButton);
        completeButton = findViewById(R.id.completeButton);
        imageSelect = findViewById(R.id.imageSelect);

        email = getIntent().getStringExtra("email");

        toggiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAbatarSelect = true;
                imageSelect.setImageResource(R.drawable.toggi);
            }
        });

        tuttleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAbatarSelect = true;
                imageSelect.setImageResource(R.drawable.tuttle);
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAbatarSelect){
                    Snackbar.make(
                            completeButton,
                            "아바타를 선택하세요!",
                            Snackbar.LENGTH_LONG
                    ).show();

                    return;
                }

                showAlertDialog();
            }
        });
    }



    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AbataSelect.this);

        builder.setTitle("회원가입 완료");
        builder.setMessage("완료하시겠습니까?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: 2023-07-10 새로운 액티비티를 띄운다. 
                Intent intent = new Intent(AbataSelect.this, CompleteCreator.class);

                intent.putExtra("email", email);

                startActivity(intent);

                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: 2023-07-10 앱 종료
                //setResult(4);
                finish();
            }
        });

        builder.show();
    }

}