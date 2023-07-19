package com.wonjun.training11_youtubeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wonjun.training11_youtubeapp.adapter.YoutubeAdapter;
import com.wonjun.training11_youtubeapp.config.Config;
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

    String searchQuery;
    String nextPageToken;
    String search;

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

        // TODO: 2023-07-19 페이징 처리
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 마지막 데이터가 화면에 나타나게 되면,
                // 네트워크를 통해서, 추가로 데이터를 받아오게한다.
                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition(); //맨 마지막 위치
                int totalCount = recyclerView.getAdapter().getItemCount(); //arrayList값의 크기를 가져온다.

                if(lastPosition + 1 == totalCount){ //스크롤을 데이터 맨 끝까지 한 상태이므로
                            //네트워크를 통해서 데이터를 추가로 받아오면 된다.

                    addNetworkData(); //길이가 너무 길어서 함수로 만들자.
                }

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search = editSearch.getText().toString().trim();

                if(search.isEmpty()){
                    return;
                }

                getNetworkData(); //길이가 너무 길어서 함수로 만들자.
            }
        });
    }

    private void addNetworkData() {
        progressBar.setVisibility(View.VISIBLE);


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.GET,
            Config.HOST + Config.PATH + searchQuery + "&pageToken=" + nextPageToken,
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    setReponseData(response); //중복된 데이터로 함수로 만들자.

                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
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

    private void setReponseData(JSONObject response) {
        try {
            //페이징 토큰
            nextPageToken = response.getString("nextPageToken");


            JSONArray items = response.getJSONArray("items");

            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);

                String videoId = item.getJSONObject("id").getString("videoId");

                JSONObject snippet = item.getJSONObject("snippet");
                String title = snippet.getString("title");
                String description = snippet.getString("description");
                String thumbnailUrl = snippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");


                Youtube youtube = new Youtube(videoId, title, description, thumbnailUrl);

                youtubeArrayList.add(youtube);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void getNetworkData() {
        youtubeArrayList.clear();
        progressBar.setVisibility(View.VISIBLE);
        searchQuery = "?part=snippet&type=video&maxResults=20&key=" + Config.GCP_API_KEY + "&q=" + search;


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.GET,
            Config.HOST + Config.PATH + searchQuery,
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    setReponseData(response); //중복된 데이터로 함수로 만들자.

                    adapter = new YoutubeAdapter(MainActivity.this, youtubeArrayList);
                    recyclerView.setAdapter(adapter);

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
}