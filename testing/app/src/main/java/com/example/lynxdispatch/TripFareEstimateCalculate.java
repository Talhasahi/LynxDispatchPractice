package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    TextInputEditText pickUpTime;
    TextView es;
    TextInputLayout t1,t2;
    LinearLayout ll1,ll2,ll3,ll4;
    private SingltenLoadedMiles  adp,adp2;
    Integer NoOfPasanger;
    String pickPickup,dropOff,baseLocation,time,date,AppointmentTime,Vehicle_List,AppointmentDate,Description_List,name,contctNo;
    Button backButton,loadedmile,baseTobase,proceedFurther,viewDetail;
    private ListView listView,baseTobaseListView;
    private List<String> n, n1, n2, n3,n4,l1,l2,l3,l4,l5;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private SwitchDateTimeDialogFragment dateTimeDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_fare_estimate_calculate);
        getTextFromPriviousActivity();

        initialization();

        if ("".equals(date)){

        }
        else {
            pickUpTime.setText(date+" "+time);
            viewDetail.setVisibility(View.VISIBLE);
            proceedFurther.setVisibility(View.INVISIBLE);
        }
        viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripFareEstimateCalculate.this, PersonalTripDetailSaved.class);
                intent.putExtra("name_saved",name );
                intent.putExtra("contctNo",contctNo );
                intent.putExtra("No_Of_pasanger", NoOfPasanger);
                intent.putExtra("pickUp", pickPickup);
                intent.putExtra("DropOff", dropOff);
                intent.putExtra("base_Location", baseLocation);
                intent.putExtra("PickUpTime", time);
                intent.putExtra("date", date);
                intent.putExtra("AppointmentTime", AppointmentTime);
                intent.putExtra("AppointmentDate", AppointmentDate);
                intent.putExtra("Vehicle_List", Vehicle_List);
                intent.putExtra("Description_List", Description_List);

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
        loadedmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.GONE);
                ll4.setVisibility(View.GONE);
                baseTobaseListView.setVisibility(View.GONE);
                ll1.setVisibility(View.VISIBLE);
                es.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
            }
        });
        baseTobase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll1.setVisibility(View.GONE);
                es.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.VISIBLE);
                ll4.setVisibility(View.VISIBLE);
                baseTobaseListView.setVisibility(View.VISIBLE);
            }
        });
        pickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeDialogFragment.startAtCalendarView();
                dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(2020, Calendar.AUGUST, 4, 6, 20).getTime());
                dateTimeDialogFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });
        proceedFurther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean fieldsOK = validate(new EditText[]{pickUpTime});
                String pickUpTimes_String = pickUpTime.getText().toString();
                if (fieldsOK ) {
                    Intent intent = new Intent(TripFareEstimateCalculate.this, SavePersonalTrip.class);
                    intent.putExtra("pickupTime", pickUpTimes_String);
                    intent.putExtra("pickupAddress", pickPickup);
                    intent.putExtra("dropOff", dropOff);
                    intent.putExtra("baseLocation", baseLocation);
                    intent.putExtra("NoOfPasanger", NoOfPasanger);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                else {


                }

            }
        });

    }

    private void initialization() {
        viewDetail = findViewById(R.id.view_detail);
        proceedFurther = findViewById(R.id.prcceed_further);
        t1 = findViewById(R.id.textInputLayout);
        t2 = findViewById(R.id.textInputLayout3);
        ll4 = findViewById(R.id.linearLayout3);
        ll3 = findViewById(R.id.linearLayout2);
        ll2 = findViewById(R.id.linearLayout);
        es = findViewById(R.id.textView11);
        ll1 = findViewById(R.id.linearLayout15);
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

        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
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
        name = b.getString("name_saved");
        contctNo = b.getString("contctNo");
        NoOfPasanger = b.getInt("pasanger");
        pickPickup = b.getString("pickUp");
        dropOff = b.getString("DropOff");
        baseLocation =  b.getString("base_Location");
         time = b.getString("time");
         date =  b.getString("date");
        AppointmentTime = b.getString("AppointmentTime");
        AppointmentDate =  b.getString("AppointmentDate");
        Vehicle_List = b.getString("Vehicle_List");
        Description_List =  b.getString("Description_List");


    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                Toast.makeText(this, "Please set the Time", Toast.LENGTH_SHORT).show();
                return  false;
            }
        }
        return true;
    }
}