package com.wonjun.training14_googleapi_study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wonjun.training14_googleapi_study.model.Place;

import java.util.ArrayList;

public class AllLocationViewActivity extends AppCompatActivity {

    ArrayList<Place> placeArrayList;
    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_location_view);

        lat = getIntent().getDoubleExtra("lat", 0);
        lon = getIntent().getDoubleExtra("lon", 0);

        placeArrayList = (ArrayList<Place>) getIntent().getSerializableExtra("placeArrayList");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                // 우리의 위치를 맵의 중심으로 놓는다.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 11));

                // foreach 방법
                for(Place place : placeArrayList){
                    double lat = place.getGeometry().getLocation().getLat();
                    double lng = place.getGeometry().getLocation().getLng();

                    LatLng location = new LatLng(lat, lng);

                    MarkerOptions markerOptions =new MarkerOptions();
                    markerOptions.position(location).title(place.getName());
                    googleMap.addMarker(markerOptions);
                }

                /*
                for(int i = 0; i< placeArrayList.size(); i++){
                    Place place = placeArrayList.get(i);

                    double lat = place.getGeometry().getLocation().getLat();
                    double lng = place.getGeometry().getLocation().getLng();

                    LatLng location = new LatLng(lat, lng);

                    MarkerOptions markerOptions =new MarkerOptions();
                    markerOptions.position(location).title(place.getName());
                    googleMap.addMarker(markerOptions).setTag(i);
                }*/
               // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
            }
        });

    }
}