package com.example.gpsservicedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private Button btnGetGps;
    private TextView txtAusgabe;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetGps = findViewById(R.id.btnGetGps);
        txtAusgabe = findViewById(R.id.txtPostition);

        btnGetGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();

        //in diesem Intervall wird die neuste Location abgefragt
        locationRequest.setInterval(5000);

        //falls eine andere Anwendung ebenfalls GPS benötigt können wir deren Abfrage
        locationRequest.setFastestInterval(3000);

        //hier gibt es noch verschiedene Genauigkeitsstufen, welche sich unterschiedlich auf die Batterie auswirken.
        //möglich sind: (PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_HIGH_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //überprüft ob wir die berechtigung haben auf den GPS service
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //falls wir keine berechtigung haben (bzw. beim ersten starten der App nicht gewährt), wird dies nochmals angefordert
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
            return;
        }
        LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
                //von getLocations() erhalten wir eine Liste mit Locations (objekten) zurück. Auf bzw. von diesen
                //können wir Längen-/Breitengrade abrufen und auch von wo sie diese erhalten haben (Povider)

                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude =
                            locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude =
                            locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    String provider = locationResult.getLocations().get(latestLocationIndex).getProvider();
                    txtAusgabe.setText(String.format(
                            "Breitengrad: %s\nLängengrad: %S\nProvider: %S", latitude, longitude, provider));
                }
            }
        }, Looper.getMainLooper());
    }
}