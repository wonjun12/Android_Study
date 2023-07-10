package com.wonjun.training04_usercreator;

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

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    EditText editEmail;
    EditText editPassword;
    EditText editPasswordEquals;

    Button button;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //데이터를 받아오는 코드는 여기에 작성.

                    if ( result.getResultCode() == 4){
                        finish();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPasswordEquals = findViewById(R.id.editPasswordEquals);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString().trim();

                if(email.contains("@") == false){
                    Snackbar.make(
                            button,
                            "이메일을 바르게 입력하세요.",
                            Snackbar.LENGTH_LONG
                    ).show();
                    return;
                }

                String password = editPassword.getText().toString();
                String passwordEquals = editPasswordEquals.getText().toString();

                if(password.length() < 4 || password.length() > 12){
                    Snackbar.make(
                            button,
                            "비밀번호는 4자리 이상, 12자리 이하입니다.",
                            Snackbar.LENGTH_LONG
                    ).show();
                    return;
                }

                if(!password.equals(passwordEquals)){
                    Snackbar.make(
                            button,
                            "비밀번호가 일치하지 않습니다.",
                            Snackbar.LENGTH_LONG
                    ).show();
                    return;
                }


                // TODO: 2023-07-10 아바타 선택하는 엑티비티를 띄운다.
                Intent intent =new Intent(MainActivity.this, AbataSelect.class);

                intent.putExtra("email", email);

                startActivity(intent);
                finish();
                //launcher.launch(intent);
            }
        });

    }
}