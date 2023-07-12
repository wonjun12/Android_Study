package com.wonjun.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.contacts.data.DatabaseHandler;
import com.wonjun.contacts.model.Contact;

public class AddActivity extends AppCompatActivity {
    EditText editName;
    EditText editPhone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Snackbar.make(
                            btnSave,
                            "필수 항목 입력하세요.",
                            Snackbar.LENGTH_SHORT
                    ).show();

                    return;
                }

                //이름과 전화번호 저장 / 메모리에 저장됨
                Contact contact = new Contact(name, phone);

                //DB에 저장하자.
                    // 핸드폰 고유의 DB가 존재한다. 해당 DB에 저장하자. (옛날에 사용한 방법이라고 함)
                // TODO: 2023-07-12 DB 저장 로직 작성 예정  SQLite3 이라고 DB종류가 존재한다.
                    // 1. DB 핸들러 생성
                    // 2. onCreate로 테이블 생성
                    // 3. 필요한 CRUD 메서드 생성
                // 만든 후 이제 DB를 실행한다.
                // DB 핸들러 생성
                DatabaseHandler handler = new DatabaseHandler(AddActivity.this, "contact_db", null, 1);
                                            //(어디 액티비티에서 실행되는지, DB 이름(스키마), 팩토리, 버전)
                //DB 실행
                handler.addContact(contact);

                //안내메시지 출력
                Toast.makeText(AddActivity.this, "잘 저장되었습니다!!", Toast.LENGTH_LONG).show();


                finish();

            }
        });
    }
}