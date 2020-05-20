package com.example.gpsservicedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnGetGps;
    private TextView txtAusgabe;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location location;

    private double breitengrad;
    private double laengengrad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetGps = findViewById(R.id.btnGetGps);
        txtAusgabe = findViewById(R.id.txtPostition);

        btnGetGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                location = getLocation();
                txtAusgabe.setText(location.toString());
            }
        });
    }

    private Location getLocation() {

        //diese location wird return wen wir keine richtige Location erhalten
        Location loc = new Location("dummyprovider");

        //testen ob wir die Permission haben
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //richtige GPS location wird returnt
            return locationGPS;

        } else {

            System.out.println("error");
            //wen permisson fehlgeschlagen ist dan wird die dummy location returnt
            return loc;}

    }


}
