package com.wonjun.training12_papagoapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.wonjun.training12_papagoapi.adapter.PapagoAdapter;
import com.wonjun.training12_papagoapi.model.Papago;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PapagoAdapter adapter;
    ArrayList<Papago> papagoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

        papagoArrayList = (ArrayList<Papago>) getIntent().getSerializableExtra("papagoArrayList");

        adapter = new PapagoAdapter(ListActivity.this, papagoArrayList);
        recyclerView.setAdapter(adapter);

    }
}