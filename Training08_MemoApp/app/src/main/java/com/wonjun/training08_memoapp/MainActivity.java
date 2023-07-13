package com.wonjun.training08_memoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.training08_memoapp.adapter.MemoAdapter;
import com.wonjun.training08_memoapp.data.DatabaseHandler;
import com.wonjun.training08_memoapp.model.Memo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    //리사이클 변수
    RecyclerView recyclerView;
    ArrayList<Memo> memoArrayList = new ArrayList<>();
    MemoAdapter adapter;

    //검색 텍스트 및 버튼
    EditText editSearch;
    ImageView imageSearch;
    ImageView imageClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //검색 관련
        editSearch = findViewById(R.id.editSearch);
        imageSearch = findViewById(R.id.imageSearch);
        imageClear = findViewById(R.id.imageClear);

        //메모 생성 이동 버튼
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoAdd.class);

                startActivity(intent);
            }
        });

        // TODO: 2023-07-13 검색 버튼 작성
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = editSearch.getText().toString().trim();

                if(search.isEmpty()){
                    Snackbar.make(
                            imageSearch,
                            "검색어를 입력해주세요.",
                            Snackbar.LENGTH_SHORT
                    ).show();
                    return;
                }

                DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "memo_db", null, 1);
                memoArrayList = handler.getSelectMemo(search);

                resetAdapter();
            }
        });

        imageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSearch.setText("");

                onResume();
            }
        });


        //https://onlyfor-me-blog.tistory.com/435
        //https://lktprogrammer.tistory.com/185
        // TODO: 2023-07-13 검색 텍스트 입력시 실시간 검색
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = editSearch.getText().toString();

                if(search.isEmpty()){
                    onResume();

                    return;
                }

                DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "memo_db", null, 1);
                memoArrayList = handler.getSelectMemo(search);

                resetAdapter();

            }
        });


    }

    @Override
    protected void onResume() {//작성 후에도 바로 적용될수 잇도록 여기다 리사이클 작성
        super.onResume();

        // TODO: 2023-07-13 db에서 데이터 불러오기
        DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "memo_db", null, 1);
        memoArrayList = handler.getAllSelectMemo();

        resetAdapter();
    }

    private void resetAdapter(){
        adapter = new MemoAdapter(MainActivity.this, memoArrayList);
        recyclerView.setAdapter(adapter);
    }
}