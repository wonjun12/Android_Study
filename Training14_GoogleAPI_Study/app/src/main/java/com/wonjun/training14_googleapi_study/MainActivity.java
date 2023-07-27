package com.wonjun.training14_googleapi_study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.wonjun.training14_googleapi_study.adapter.PlaceAdapter;
import com.wonjun.training14_googleapi_study.api.NetworkClient;
import com.wonjun.training14_googleapi_study.api.PlaceApi;
import com.wonjun.training14_googleapi_study.config.Config;
import com.wonjun.training14_googleapi_study.model.Place;
import com.wonjun.training14_googleapi_study.model.PlaceRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    EditText editSearch;
    Button btnSearch;

    RecyclerView recyclerView;
    PlaceAdapter adapter;
    ArrayList<Place> placeArrayList = new ArrayList<>();

    String next_page_token;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition(); //맨 마지막 위치
                int totalCount = recyclerView.getAdapter().getItemCount(); //arrayList값의 크기를 가져온다.

                if(lastPosition + 1 == totalCount){ //스크롤을 데이터 맨 끝까지 한 상태이므로
                    if(next_page_token != null){
                        progressBar.setVisibility(View.VISIBLE);

                        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                        PlaceApi api = retrofit.create(PlaceApi.class);

                        Call<PlaceRes> call = api.addPlaceList(next_page_token, Config.API_KEY);
                        call.enqueue(new Callback<PlaceRes>() {
                            @Override
                            public void onResponse(Call<PlaceRes> call, Response<PlaceRes> response) {
                                progressBar.setVisibility(View.INVISIBLE);
                                if(response.isSuccessful()){
                                    PlaceRes res = response.body();


                                    next_page_token = res.getNext_page_token();

                                    placeArrayList.addAll(res.getResults());

                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<PlaceRes> call, Throwable t) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });


                    }
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = editSearch.getText().toString().trim();

                if(search.isEmpty()){
                    return;
                }

                placeArrayList.clear();

                progressBar.setVisibility(View.VISIBLE);


                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
//?language=ko&location=37.544147,126.8357822&radius=2000&type=restaurant&key=[
                PlaceApi api = retrofit.create(PlaceApi.class);

                Call<PlaceRes> call = api.getPlaceList("ko", search, "restaurant", 2000, "37.544147,126.8357822", Config.API_KEY);
                //Call<PlaceRes> call = api.getPlaceList("ko", "restaurant", 2000, "37.544147,126.8357822", Config.API_KEY);
                call.enqueue(new Callback<PlaceRes>() {
                    @Override
                    public void onResponse(Call<PlaceRes> call, Response<PlaceRes> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if(response.isSuccessful()){
                            PlaceRes res = response.body();


                            next_page_token = res.getNext_page_token();

                            placeArrayList.addAll(res.getResults());

                            adapter = new PlaceAdapter(MainActivity.this, placeArrayList);
                            recyclerView.setAdapter(adapter);
                        }else {

                        }

                    }

                    @Override
                    public void onFailure(Call<PlaceRes> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.btnToMap){
            Intent intent = new Intent(MainActivity.this, AllLocationViewActivity.class);
            intent.putExtra("placeArrayList", placeArrayList);
            startActivity(intent);
        }

        return true;
    }
}