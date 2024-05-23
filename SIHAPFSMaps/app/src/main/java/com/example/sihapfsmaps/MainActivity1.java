package com.example.sihapfsmaps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MainActivity1 extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity1.this);

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        LatLng a = new LatLng(16.506174, 80.648018);
        LatLng b = new LatLng(16.292000, 81.259499);
        LatLng c = new LatLng(16.537791, 80.599426);
        LatLng d = new LatLng(16.5065, 80.6442);
        LatLng e = new LatLng(16.5140,80.6566);
        LatLng f = new LatLng(16.5006,80.6340);
        myMap.moveCamera(CameraUpdateFactory.newLatLng(a));
        MarkerOptions options = new MarkerOptions().position(a).title("AMBULANCE,"+"1234567890");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        myMap.addMarker(options);
        myMap.addMarker(new MarkerOptions().position(c).title("FIRE,"+"98735465328"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(c));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(b));
        MarkerOptions options1 = new MarkerOptions().position(b).title("POLICE,"+"3264785921");
        options1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        myMap.addMarker(options1);
        myMap.moveCamera(CameraUpdateFactory.newLatLng(d));
        MarkerOptions options2 = new MarkerOptions().position(d).title("AMBULANCE,"+"1456987245");
        options2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        myMap.addMarker(options2);
        myMap.addMarker(new MarkerOptions().position(f).title("FIRE,"+"7895648597"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(f));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(e));
        MarkerOptions options3 = new MarkerOptions().position(e).title("POLICE,"+"56894795684");
        options3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        myMap.addMarker(options3);
    }

}
