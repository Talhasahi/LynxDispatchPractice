package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MarkedReadyActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;
    private Button backButton;
    private RadioButton listButton, mapButton;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marked_ready);

        inialization();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapFragment.getView().setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                mapFragment.getView().setVisibility(View.VISIBLE);
            }
        });


    }

    private void inialization() {
        backButton = findViewById(R.id.backButton_trip_marked_ready);
        listButton = findViewById(R.id.List_MarkedReady);
        mapButton = findViewById(R.id.Map_MarkedReady);
        listView = findViewById(R.id.listview_marked_ready);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPIMarkedReady);
        mapFragment.getMapAsync(MarkedReadyActivity.this);
        mapFragment.getView().setVisibility(View.GONE);
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