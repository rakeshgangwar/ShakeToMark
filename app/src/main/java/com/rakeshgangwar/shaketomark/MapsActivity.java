package com.rakeshgangwar.shaketomark;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rakeshgangwar.shaketomark.database.DBHelper;
import com.rakeshgangwar.shaketomark.database.Locations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dbHelper=new DBHelper(this);
        Intent intent = new Intent(this, ShakeService.class);
        startService(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        putMarkers();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mMap!=null)
            putMarkers();
    }

    public void putMarkers(){
        mMap.clear();
        ArrayList<Locations> arrayList=dbHelper.getAllCoordinates();
        if(arrayList.size()>0){
            for(Locations locations:arrayList){
                addMarker(locations.getLatitude(),locations.getLongitude());
            }
        }
    }

    public void addMarker(String latitude, String longitude){
        LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        mMap.addMarker(new MarkerOptions().position(latLng).title("You have been here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
