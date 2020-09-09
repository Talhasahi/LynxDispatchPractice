package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UrgentTripDetail_Saved extends AppCompatActivity {
     TextView name,address,time,contactNo;
     Button backButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_trip_detail__saved);

        initialization();
        getdataFromCalculateTrip();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    private void getdataFromCalculateTrip() {
        Bundle b = getIntent().getExtras();
        String     name_saved = b.getString("names", null);
        String    PickUpTime_saved = b.getString("PickUpTime_saved", null);
        String    address_saved = b.getString("address_saved", null);
        String    contactNo_saved = b.getString("contactNo_saved", null);
        String date = b.getString("date_saved", null);
        name.setText(name_saved);
        time.setText(date+" "+PickUpTime_saved);
        address.setText(address_saved);
        contactNo.setText(contactNo_saved);

    }

    private void initialization() {
        backButton = findViewById(R.id.backButton_urgentTripDetail_saved);
        name = findViewById(R.id.name_urgent_save);
        address = findViewById(R.id.address_saved);
        time = findViewById(R.id.time_urgent_saved);
        contactNo = findViewById(R.id.contact_No_saved);


    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}