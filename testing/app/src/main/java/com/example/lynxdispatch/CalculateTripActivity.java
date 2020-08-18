package com.example.lynxdispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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
                Toast.makeText(CalculateTripActivity.this, date.toString(), Toast.LENGTH_SHORT).show();

               Integer d = date.getDate().getDate();
               int m = date.getMonth();
               int y = date.getYear();
            }
        });

        List<CalendarDay> events = new ArrayList<>();
        CalendarDay day = CalendarDay.from(2020,07,7);
        CalendarDay day1 = CalendarDay.from(2020,07,8);
        CalendarDay day2 = CalendarDay.from(2020,07,9);
        CalendarDay day3 = CalendarDay.from(2020,07,10);
        events.add(day);
        events.add(day1);
        events.add(day2);
        events.add(day3);
        calendarView.addDecorator(new EventDecorator(Color.RED, events));

    }

    private void initialization() {
        calendarView = findViewById(R.id.calendarView);
        marqueeText = findViewById(R.id.text);
        newTripEstimatebtn = findViewById(R.id.newTripEstimatebtn);
        backButton = findViewById(R.id.backButton_calculater);
        nodata_save = findViewById(R.id.nodata_save);
    }
    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}