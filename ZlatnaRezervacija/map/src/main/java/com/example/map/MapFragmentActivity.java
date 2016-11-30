package com.example.map;


import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by masrnec on 28.11.2016..
 */

public class MapFragmentActivity extends AppCompatActivity implements  OnMapReadyCallback {
    private GoogleMap gMap;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progress = ProgressDialog.show(this, getString(R.string.MapLoading), getString(R.string.PleaseWait));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_maps);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        progress.dismiss();
        gMap = googleMap;
        LatLng ZlatneGorice = new LatLng(46.235996,16.402431);
        gMap.addMarker(new MarkerOptions().position(ZlatneGorice).title("Zlatne gorice"));
        gMap.getUiSettings().setMapToolbarEnabled(false);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ZlatneGorice, 10));


    }

}
