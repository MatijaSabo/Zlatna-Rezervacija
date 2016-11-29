package com.example.map;


import android.os.Bundle;

import android.support.v4.app.FragmentActivity;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by masrnec on 28.11.2016..
 */

public class MapFragmentActivity extends FragmentActivity implements  OnMapReadyCallback {
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng ZlatneGorice = new LatLng(46.235996,16.402431);
        gMap.addMarker(new MarkerOptions().position(ZlatneGorice).title("Zlatne gorice"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(ZlatneGorice));
    }

}
