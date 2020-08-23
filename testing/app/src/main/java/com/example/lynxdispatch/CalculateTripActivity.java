package com.example.lynxdispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class CalculateTripActivity extends AppCompatActivity {
    private TextView marqueeText, nodata_save;
    private Button newTripEstimatebtn, backButton;
    private MaterialCalendarView calendarView;
    private ListView listView;
    private List<String> name, contctNo, pickUpAddress, PickUpTime, date;
    private List<Integer> savetripId;
    private SingltenSaveTripListAdapter adp;
    SQLite_Helper_Save_Trip save_trip_in_sqlLite;
    private CalendarDay d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_trip);
        initialization();
        try {
            getDataFromSqlLite();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                d = date;
                getAllDataWithDate();
            }
        });


    }

    public void getAllDataWithDate() {
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        Cursor res = save_trip_in_sqlLite.getDataWithDate(myDateFormat.format(d.getDate()));
        if (res.getCount() == 0) {
            Toast.makeText(CalculateTripActivity.this, "No Saved Trip Found...", Toast.LENGTH_SHORT).show();
            name.clear();
            contctNo.clear();
            pickUpAddress.clear();
            PickUpTime.clear();
            savetripId.clear();
        } else {
            name.clear();
            contctNo.clear();
            pickUpAddress.clear();
            PickUpTime.clear();
            savetripId.clear();

            while (res.moveToNext()) {
                savetripId.add(res.getInt(0));
                StringTokenizer tk = new StringTokenizer(res.getString(4));
                String time = tk.nextToken();
                PickUpTime.add(time);
                name.add(res.getString(2));
                contctNo.add(res.getString(3));
                pickUpAddress.add(res.getString(5));
            }
        }
        adp = new SingltenSaveTripListAdapter(CalculateTripActivity.this, name, PickUpTime, pickUpAddress, contctNo);
        listView.setAdapter(adp);
        adp.notifyDataSetInvalidated();
    }


    private void getDataFromSqlLite() throws ParseException {
        Cursor res = save_trip_in_sqlLite.getAllData();

        if (res.getCount() == 0) {

        } else {
            name.clear();
            contctNo.clear();
            pickUpAddress.clear();
            PickUpTime.clear();
            date.clear();
            List<CalendarDay> events = new ArrayList<>();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            while (res.moveToNext()) {
                StringTokenizer tk = new StringTokenizer(res.getString(4));
                String date1 = tk.nextToken();
                String time = tk.nextToken();
                Date date = df.parse(date1);
                CalendarDay day = CalendarDay.from(date);
                events.add(day);
            }
            calendarView.addDecorator(new EventDecorator(Color.RED, events));
        }
    }

    private void initialization() {
        name = new ArrayList<>();
        contctNo = new ArrayList<>();
        pickUpAddress = new ArrayList<>();
        PickUpTime = new ArrayList<>();
        date = new ArrayList<>();
        savetripId = new ArrayList<>();
        save_trip_in_sqlLite = new SQLite_Helper_Save_Trip(CalculateTripActivity.this);
        listView = findViewById(R.id.listViewSave_Trip);
        calendarView = findViewById(R.id.calendarView);
        Date currentDate = Calendar.getInstance().getTime();
        calendarView.setSelectedDate(currentDate);
        d= CalendarDay.from(currentDate);
        getAllDataWithDate();
        marqueeText = findViewById(R.id.text);
        newTripEstimatebtn = findViewById(R.id.newTripEstimatebtn);
        backButton = findViewById(R.id.backButton_calculater);

    }

    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, List<CalendarDay> dates) {
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

    @Override
    protected void onResume() {
        super.onResume();
        try {
            getDataFromSqlLite();
            if (d == null) {

            } else {
                getAllDataWithDate();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    protected void onPause() {
        super.onPause();


    }
}