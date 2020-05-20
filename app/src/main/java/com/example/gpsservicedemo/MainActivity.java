package com.example.gpsservicedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetGps = findViewById(R.id.btnGetGps);
        txtAusgabe = findViewById(R.id.txtPostition);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        location = getLastBestLocation();

        breitengrad = location.getLatitude();
        laengengrad = location.getLongitude();

        System.out.println(breitengrad);
        System.out.println(laengengrad);

        btnGetGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = getLastBestLocation();
                txtAusgabe.setText(location.toString());

            }
        });
    }

        private Location getLastBestLocation() {

            Location zeroLoca = new Location(locationManager.GPS_PROVIDER);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                long GPSLocationTime = 0;
                if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

                long NetLocationTime = 0;

                if (null != locationNet) {
                    NetLocationTime = locationNet.getTime();
                }

                if ( 0 < GPSLocationTime - NetLocationTime ) {
                    System.out.println("gpslocation");
                    return locationGPS;
                }
                else {
                    System.out.println("netlocation");
                    return locationNet;
                }

            }
            System.out.println("error");
            return zeroLoca;



}}
