package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TripFareEstimateCalculate extends AppCompatActivity {

    TextInputEditText pickUpTime,PickUpTime_base_to_base;
    TextView es;
    TextInputLayout t1,t2;
    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6,ll7,ll8,ll9,ll10;
    private SingltenLoadedMiles  adp,adp2;
    Integer NoOfPasanger;
    String pickPickup,dropOff,baseLocation;
    Button backButton,loadedmile,baseTobase;
    private ListView listView,baseTobaseListView;
    private List<String> n, n1, n2, n3,n4,l1,l2,l3,l4,l5;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private SwitchDateTimeDialogFragment dateTimeDialogFragment,dateTimeDialogFragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_fare_estimate_calculate);
        getTextFromPriviousActivity();

        initialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        loadedmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickUpTime_base_to_base.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.VISIBLE);
                ll4.setVisibility(View.VISIBLE);
                baseTobaseListView.setVisibility(View.VISIBLE);
             t2.setVisibility(View.VISIBLE);
            }
        });
        baseTobase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setVisibility(View.VISIBLE);
                ll1.setVisibility(View.INVISIBLE);
                es.setVisibility(View.INVISIBLE);
                pickUpTime.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.INVISIBLE);
            }
        });
        pickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeDialogFragment.startAtCalendarView();
                dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 4, 6, 20).getTime());
                dateTimeDialogFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });
        PickUpTime_base_to_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeDialogFragment1.startAtCalendarView();
                dateTimeDialogFragment1.setDefaultDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 4, 6, 20).getTime());
                dateTimeDialogFragment1.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });



    }

    private void initialization() {
        t1 = findViewById(R.id.textInputLayout);
        t2 = findViewById(R.id.textInputLayout3);
        ll4 = findViewById(R.id.linearLayout3);
        ll3 = findViewById(R.id.linearLayout2);
        ll2 = findViewById(R.id.linearLayout);
        es = findViewById(R.id.textView11);
        ll1 = findViewById(R.id.linearLayout15);
        PickUpTime_base_to_base = findViewById(R.id.PickUpTime_base_to_base);
        pickUpTime = findViewById(R.id.pickup_time_trip_fare_Estimate);
        backButton = findViewById(R.id.backButton_trip_fare_estimate_calculate);
        loadedmile = findViewById(R.id.calm);
        baseTobase  = findViewById(R.id.base_to_base);
        listView = findViewById(R.id.listview_loaded_miles);
        baseTobaseListView = findViewById(R.id.listview_base_tobase);
        dateTimeDialogFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if (dateTimeDialogFragment == null) {
            dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel),
                    "Clean" // Optional
            );
        }
        dateTimeDialogFragment1 = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if (dateTimeDialogFragment1 == null) {
            dateTimeDialogFragment1 = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel),
                    "Clean" // Optional
            );
        }
        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                pickUpTime.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                pickUpTime.setText("");
            }
        });
        dateTimeDialogFragment1.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                PickUpTime_base_to_base.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                PickUpTime_base_to_base.setText("");
            }
        });
        l1 = new ArrayList<>();
        l2 =  new ArrayList<>();
        l3 =  new ArrayList<>();
        l4 =  new ArrayList<>();
        l5 =   new ArrayList<>();
        n = new ArrayList<>();
        n1 = new ArrayList<>();
        n2 = new ArrayList<>();
        n3 = new ArrayList<>();
        n4 = new ArrayList<>();

        l1.add("Leg A");
        l2.add(pickPickup);
        l3.add("1111.64 Miles");
        l4.add("10 Hr 49 Mins");
        l5.add(dropOff);


        n.add("Leg A");
        n.add("Leg B");
        n.add("Leg C");
        n1.add(baseLocation);
        n1.add(pickPickup);
        n1.add(dropOff);
        n2.add("1111.64 Miles");
        n2.add("1111.64 Miles");
        n2.add("1111.64 Miles");
        n3.add("10 Hr 49 Mins");
        n3.add("10 Hr 49 Mins");
        n3.add("10 Hr 49 Mins");
        n4.add(pickPickup);
        n4.add(dropOff);
        n4.add(baseLocation);
        adp2 = new SingltenLoadedMiles(TripFareEstimateCalculate.this,l1,l2,l3,l4,l5);
        adp = new SingltenLoadedMiles(TripFareEstimateCalculate.this, n, n1, n2, n3,n4);
        listView.setAdapter(adp2);
        baseTobaseListView.setAdapter(adp);
        adp.notifyDataSetInvalidated();
        adp2.notifyDataSetChanged();
    }

    private void getTextFromPriviousActivity() {
        Bundle b = getIntent().getExtras();
        NoOfPasanger = b.getInt("No_Of_pasanger");
        pickPickup = b.getString("pickUp");
        dropOff = b.getString("DropOff");
        baseLocation =  b.getString("base_Location");

    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}