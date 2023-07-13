package com.wonjun.training08_memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.training08_memoapp.data.DatabaseHandler;
import com.wonjun.training08_memoapp.model.Memo;

public class MemoAdd extends AppCompatActivity {

    EditText editTitle;
    EditText editContext;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_add);

        editTitle = findViewById(R.id.editTitle);
        editContext = findViewById(R.id.editContext);
        btnSave = findViewById(R.id.btnSave);

        //메모 저장 버튼
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력한 데이터 확인
                String title = editTitle.getText().toString().trim();
                String context = editContext.getText().toString().trim();

                if(title.isEmpty() || context.isEmpty()){
                    Snackbar.make(
                            btnSave,
                            "필수 항목을 입력해주세요.",
                            Snackbar.LENGTH_SHORT
                    ).show();
                    return;
                }


                // 객체로 만들어서 값을 넘기자.
                Memo memo = new Memo(title, context);

                // TODO: 2023-07-13 DB 연결해서 데이터 저장하기
                //db연결
                DatabaseHandler handler = new DatabaseHandler(MemoAdd.this, "memo_db", null, 1);
                handler.insertMemo(memo);

                //안내메시지 출력
                Toast.makeText(MemoAdd.this, "잘 저장되었습니다!!", Toast.LENGTH_LONG).show();

                finish();

            }
        });
    }
}