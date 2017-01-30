package com.foi.map;


import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    public void onMapReady(GoogleMap googleMap) {
        progress.dismiss();
        gMap = googleMap;
        if(gMap != null ){
            gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }
                /*  Uređivanje markera */
                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.marker_info,null);
                    TextView markerInfoTittle = (TextView) v.findViewById(R.id.markerInfoTittle);
                    TextView markerInfoAdress = (TextView) v.findViewById(R.id.markerInfoAdress);
                    TextView markerInfoAdressCity = (TextView) v.findViewById(R.id.markerInfoAdressCity);
                    markerInfoTittle.setText(R.string.markerInfoTittle);
                    markerInfoAdress.setText(R.string.markerInfoAdress);
                    markerInfoAdressCity.setText(R.string.markerInfoAdressCity);
                    return v;
                }
            });
            /* Dodavanje markera na mapu,pozicioniranje kamere...  */
            LatLng ZlatneGorice = new LatLng(46.235996,16.402431);
            gMap.addMarker(new MarkerOptions().position(ZlatneGorice)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            gMap.getUiSettings().setMapToolbarEnabled(false);
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ZlatneGorice, 10));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu_type,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /*  Promjena načina prikaza mape */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle()==getString(R.string.menuTypeNormal)){
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } if(item.getTitle()==getString(R.string.menuTypeTerrain)){
            gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } if(item.getTitle()==getString(R.string.menuTypeHybrid)){
            gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } if(item.getTitle()==getString(R.string.menuTypeSatellite)){
            gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
