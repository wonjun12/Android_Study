package com.wonjun.memoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wonjun.memoapp.adapter.MemoAdapter;
import com.wonjun.memoapp.api.MemoApi;
import com.wonjun.memoapp.api.NetworkClient;
import com.wonjun.memoapp.api.UserApi;
import com.wonjun.memoapp.config.Config;
import com.wonjun.memoapp.memo.AddActivity;
import com.wonjun.memoapp.model.Memo;
import com.wonjun.memoapp.model.MemoList;
import com.wonjun.memoapp.model.ResultRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button btnAdd;

    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoArrayList = new ArrayList<>();

    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인 한 유저인지 아닌지 체크를 해야한다.
        // 로그인을 안했으면, 회원가입 or 로그인 액티비티를 실행한다.
            // 확인하기 위해서는 따로 저장이 되어있어야한다. 파일에 저장하는 방법을 사용하자.
        //SharedPreferences sp = getSharedPreferences("MemoApp", MODE_PRIVATE); 타이밍해서 오타날수도 있으니 Config에 넣자.
        SharedPreferences sp = getSharedPreferences(Config.PREFFERENCE_NAME, MODE_PRIVATE);
            // 저장되어 있는 것이 있는지 확인
        //String accessToken = sp.getString("accessToken", "");
        accessToken = sp.getString(Config.ACCESS_TOKEN, "");
        if(accessToken.isEmpty()){ //비어 있냐? 여부 확인
            //비어있다면 로그인을 한번도 한적 없다는 뜻이다.
                // 회원가입으로 보내자.
            Intent intent = new Intent(MainActivity.this, UserRegisterActivity.class);
            startActivity(intent);

            finish(); // 더이상 진행되면 안되니 종료하자.
            return; //리턴을 하지 않으면 아래의 코드를 실행함
                    // 왜? onCreate는 아직 끝나지 않았기 때문에
        }
        


        // 했으면, 아래의 코드를 실행


        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        // 네트워크를 받는 함수를 따로 만들자.
        //getNetworkData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getNetworkData();
    }

    private void getNetworkData() {
        // 프로그레스바 보이게하기
        progressBar.setVisibility(View.VISIBLE);
        memoArrayList.clear();

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

        MemoApi api = retrofit.create(MemoApi.class);

        // 헤더에 넣을 값의 토큰을 가져와서 넣는다.
        Call<MemoList> call = api.getMemoList("Bearer " + accessToken);
        call.enqueue(new Callback<MemoList>() {
            @Override
            public void onResponse(Call<MemoList> call, Response<MemoList> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if(response.isSuccessful()){

                    MemoList memoList = response.body();

                    memoArrayList.addAll(memoList.getItems());
                    adapter = new MemoAdapter(MainActivity.this, memoArrayList);
                    recyclerView.setAdapter(adapter);
                }else {
                    Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                    startActivity(intent);

                    finish();
                }
            }

            @Override
            public void onFailure(Call<MemoList> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
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

        if(itemId == R.id.btnLogout){
            Retrofit  retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

            UserApi api = retrofit.create(UserApi.class);
            Call<ResultRes> call = api.logout("Bearer " + accessToken);
            call.enqueue(new Callback<ResultRes>() {
                @Override
                public void onResponse(Call<ResultRes> call, Response<ResultRes> response) {
                    if(response.isSuccessful()){
                        onResume();
                    }
                }

                @Override
                public void onFailure(Call<ResultRes> call, Throwable t) {

                }
            });
        }

        return true;
    }
}