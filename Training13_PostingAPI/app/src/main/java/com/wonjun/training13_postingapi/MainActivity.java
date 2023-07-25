package com.wonjun.training13_postingapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wonjun.training13_postingapi.adapter.PostAdapter;
import com.wonjun.training13_postingapi.api.NetworkClient;
import com.wonjun.training13_postingapi.api.PostApi;
import com.wonjun.training13_postingapi.config.Config;
import com.wonjun.training13_postingapi.model.Post;
import com.wonjun.training13_postingapi.model.PostRes;
import com.wonjun.training13_postingapi.post.PostCreateActivity;
import com.wonjun.training13_postingapi.user.UserLoginActivity;
import com.wonjun.training13_postingapi.user.UserRegisterActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<Post> postArrayList = new ArrayList<>();
    PostAdapter adapter;

    Button btnPostAdd;

    String accessToken;

    // 페이징 처리에 필요한 변수
    int offset = 0;
    int limit = 5;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE, MODE_PRIVATE);
        accessToken = sp.getString(Config.ACCESS_TOKEN, "");
        if(accessToken.isEmpty()){
            Intent intent = new Intent(MainActivity.this, UserRegisterActivity.class);

            startActivity(intent);
            finish();
            return;
        }

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        btnPostAdd = findViewById(R.id.btnPostAdd);

        btnPostAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostCreateActivity.class);
                startActivity(intent);
            }
        });

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
                    //네트워크를 통해서 데이터를 추가로 받아오면 된다.

                    if(count == limit){
                        getNetworkPost();
                    }

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllListPost();
    }

    protected void getAllListPost(){
        postArrayList.clear();
        offset = 0;

        getNetworkPost();
    }

    protected void getNetworkPost(){
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

        PostApi api = retrofit.create(PostApi.class);
        Call<PostRes> call = api.getAllPost(accessToken, offset, limit);
        call.enqueue(new Callback<PostRes>() {
            @Override
            public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if(response.isSuccessful()){
                    PostRes res = response.body();

                    // 페이징을 위해 count를 저장시킨
                    count = res.getCount();
                    offset = offset + count;

                    postArrayList.addAll(res.getItems());

                    adapter = new PostAdapter(MainActivity.this, postArrayList);
                    recyclerView.setAdapter(adapter);
                }else {
                    Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                    startActivity(intent);

                    finish();
                }
            }

            @Override
            public void onFailure(Call<PostRes> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}