package com.wonjun.networkapp2_recylerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wonjun.networkapp2_recylerview.adapter.PostAdapter;
import com.wonjun.networkapp2_recylerview.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> postArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // 리사이클 뷰 끼리 경계선 (구분선)을 자동으로 생성해주는 함수
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                MainActivity.this,
                new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        // 네트워크 통신 준비
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // 통신할 HTTP 준비
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // 네트워크로 부터 데이터 파싱 후 화면에 보여준다.
                        try {
                            for(int i = 0; i < response.length(); i++){

                                    JSONObject jsonObject = response.getJSONObject(i);

                                    int userId = jsonObject.getInt("userId");
                                    int id = jsonObject.getInt("id");
                                    String title = jsonObject.getString("title");
                                    String body = jsonObject.getString("body");

                                    Post post = new Post(userId, id, title, body);

                                    postArrayList.add(post);


                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        adapter = new PostAdapter(MainActivity.this, postArrayList);
                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) ;

        // 통신하라
        queue.add(request);



    }
}