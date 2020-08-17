package com.example.lynxdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class createNewTripDispatcherActivity extends AppCompatActivity {

    private AutoCompleteTextView spinner_broker, spinner_vehicle;
    private Button backButton, createTrip;
    private TextInputEditText name, tripId, contactNo, picupAddress, dropoffAddress, pickupTime,
            dropoffTime, dispatcherNote;
    private String broker, vehicle;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private int PLACE_PICKER_REQUEST = 1;
    private int PLACE_PICKER_REQUEST2 = 2;
    private SwitchDateTimeDialogFragment dateTimeDialogFragment, dateTimeDialogFragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip_dispatcher);
        inialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTrip();
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
        dropoffTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeDialogFragment1.startAtCalendarView();
                dateTimeDialogFragment1.setDefaultDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 4, 6, 20).getTime());
                dateTimeDialogFragment1.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });


        picupAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(createNewTripDispatcherActivity.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        dropoffAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(createNewTripDispatcherActivity.this), PLACE_PICKER_REQUEST2);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String s=convertLatitudeLongitudetoAddress(place.getLatLng().latitude, place.getLatLng().longitude);
                picupAddress.setText(s);
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST2) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                StringBuilder stringBuilder = new StringBuilder();
//                String latitude = String.valueOf(place.getLatLng().latitude);
//                String longitude = String.valueOf(place.getLatLng().longitude);
//                stringBuilder.append("Latitude:");
//                stringBuilder.append(latitude);
//                stringBuilder.append("Longitude:");
//                stringBuilder.append(longitude);
                String s=convertLatitudeLongitudetoAddress(place.getLatLng().latitude, place.getLatLng().longitude);
                dropoffAddress.setText(s);
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

    private void createNewTrip() {
        broker = spinner_broker.getText().toString();
        vehicle = spinner_vehicle.getText().toString();
        if (broker.equals("Select Broker") ||
                vehicle.equals("Select Vehicle") ||
                TextUtils.isEmpty(name.getText().toString()) ||
                TextUtils.isEmpty(tripId.getText().toString()) ||
                TextUtils.isEmpty(contactNo.getText().toString()) ||
                TextUtils.isEmpty(pickupTime.getText().toString()) ||
                TextUtils.isEmpty(picupAddress.getText().toString()) ||
                TextUtils.isEmpty(dropoffAddress.getText().toString()) ||
                TextUtils.isEmpty(dispatcherNote.getText().toString())) {
            Toast.makeText(this, "Please Fill All Fields...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Trip Created", Toast.LENGTH_SHORT).show();
        }
    }//sending request for creating new trip

    private void inialization() {
        spinner_broker = findViewById(R.id.spinner_broker_Dispatcher);
        spinner_vehicle = findViewById(R.id.spinner_vehicle_Dispatcher);
        backButton = findViewById(R.id.backButton_go_create_new_trip_dispatcher);
        createTrip = findViewById(R.id.button_create_trip_dispatcher);
        name = findViewById(R.id.name_create_trip_dispatcher);
        tripId = findViewById(R.id.name_trip_id_dispatcher);
        contactNo = findViewById(R.id.name_contact_no_dispatcher);
        picupAddress = findViewById(R.id.name_pickup_address_dispatcher);
        dropoffAddress = findViewById(R.id.name_dropoff_address_dispatcher);
        pickupTime = findViewById(R.id.name_pickup_time_dispatcher);
        dropoffTime = findViewById(R.id.name_apt_time_dispatcher);
        dispatcherNote = findViewById(R.id.name_note_create_trip_dispatcher);


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


        String[] brokers = new String[]{"MAS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(createNewTripDispatcherActivity.this,
                R.layout.list_item,
                brokers);
        spinner_broker.setText("Select Broker");
        spinner_broker.setAdapter(adapter);

        String[] vehicles = new String[]{"Standard", "BLS Stretcher", "Premium", "SUV", "WAV"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(createNewTripDispatcherActivity.this,
                R.layout.list_item,
                vehicles);
        spinner_vehicle.setText("Select Vehicle");
        spinner_vehicle.setAdapter(adapter1);

    }
//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) activity.getSystemService(
//                        Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(
//                activity.getCurrentFocus().getWindowToken(), 0);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}