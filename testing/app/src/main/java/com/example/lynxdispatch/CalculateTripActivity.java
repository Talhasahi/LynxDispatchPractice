package com.example.lynxdispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Date;
import java.util.List;

public class CalculateTripActivity extends AppCompatActivity {
    private TextView marqueeText,nodata_save;
    private Button newTripEstimatebtn,backButton;
    private MaterialCalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_trip);
        initialization();
        marqueeText.setSelected(true);
        newTripEstimatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This Is Talha Comment
                //This Is Talha Comment 2
                //This s Talha Comment 3
                Intent intent = new Intent(CalculateTripActivity.this, NewTripEstimate.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //checking by fahad1
                //checking by fahad2
                //checking by fahad3
                //checking by fahad4
                Toast.makeText(CalculateTripActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
               Integer d = date.getDate().getDate();
               int m = date.getMonth();
               int y = date.getYear();
          
            }
        });

    }

    private void initialization() {
        calendarView = findViewById(R.id.calendarView);
        marqueeText = findViewById(R.id.text);
        newTripEstimatebtn = findViewById(R.id.newTripEstimatebtn);
        backButton = findViewById(R.id.backButton_calculater);
        nodata_save = findViewById(R.id.nodata_save);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}