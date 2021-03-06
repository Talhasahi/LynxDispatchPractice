package com.example.lynxdispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.text.style.RelativeSizeSpan;

import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.SubscriptSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
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
    private TextView marqueeText;
    private Button newTripEstimatebtn, backButton;
    private MaterialCalendarView calendarView;
    private ListView listView;
    private List<String> name, contctNo, pickUpAddress, PickUpTime, date,checkActivity,dropOffAddress,baseTobase,noOffPasanger,Vehicle_List,Description_List,AppointmentDate,AppointmentTime;
    private List<Integer> savetripId;
    private SingltenSaveTripListAdapter adp;
    private SQLite_Helper_Save_Trip save_trip_in_sqlLite;
    private CalendarDay d;
    SpannableStringBuilder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_trip);
        initialization();
         builder = new SpannableStringBuilder("1");

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
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 if  (checkActivity.get(position).equals("urgentTrip")){
                     Intent intent = new Intent(CalculateTripActivity.this, UrgentTripDetail_Saved.class);
                     intent.putExtra("names", name.get(position));
                     intent.putExtra("PickUpTime_saved", PickUpTime.get(position));
                     intent.putExtra("address_saved", pickUpAddress.get(position));
                     intent.putExtra("contactNo_saved", contctNo.get(position));
                     intent.putExtra("date_saved", date.get(position));

                     startActivity(intent);
                     overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                 }
                 else {

                     Intent intent = new Intent(CalculateTripActivity.this, TripFareEstimateCalculate.class);

                     intent.putExtra("name_saved", name.get(position));
                     intent.putExtra("pasanger", Integer.parseInt(noOffPasanger.get(position)));
                     intent.putExtra("pickUp", pickUpAddress.get(position));
                     intent.putExtra("DropOff", dropOffAddress.get(position));
                     intent.putExtra("base_Location", baseTobase.get(position));
                     intent.putExtra("time", PickUpTime.get(position));
                     intent.putExtra("date", date.get(position));
                     intent.putExtra("AppointmentTime", AppointmentTime.get(position));
                     intent.putExtra("AppointmentDate", AppointmentDate.get(position));
                     intent.putExtra("Vehicle_List", Vehicle_List.get(position));
                     intent.putExtra("Description_List", Description_List.get(position));
                     intent.putExtra("contctNo", contctNo.get(position));
                     startActivity(intent);
                     overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                 }


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
            date.clear();
            checkActivity.clear();
            baseTobase.clear();
            dropOffAddress.clear();
            noOffPasanger.clear();
            Vehicle_List.clear();
            AppointmentDate.clear();
            Description_List.clear();
            AppointmentTime.clear();
            while (res.moveToNext()) {
                savetripId.add(res.getInt(0));
                StringTokenizer tk = new StringTokenizer(res.getString(4));
                String date1 = tk.nextToken();
                String time = tk.nextToken();
                PickUpTime.add(time);
                date.add(date1);
                name.add(res.getString(2));
                contctNo.add(res.getString(3));
                pickUpAddress.add(res.getString(5));
                checkActivity.add(res.getString(9));
                dropOffAddress.add(res.getString(6));
                noOffPasanger.add(res.getString(7));
                baseTobase.add(res.getString(8));
                Vehicle_List.add(res.getString(10));

                if (!"".equals(res.getString(12))){
                    StringTokenizer tk1 = new StringTokenizer(res.getString(12));
                    String date2 = tk1.nextToken();
                    String time1 = tk1.nextToken();
                    AppointmentTime.add(time1);
                    AppointmentDate.add(date2);
                }
                else {
                    AppointmentTime.add("");
                    AppointmentDate.add("");
                }

                Description_List.add(res.getString(11));
            }
        }
        adp = new SingltenSaveTripListAdapter(CalculateTripActivity.this, name, PickUpTime, pickUpAddress, contctNo,date,checkActivity,dropOffAddress,noOffPasanger,baseTobase,Description_List,AppointmentDate,AppointmentTime,Vehicle_List);
        listView.setAdapter(adp);
        adp.notifyDataSetInvalidated();
    }


    private void getDataFromSqlLite() throws ParseException {
        Cursor res = save_trip_in_sqlLite.getAllData();

        if (res.getCount() == 0) {

        } else {
            Vehicle_List.clear();
            AppointmentDate.clear();
            AppointmentTime.clear();
            Description_List.clear();
            name.clear();
            contctNo.clear();
            pickUpAddress.clear();
            PickUpTime.clear();
            date.clear();
            checkActivity.clear();
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
        Vehicle_List = new ArrayList<>();
        AppointmentDate = new ArrayList<>();
        Description_List = new ArrayList<>();
        AppointmentTime = new ArrayList<>();
        noOffPasanger = new ArrayList<>();
        baseTobase = new ArrayList<>();
        dropOffAddress = new ArrayList<>();
        checkActivity = new ArrayList<>();
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
        d = CalendarDay.from(currentDate);
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
          //  view.addSpan(builder);
            view.addSpan(builder);
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
