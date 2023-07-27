package com.wonjun.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 액티비티 안의 프래그먼트를 가져온 것임.
    // 여기에 다가 지도 셋팅 할 예정
    SupportMapFragment mapFragment =
            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

    // 맵이 준비되면,
    mapFragment.getMapAsync(new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            // 맵이 화면에 나타난다.
                // 로직 작성

            // 내가 정한 위치로 지도 표시
            LatLng myLocation = new LatLng(37.5429, 126.6772);

            // 지도의 중심을, 내가 정한 위치로 셋팅 하면서 확대/축소 하기
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));

            // 마커를 만들어서, 지도에 표시
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(myLocation).title("연희맨");
            googleMap.addMarker(markerOptions).setTag(0); // id대신 tag를 이용해 작동 id를 넣을 수 있다.

            // 한줄로 마커 만들기
            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(37.5438, 126.6772))
                            .title("마커마커맨2")
            );

            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(new LatLng(37.5428, 126.6762)).title("마커마커맨3");
            googleMap.addMarker(markerOptions1);

            // 마커를 클릭 했을때 처리하는 코드 작성
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    // 마커 클릭했을때의 로직 작성.
                    Log.i("GOOGLE MAP", "id : " + marker.getId());
                    Log.i("GOOGLE MAP", "title : " + marker.getTitle());
                    Log.i("GOOGLE MAP", "tag : " + marker.getTag());

                    // tag로 나뉘어서 작성 가능
                    int tag = (int) marker.getTag();
                    if(tag == 0){

                    }else {

                    }

                    return false;
                }
            });

            // 지도 타입 설정가능
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    });

}
}