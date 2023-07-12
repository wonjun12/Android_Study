package com.wonjun.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wonjun.contacts.adapter.ContactAdapter;
import com.wonjun.contacts.data.DatabaseHandler;
import com.wonjun.contacts.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    //리사이클
    RecyclerView recyclerView;
    ContactAdapter adapter;
    ArrayList<Contact> contactArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);

                startActivity(intent);
            }
        });


        /* //연락처를 추가를 하고 메인으로 왔을때 바로 업데이트가 되어 있지 않다. 바로 업데이트를 하도록 작성하자.
        // contactArrayList에 데이터가 있어야하는데 DB에 있다.
        // DB에 저장된 데이터를 불러오자.
        DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "contact_db", null, 1);
        contactArrayList = handler.getAllContacts(); //함수가 필요하다. 만들자.

        adapter = new ContactAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(adapter);

         */
    }

    @Override
    protected void onResume() {
        super.onResume();

        // contactArrayList에 데이터가 있어야하는데 DB에 있다.
        // DB에 저장된 데이터를 불러오자.
        DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "contact_db", null, 1);
        contactArrayList = handler.getAllContacts(); //함수가 필요하다. 만들자.

        adapter = new ContactAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(adapter);
    }
}