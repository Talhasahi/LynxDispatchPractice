package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardDispatcherActivity extends AppCompatActivity {


    private TextView todayStatus_t, activeTrips_t, tripsinProgress_t, markedReady_t, bLegs_t, fleet_t, approachingTrips_t, futureTrips_t,
            past_Trips_t, privateTrips_t;
    private LinearLayout todayStatus_b, activeTrips_b, tripsinProgress_b, markedReady_b, bLegs_b, fleet_b, approachingTrips_b, futureTrips_b,
            past_Trips_b, privateTrips_b;
    private Button backButton, CreateTrip;
    private Intent intent;
    private ProgressBar progressBar_TodayStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_dispatcher);


        initialization();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }); //backButton

        CreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, createNewTripDispatcherActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }); // create new trip activity

        todayStatus_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDispatcherActivity.this, "Today Status", Toast.LENGTH_SHORT).show();
            }
        });
        activeTrips_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, ActiveDriversActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
        tripsinProgress_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDispatcherActivity.this, "Trips inProgress Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        markedReady_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDispatcherActivity.this, "Marked Ready", Toast.LENGTH_SHORT).show();
            }
        });
        bLegs_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDispatcherActivity.this, "B Legs", Toast.LENGTH_SHORT).show();
            }
        });
        fleet_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, FleetActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        approachingTrips_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDispatcherActivity.this, "Approaching Trips", Toast.LENGTH_SHORT).show();
            }
        });
        futureTrips_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDispatcherActivity.this, "Future Trips", Toast.LENGTH_SHORT).show();
            }
        });
        past_Trips_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDispatcherActivity.this, "Past Trips", Toast.LENGTH_SHORT).show();
            }
        });
        privateTrips_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDispatcherActivity.this, "Private Trips", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initialization() {

        todayStatus_t = findViewById(R.id.TripsStatus_Dispatcher);
        activeTrips_t = findViewById(R.id.linearLayout4_text);
        tripsinProgress_t = findViewById(R.id.linearLayout5_text);
        markedReady_t = findViewById(R.id.linearLayout6_text);
        bLegs_t = findViewById(R.id.linearLayout7_text);
        fleet_t = findViewById(R.id.linearLayout8_text);
        approachingTrips_t = findViewById(R.id.linearLayout9_text);
        futureTrips_t = findViewById(R.id.linearLayout10_text);
        past_Trips_t = findViewById(R.id.linearLayout11_text);
        privateTrips_t = findViewById(R.id.linearLayout12_text);
        todayStatus_b = findViewById(R.id.linearLayout);
        activeTrips_b = findViewById(R.id.linearLayout4);
        tripsinProgress_b = findViewById(R.id.linearLayout5);
        markedReady_b = findViewById(R.id.linearLayout6);
        bLegs_b = findViewById(R.id.linearLayout7);
        fleet_b = findViewById(R.id.linearLayout8);
        approachingTrips_b = findViewById(R.id.linearLayout9);
        futureTrips_b = findViewById(R.id.linearLayout10);
        past_Trips_b = findViewById(R.id.linearLayout11);
        privateTrips_b = findViewById(R.id.linearLayout12);
        backButton = findViewById(R.id.backButton_go_to_dashboard_dispatcher);
        CreateTrip = findViewById(R.id.button_dispatcher_create_trip);
        progressBar_TodayStatus = findViewById(R.id.progressBar_TodayStatus);

        progressBar_TodayStatus.setMax(161); //temp
        progressBar_TodayStatus.setProgress(25); //temp value


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}