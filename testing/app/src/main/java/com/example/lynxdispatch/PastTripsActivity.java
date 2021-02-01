package com.example.lynxdispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PastTripsActivity extends AppCompatActivity implements singlten_calender_view.OnCalendarItemListener {

    private Button backButton;
    private RadioButton remaining, assigned, finished;
    private ListView listView;
    private RecyclerView calendarView;
    private singlten_trip_status_class adp;
    private List<Integer> l1;
    private List<String> l2, l3, l4, l5, l6, l7, l8, l9, l10, l11;
    private int flagTripsStatus = 0;
    private singlten_calender_view adp1;
    private ArrayList<String> MonthList, YearList, DayList, No_Of_Trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_trips);

        inialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        remaining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTripsStatus = 1;
                adp = new singlten_trip_status_class(PastTripsActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });

        assigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTripsStatus = 2;
                adp = new singlten_trip_status_class(PastTripsActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTripsStatus = 3;
                adp = new singlten_trip_status_class(PastTripsActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });


    }

    private void inialization() {
        backButton = findViewById(R.id.backButton_past_trips);
        remaining = findViewById(R.id.remaining_past_trips);
        assigned = findViewById(R.id.assigned_past_trips);
        finished = findViewById(R.id.finished_past_trips);
        listView = findViewById(R.id.listview_past_trips);
        calendarView = findViewById(R.id.calendarView_pastTrips);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        remaining.setText("Remaining (" + 85 + ")");
        assigned.setText("Assigned (" + 26 + ")");
        finished.setText("Finished (" + 47 + ")");
        flagTripsStatus = 1;

        l1 = new ArrayList<>();
        l2 = new ArrayList<>();
        l3 = new ArrayList<>();
        l4 = new ArrayList<>();
        l5 = new ArrayList<>();
        l6 = new ArrayList<>();
        l7 = new ArrayList<>();
        l8 = new ArrayList<>();
        l9 = new ArrayList<>();
        l10 = new ArrayList<>();
        l11 = new ArrayList<>();
        MonthList = new ArrayList<>();
        YearList = new ArrayList<>();
        DayList = new ArrayList<>();
        No_Of_Trips = new ArrayList<>();

        MonthList.add("Sep.");
        MonthList.add("Sep.");
        MonthList.add("Sep.");
        MonthList.add("Sep.");
        MonthList.add("Sep.");
        MonthList.add("Sep.");
        MonthList.add("Sep.");


        YearList.add("2020");
        YearList.add("2020");
        YearList.add("2020");
        YearList.add("2020");
        YearList.add("2020");
        YearList.add("2020");
        YearList.add("2020");

        DayList.add("05");
        DayList.add("12");
        DayList.add("19");
        DayList.add("20");
        DayList.add("22");
        DayList.add("25");
        DayList.add("27");

        No_Of_Trips.add("2");
        No_Of_Trips.add("1");
        No_Of_Trips.add("5");
        No_Of_Trips.add("12");
        No_Of_Trips.add("5");
        No_Of_Trips.add("8");
        No_Of_Trips.add("9");

        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(4);

        l2.add("Fahad Ali Mughal");
        l2.add("Fahad Ali");
        l2.add("Fahad");
        l2.add("Talha");

        l3.add("Fahad Ali Mughal");
        l3.add("Fahad Ali");
        l3.add("Fahad");
        l3.add("Talha");

        l4.add("Fahad Ali Mughal");
        l4.add("Fahad Ali");
        l4.add("Fahad");
        l4.add("Talha");

        l5.add("Fahad Ali Mughal");
        l5.add("Fahad Ali");
        l5.add("Fahad");
        l5.add("Talha");

        l6.add("Fahad Ali Mughal");
        l6.add("Fahad Ali");
        l6.add("Fahad");
        l6.add("Talha");

        l7.add("Fahad Ali Mughal");
        l7.add("Fahad Ali");
        l7.add("Fahad");
        l7.add("Talha");

        l8.add("Fahad Ali Mughal");
        l8.add("Fahad Ali");
        l8.add("Fahad");
        l8.add("Talha");

        l9.add("Fahad Ali Mughal");
        l9.add("Fahad Ali");
        l9.add("Fahad");
        l9.add("Talha");

        l10.add("Started");
        l10.add("Offered");
        l10.add("Accepted");
        l10.add("Cancelled");

        l11.add("");
        l11.add("");
        l11.add("");
        l11.add("JustCancel");

        adp = new singlten_trip_status_class(PastTripsActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10);
        listView.setAdapter(adp);
        adp.notifyDataSetInvalidated();

        adp1 = new singlten_calender_view(PastTripsActivity.this, MonthList, DayList, YearList, No_Of_Trips, this);
        calendarView.setAdapter(adp1);
        calendarView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onCalenderClick(int position) {
        Toast.makeText(this, YearList.get(position) + "-" + MonthList.get(position) + "-" + DayList.get(position), Toast.LENGTH_SHORT).show();
    }

}