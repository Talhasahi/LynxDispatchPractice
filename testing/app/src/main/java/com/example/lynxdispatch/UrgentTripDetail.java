package com.example.lynxdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class UrgentTripDetail extends AppCompatActivity {
    Button saveTrip,makeTripLive,backButton;
    EditText pickupTime,PickUpAddress,name,contact_no;
    String pickUPAddressFromeNewTripEstimate;
    private int PLACE_PICKER_REQUEST = 1;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private SwitchDateTimeDialogFragment dateTimeDialogFragment, dateTimeDialogFragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_trip_detail);
        getTextFromPriviousActivity();
        initialization();
        PickUpAddress.setText(pickUPAddressFromeNewTripEstimate);
        PickUpAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(UrgentTripDetail.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeDialogFragment.startAtCalendarView();
                dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 4, 6, 20).getTime());
                dateTimeDialogFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });
        saveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  pickup_Time =  pickupTime.getText().toString();
                String  PickUp_Address = PickUpAddress.getText().toString();
                String  name_ = name.getText().toString();
                String  contacts_no = contact_no.getText().toString();
                boolean fieldsOK = validate(new EditText[]{pickupTime, PickUpAddress, name,contact_no});
                if (fieldsOK) {


                }
                else {
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    private void getTextFromPriviousActivity() {
        Bundle b = getIntent().getExtras();
        pickUPAddressFromeNewTripEstimate = b.getString("PickUp_Address");
    }

    private void initialization() {
        backButton = findViewById(R.id.backButton_urgentTripDetail);
        saveTrip = findViewById(R.id.save_trip);
        makeTripLive = findViewById(R.id.make_Trip_live);
        pickupTime = findViewById(R.id.pickup_time);
        PickUpAddress = findViewById(R.id.pickup_address);
        name = findViewById(R.id.name);
        contact_no  = findViewById(R.id.contact_No_urgentTrip);

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
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
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
                currentField.setError("Do not leave empty");
                return false;
            }
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String s=convertLatitudeLongitudetoAddress(place.getLatLng().latitude, place.getLatLng().longitude);
                PickUpAddress.setText(s);
            }
        }

    }
    private String convertLatitudeLongitudetoAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            Log.e("tag", "Unable connect to Geocoder", e);
        }
        return null;
    }
}