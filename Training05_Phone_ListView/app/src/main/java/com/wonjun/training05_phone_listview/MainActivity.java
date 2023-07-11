package com.wonjun.training05_phone_listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.wonjun.training05_phone_listview.adapter.NamePhoneAdapter;
import com.wonjun.training05_phone_listview.model.NamePhone;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //메인 기본 변수
    EditText editName;
    EditText editPhone;
    Button btnSave;
    
    //리사이클 변수 생성
    RecyclerView recyclerView;
    NamePhoneAdapter adapter;
    ArrayList<NamePhone> namePhoneList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //메인 기본 변수 find
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        //리사이클 찾기
        recyclerView = findViewById(R.id.recyclerView);
        //리사이클 초기화
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Snackbar.make(
                            btnSave,
                            "이름과 전화번호를 전부 입력하세요.",
                            Snackbar.LENGTH_SHORT
                    ).show();
                    return;
                }

                NamePhone namePhone = new NamePhone(name, phone);
                namePhoneList.add(0, namePhone);

                editName.setText("");
                editPhone.setText("");

                adapter.notifyDataSetChanged();

            }
        });

        //어뎁터 객체 생성 연결
        adapter = new NamePhoneAdapter(MainActivity.this, namePhoneList);
        recyclerView.setAdapter(adapter);

    }
}