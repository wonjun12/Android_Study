package com.wonjun.training09_networkapp_volley;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.wonjun.training09_networkapp_volley.adapter.MemberAdapter;
import com.wonjun.training09_networkapp_volley.model.Member;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    RecyclerView recyclerView;
    MemberAdapter adapter;
    ArrayList<Member> memberArrayList = new ArrayList<>();

    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //데이터를 받아오는 코드는 여기에 작성.
                    Log.i("MAIN", result.getResultCode() + "");
                    switch (result.getResultCode()){
                        // 직원 추가
                        case 22:
                            {
                                Member member = (Member) result.getData().getSerializableExtra("member");
                                memberArrayList.add(0,member);
                            }
                            break;

                            //직원 수정
                        case 23:
                            {
                                int index = result.getData().getIntExtra("index", 0);
                                Member member = (Member) result.getData().getSerializableExtra("member");

                                memberArrayList.set(index, member);
                            }

                            break;

                    }
                    if ( result.getResultCode() == 22){


                        /*adapter = new MemberAdapter(MainActivity.this, memberArrayList);
                        recyclerView.setAdapter(adapter);*/
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/employees.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");

                            if(!status.equals("success")){
                                return;
                            }

                            JSONArray jsonArray = response.getJSONArray("data");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("employee_name");
                                int salary = jsonObject.getInt("employee_salary");
                                int age = jsonObject.getInt("employee_age");

                                Member member = new Member(id, name, salary, age);

                                memberArrayList.add(member);
                            }

                            adapter = new MemberAdapter(MainActivity.this, memberArrayList);
                            recyclerView.setAdapter(adapter);

                            Snackbar.make(
                                    btnAdd,
                                    response.getString("message"),
                                    Snackbar.LENGTH_SHORT
                            ).show();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(request);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemberAdd.class);

                launcher.launch(intent);
                //startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new MemberAdapter(MainActivity.this, memberArrayList);
        recyclerView.setAdapter(adapter);
    }
}