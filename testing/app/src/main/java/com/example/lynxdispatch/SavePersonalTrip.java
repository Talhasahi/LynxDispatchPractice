package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SavePersonalTrip extends AppCompatActivity {
    SQLite_Helper_Save_Trip save_trip_in_sqlLite;
    private AutoCompleteTextView  spinner_vehicle;
    private Button backButton, saveForLater,startTrip,makeTriplive;
    private TextInputEditText name,  contactNo,   pickupTime,
            dropoffTime, dispatcherNote;
    private SwitchDateTimeDialogFragment dateTimeDialogFragment, dateTimeDialogFragment1;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    Integer NoOfPasanger;
    String pickPickup,dropOff,baseLocation,pickupTimes;
    SharedPreferences prefs;
    Long    userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_personal_trip);
        getTextFromPriviousActivity();

        inialization();

        pickupTime.setText(pickupTimes);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        saveForLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePersonalTrip();
            }

            private void savePersonalTrip() {
                String spinner_vehicle_String = spinner_vehicle.getText().toString();
                String name_String = name.getText().toString();

                String contactNo_String = contactNo.getText().toString();
                String pickupTime_String = pickupTime.getText().toString();
                String dropoffTime_String = dropoffTime.getText().toString();
                String dispatcherNote_String = dispatcherNote.getText().toString();

                boolean fieldsOK = validate(new EditText[]{name, contactNo, pickupTime,dispatcherNote});
                if (fieldsOK &&  !spinner_vehicle_String.equals("Select Vehicle")) {
                    boolean b = save_trip_in_sqlLite.insertData(userId,name_String,contactNo_String,pickupTime_String,pickPickup,dropOff,NoOfPasanger,baseLocation,"savePersonalTrip",spinner_vehicle_String,dispatcherNote_String,dropoffTime_String);
                    if (b){
                        Cursor res =  save_trip_in_sqlLite.getAllData();
      while (res.moveToNext()){

          Toast.makeText(SavePersonalTrip.this, res.getString(8), Toast.LENGTH_SHORT).show();
          Toast.makeText(SavePersonalTrip.this, res.getString(7), Toast.LENGTH_SHORT).show();

     }

                        Intent newIntent = new Intent(SavePersonalTrip.this,CalculateTripActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(newIntent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                    }
                    else {
                        Toast.makeText(SavePersonalTrip.this, "Not Saved", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(SavePersonalTrip.this, "Please Select Vehicle", Toast.LENGTH_SHORT).show();
                }

            }
        });
        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeDialogFragment.startAtCalendarView();
                dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(2020, Calendar.AUGUST, 4, 6, 20).getTime());
                dateTimeDialogFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });
        dropoffTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeDialogFragment1.startAtCalendarView();
                dateTimeDialogFragment1.setDefaultDateTime(new GregorianCalendar(2020, Calendar.AUGUST, 4, 6, 20).getTime());
                dateTimeDialogFragment1.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });

    }

    private void getTextFromPriviousActivity() {
        Bundle b = getIntent().getExtras();
        pickupTimes = b.getString("pickupTime");
        pickPickup = b.getString("pickupAddress");
        dropOff = b.getString("dropOff");
        baseLocation = b.getString("baseLocation");
        NoOfPasanger = b.getInt("NoOfPasanger");
        Toast.makeText(SavePersonalTrip.this, String.valueOf(NoOfPasanger), Toast.LENGTH_SHORT).show();

    }

    private void inialization() {

        prefs = this.getSharedPreferences("login_data", MODE_PRIVATE);
        userId = prefs.getLong("UserId",0);

        //For Spinner
        spinner_vehicle = findViewById(R.id.spinner_vehicle_Dispatcher);
        String[] vehicles = new String[]{"Standard", "BLS Stretcher", "Premium", "SUV", "WAV"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(SavePersonalTrip.this,
                R.layout.list_item,
                vehicles);
        //For SQL
        save_trip_in_sqlLite  = new SQLite_Helper_Save_Trip(SavePersonalTrip.this);

        spinner_vehicle.setText("Select Vehicle");
        spinner_vehicle.setAdapter(adapter1);
        backButton = findViewById(R.id.backButton_save_personal_trip);
        saveForLater = findViewById(R.id.saveForLater);
        startTrip = findViewById(R.id.startTrip);
        makeTriplive = findViewById(R.id.make_Trip_live_1);
        name = findViewById(R.id.name_save_personal_trip);
        contactNo = findViewById(R.id.contact_No_save_persoal_trip);
        pickupTime = findViewById(R.id.name_pickup_time_save_personal_trip);
        dropoffTime = findViewById(R.id.name_apt_time_save_personal_trip);
        dispatcherNote = findViewById(R.id.name_note_save_personal_trip);

        // Construct SwitchDateTimePicker
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
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                pickupTime.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                pickupTime.setText("");
            }
        });
        dateTimeDialogFragment1.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                dropoffTime.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                dropoffTime.setText("");
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                Toast.makeText(this, "Please Fill all Fields", Toast.LENGTH_SHORT).show();
                return  false;
            }
        }
        return true;
    }
}