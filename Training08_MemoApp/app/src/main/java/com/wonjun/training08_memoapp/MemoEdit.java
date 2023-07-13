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

public class MemoEdit extends AppCompatActivity {

    EditText editTitle;
    EditText editContext;
    Button btnSave;

    Memo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        editTitle = findViewById(R.id.editTitle);
        editContext = findViewById(R.id.editContext);
        btnSave = findViewById(R.id.btnSave);

        memo = (Memo) getIntent().getSerializableExtra("memo");

        editTitle.setText(memo.getTitle());
        editContext.setText(memo.getContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                memo.setTitle(title);
                memo.setContext(context);

                DatabaseHandler handler = new DatabaseHandler(MemoEdit.this, "memo_db", null, 1);
                handler.updateMemo(memo);


                //안내메시지 출력
                Toast.makeText(MemoEdit.this, "수정되었습니다!", Toast.LENGTH_LONG).show();

                finish();
            }
        });

    }
}