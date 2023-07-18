package com.wonjun.training11_youtubeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wonjun.training11_youtubeapp.adapter.YoutubeAdapter;
import com.wonjun.training11_youtubeapp.model.Youtube;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    ImageView btnSearch;
    ProgressBar progressBar;

    RecyclerView recyclerView;
    YoutubeAdapter adapter;
    ArrayList<Youtube> youtubeArrayList = new ArrayList<>();

    static String youtubeSearchUrl = "https://www.googleapis.com/youtube/v3/search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        progressBar.setVisibility(View.INVISIBLE);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String search = editSearch.getText().toString().trim();

                if(search.isEmpty()){
                    return;
                }
                youtubeArrayList = new ArrayList<>();
                progressBar.setVisibility(View.VISIBLE);
                String searchQuery = "?part=snippet&type=video&maxResults=20&key=AIzaSyCpkTHntbDSnfmOPKQGYSX2TqC9ZCqlFc4&q=" + search;


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET,
                        youtubeSearchUrl + searchQuery,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray items = response.getJSONArray("items");

                                    for(int i = 0; i < items.length(); i++){
                                        JSONObject item = items.getJSONObject(i);

                                        String videoId = item.getJSONObject("id").getString("videoId");

                                        JSONObject snippet = item.getJSONObject("snippet");
                                        String title = snippet.getString("title");
                                        String description = snippet.getString("description");
                                        String thumbnailUrl = snippet.getJSONObject("thumbnails").getJSONObject("medium").getString("url");


                                        Youtube youtube = new Youtube(videoId, title, description, thumbnailUrl);

                                        youtubeArrayList.add(youtube);
                                    }

                                    adapter = new YoutubeAdapter(MainActivity.this, youtubeArrayList);
                                    recyclerView.setAdapter(adapter);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                );

                queue.add(request);


            }
        });

    }
}