package com.wonjun.training14_googleapi_study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
    // 내 위치 가져오기
    LocationManager locationManager;
    LocationListener locationListener;
    double lat;
    double lon;

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

        // 폰의 위치를 가져오기 위해서는, 시스템 서비스로부터 로케이션 매니져를 받아온다.
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // 로케이션 리스트를 만든다.
        // 위치를 받으면 동작하는 함수
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // 위치가 변할때마다 실행된다.

                // 위치가 변경될때 마다 위도, 경도를 가져온다.
                lat =  location.getLatitude(); // 위도
                lon =  location.getLongitude(); // 경도
            }
        };

        // 위치 권한 허용 여부
        if(ActivityCompat.checkSelfPermission(
                MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                !=
                PackageManager.PERMISSION_GRANTED){ // 위치 권한 허용 했냐
            // 권한 요청 하지 않았다면,
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    100); // 다시 권한을 요청한다.
            return;
        }

        // 권한이 정상적으로 허용되었다면,
        // 로케이션 매니저에, 리스너를 연결한다.
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, // GPS 센서 연결
                5000, // 몇초에 한번 씩 위치를 갱신/파악 하게 할 것인지
                -1, // 몇미터 이동에 한번씩 찾을까
                locationListener);// 어떤 코드를 실행 시킬것인지.

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

                Call<PlaceRes> call = api.getPlaceList("ko", search, "restaurant", 2000, lat+","+lon, Config.API_KEY);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            // 권한 허용이 또 안되었으수도 있으니, 다시 확인하자.
            if(ActivityCompat.checkSelfPermission(
                    MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    !=
                    PackageManager.PERMISSION_GRANTED){ // 위치 권한 허용 했냐
                // 권한 요청 하지 않았다면,
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        100); // 다시 권한을 요청한다.
                return;
            }

            // 권한이 정상적으로 허용되었다면,
            // 로케이션 매니저에, 리스너를 연결한다.
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, // GPS 센서 연결
                    5000, // 몇초에 한번 씩 위치를 갱신/파악 하게 할 것인지
                    -1, // 몇미터 이동에 한번씩 찾을까
                    // -1은 거리 상관없이 셋팅함.
                    locationListener// 어떤 코드를 실행 시킬것인지.
            );
        }
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

            // 내 위치정보도 같이 넘겨주자.
            intent.putExtra("lat", lat);
            intent.putExtra("lon", lon);


            startActivity(intent);
        }

        return true;
    }
}