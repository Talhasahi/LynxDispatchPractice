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


    private TextView todayStatus_t;
    private LinearLayout todayStatus_b, activeTrips_b, enroutedropoff, enroutepickup, notstarted,
            fleet_b, arrivedatpickup, unnassigned,
            completed, pending,cancelled,attested,no_show;
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
                intent = new Intent(DashboardDispatcherActivity.this, TripStatusActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
        enroutedropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, TripsInProgressActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        enroutepickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, MarkedReadyActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        notstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, B_Legs_Activity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
        arrivedatpickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, ApproachingTripsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        unnassigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, FutureTrips_Activity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, PastTripsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, PrivatePayActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, CancelledTripsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        attested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, AttestedTripsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        no_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardDispatcherActivity.this, No_ShowActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    private void initialization() {

        todayStatus_t = findViewById(R.id.TripsStatus_Dispatcher);

        todayStatus_b = findViewById(R.id.linearLayout);
        activeTrips_b = findViewById(R.id.linearLayout4);
        enroutedropoff = findViewById(R.id.linearLayout5);
        enroutepickup = findViewById(R.id.linearLayout6);
        notstarted = findViewById(R.id.linearLayout7);
        fleet_b = findViewById(R.id.linearLayout8);
        arrivedatpickup = findViewById(R.id.linearLayout9);
        unnassigned = findViewById(R.id.linearLayout10);
        completed = findViewById(R.id.linearLayout11);
        pending = findViewById(R.id.linearLayout12);
        cancelled = findViewById(R.id.linearLayout15);
        attested = findViewById(R.id.linearLayout14);
        no_show = findViewById(R.id.linearLayout13);

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