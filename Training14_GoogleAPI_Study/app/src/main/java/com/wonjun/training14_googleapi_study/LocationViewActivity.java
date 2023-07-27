package com.wonjun.training14_googleapi_study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wonjun.training14_googleapi_study.model.Place;

public class LocationViewActivity extends AppCompatActivity {

    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);

        place = (Place) getIntent().getSerializableExtra("place");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                double lat = place.getGeometry().getLocation().getLat();
                double lng = place.getGeometry().getLocation().getLng();

                LatLng location = new LatLng(lat, lng);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));

                MarkerOptions markerOptions =new MarkerOptions();
                markerOptions.position(location).title(place.getName());
                googleMap.addMarker(markerOptions).setTag(0);
            }
        });


    }
}