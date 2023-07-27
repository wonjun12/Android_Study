package com.wonjun.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // 필요한 변수
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 폰의 위치를 가져오기 위해서는, 시스템 서비스로부터 로케이션 매니져를 받아온다.
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // 로케이션 리스트를 만든다.
            // 위치를 받으면 동작하는 함수
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // 위치가 변할때마다 실행된다.

                // 위치가 변경될때 마다 위도, 경도를 가져온다.
                location.getLatitude(); // 위도
                location.getLongitude(); // 경도

                Log.i("MY LOCATION", "위도 : " + location.getLatitude());
                Log.i("MY LOCATION", "경도 : " + location.getLongitude());
            }

        };

        // 위치 권한 허용 여부
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
                3000, // 몇초에 한번 씩 위치를 갱신/파악 하게 할 것인지
                -1, // 몇미터 이동에 한번씩 찾을까
                locationListener);// 어떤 코드를 실행 시킬것인지.
    }

    // 권한 요청을 하면
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
                    3000, // 몇초에 한번 씩 위치를 갱신/파악 하게 할 것인지
                    10, // 몇미터 이동에 한번씩 찾을까
                        // -1은 거리 상관없이 셋팅함.
                    locationListener// 어떤 코드를 실행 시킬것인지.
            );
        }
    }
}