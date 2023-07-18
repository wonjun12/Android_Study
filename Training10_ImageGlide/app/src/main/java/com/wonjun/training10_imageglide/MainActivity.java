package com.wonjun.training10_imageglide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wonjun.training10_imageglide.adapter.PhotoAdapter;
import com.wonjun.training10_imageglide.model.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    RecyclerView recyclerView;
    PhotoAdapter adapter;
    ArrayList<Photo> photoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/photos.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int albumId = jsonObject.getInt("albumId");
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String url = jsonObject.getString("url");
                                String thumbnailUrl = jsonObject.getString("thumbnailUrl");

                                Photo photo = new Photo(albumId, id, title, url, thumbnailUrl);

                                photoArrayList.add(photo);
                            }

                            adapter = new PhotoAdapter(MainActivity.this, photoArrayList);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);


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
    }
}