package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ApproachingTripsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;
    private Button backButton;
    private RadioButton lessthan1, greaterthan1, greaterthan2;
    private FloatingActionButton currentLocation, driversFloating;
    private int toggleFlagcurrentLocation = 0, toggleFlagDrivers = 0;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approaching_trips);

        inialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        lessthan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        greaterthan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        greaterthan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (toggleFlagcurrentLocation == 0) {
                    currentLocation.setBackgroundTintList(ColorStateList.valueOf(Color
                            .parseColor("#02AEF0")));
                    if (gpsTracker.canGetLocation()) {
                        double latitude = gpsTracker.getLatitude();
                        double longitude = gpsTracker.getLongitude();
                        LatLng addr = new LatLng(latitude, longitude);
                        mapAPI.clear();
                        mapAPI.addMarker(new MarkerOptions().position(addr).title("currentLocation"));
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(addr));
                        mapAPI.setMinZoomPreference(15.0f);
                    } else {
                        gpsTracker.showSettingsAlert();
                    }
                    toggleFlagcurrentLocation = 1;
                } else if (toggleFlagcurrentLocation == 1) {
                    currentLocation.setBackgroundTintList(ColorStateList.valueOf(Color
                            .parseColor("#ffcdd8ec")));
                    if (gpsTracker.canGetLocation()) {
                        double latitude = 32.383636;
                        double longitude = 74.426882;
                        LatLng addr = new LatLng(latitude, longitude);
                        mapAPI.clear();
                        mapAPI.addMarker(new MarkerOptions().position(addr).title("otherLocation"));
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(addr));
                        mapAPI.setMinZoomPreference(15.0f);
                    } else {
                        gpsTracker.showSettingsAlert();
                    }
                    toggleFlagcurrentLocation = 0;
                }
            }
        });

        driversFloating.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (toggleFlagDrivers == 0) {
                    driversFloating.setBackgroundTintList(ColorStateList.valueOf(Color
                            .parseColor("#02AEF0")));
                    if (gpsTracker.canGetLocation()) {
                        double latitude = 32.599936;
                        double longitude = 74.255582;
                        LatLng addr = new LatLng(latitude, longitude);
                        mapAPI.clear();
                        mapAPI.addMarker(new MarkerOptions().position(addr).title("driverLocation"));
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(addr));
                        mapAPI.setMinZoomPreference(15.0f);
                    } else {
                        gpsTracker.showSettingsAlert();
                    }
                    toggleFlagDrivers = 1;
                } else if (toggleFlagDrivers == 1) {
                    driversFloating.setBackgroundTintList(ColorStateList.valueOf(Color
                            .parseColor("#ffcdd8ec")));
                    if (gpsTracker.canGetLocation()) {
                        double latitude = 32.383636;
                        double longitude = 74.426882;
                        LatLng addr = new LatLng(latitude, longitude);
                        mapAPI.clear();
                        mapAPI.addMarker(new MarkerOptions().position(addr).title("otherdriverLocation"));
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(addr));
                        mapAPI.setMinZoomPreference(15.0f);
                    } else {
                        gpsTracker.showSettingsAlert();
                    }
                    toggleFlagDrivers = 0;
                }


            }
        });

    }

    private void inialization() {
        gpsTracker = new GpsTracker(ApproachingTripsActivity.this);
        backButton = findViewById(R.id.backButton_approaching_trips);
        lessthan1 = findViewById(R.id.less1_approaching_trips);
        greaterthan1 = findViewById(R.id.greater1_approaching_trips);
        greaterthan2 = findViewById(R.id.greater2_approaching_trips);
        currentLocation = findViewById(R.id.currentLocation_approachingTrips);
        driversFloating = findViewById(R.id.drivers_approachingTrips);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPIApproachingTrips);
        mapFragment.getMapAsync(ApproachingTripsActivity.this);
        driversFloating.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#ffcdd8ec")));
        currentLocation.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#ffcdd8ec")));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
    }
}