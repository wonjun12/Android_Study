package com.wonjun.uitest1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textTitle;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTitle = findViewById(R.id.textTitle); //연결
        
        textTitle.setText("우아아아아아악");

        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. 유저가 입력한 이름을 가져온다. + 문자열로 변환 + 공백제거(strip:trim)
                String name = editName.getText().toString().trim();
                Log.i("UITest1 Main", name ) ;

                // 2. 유저가 입력한 비밀 번호를 가져온다.
                String password = editPassword.getText().toString().trim();
                Log.i("UITest1 Main", password) ;

                // 3. 유저가 입력한 이메일 가져온다.
                String email = editEmail.getText().toString().trim();
                Log.i("UITest Main", email);

                // 4. 데이터가 없을 경우 유저에게 알려준다.
                // ex) 이름은 필수항목 입니다. 입력해주세요.

                if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
                    // context: 이 클래스,  LENGTH_SHOT 화면 작게
//                    Toast.makeText(MainActivity.this,
//                            "필수항목 모두 입력해주세요(makeText)",
//                            Toast.LENGTH_SHORT).show();

                    Snackbar.make(btnSave,
                            "필수항목 모두 입력하세요(Snackber)",
                            Snackbar.LENGTH_LONG).show();
                    return;
                }

                // 정상이니까 네트워크로 데이터를 보내준다.
    }
}