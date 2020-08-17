package com.example.lynxdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import me.himanshusoni.quantityview.QuantityView;

public class NewTripEstimate extends AppCompatActivity {
    private int PLACE_PICKER_REQUEST = 1;
    private int PLACE_PICKER_REQUEST2 = 2;
    private int PLACE_PICKER_REQUEST3= 3;

    private ImageView fareParameter;
    private Button backButton,createUrgentTrip,calculate;
    private TextInputEditText baseLocation,pickUpAddress,dropoFFAddress;
    private  TextView baseFareValue,perMileValue,perMinVale;
    private  QuantityView quantityView;

    SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_estimate);
        initialization();

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int No_Of_pasanger = quantityView.getQuantity();
                String  pickUp  = pickUpAddress.getText().toString();
                String DropOff = dropoFFAddress.getText().toString();
                String base_Location = baseLocation.getText().toString();
                Intent intent = new Intent(NewTripEstimate.this,TripFareEstimateCalculate.class);
                intent.putExtra("No_Of_pasanger", No_Of_pasanger);
                intent.putExtra("pickUp", pickUp);
                intent.putExtra("DropOff", DropOff);
                intent.putExtra("base_Location", base_Location);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        baseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(   NewTripEstimate.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        pickUpAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(NewTripEstimate.this), PLACE_PICKER_REQUEST2);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        dropoFFAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(NewTripEstimate.this), PLACE_PICKER_REQUEST3);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });


        String     baselocation = prefs.getString("baselocation", null);
        String    basefare = prefs.getString("basefare", null);
        String    permnute = prefs.getString("permnute", null);
        String    permile = prefs.getString("permile", null);

        baseFareValue.setText(basefare);
        perMileValue.setText(permile);
        perMinVale.setText(permnute);
        baseLocation.setText(baselocation);

        fareParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewTripEstimate.this, FareParameters.class);
                startActivity(intent);
                finish();
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
        createUrgentTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean fieldsOK = validate(new EditText[]{baseLocation, pickUpAddress, dropoFFAddress});
                if (fieldsOK) {
                    String pickUpAddress_String = pickUpAddress.getText().toString();
                    Intent intent = new Intent(NewTripEstimate.this, UrgentTripDetail.class);
                    intent.putExtra("PickUp_Address", pickUpAddress_String);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                }
                else {
                }


            }
        });
    }


    private void initialization() {
        quantityView = findViewById(R.id.quantityView_default);
        calculate = findViewById(R.id.calculates);
        baseFareValue =findViewById(R.id.base_fare_Value);
        perMileValue = findViewById(R.id.per_mile_value);
        perMinVale = findViewById(R.id.per_minute_value);
        baseLocation = findViewById(R.id.base_location_newTrip);
        pickUpAddress = findViewById(R.id.pickUp_Time_new_Trip);
        dropoFFAddress = findViewById(R.id.dropoff_address_new_trip);
        createUrgentTrip = findViewById(R.id.create_urgent_Trips);
        backButton = findViewById(R.id.backButton_new_trip_estimate);
        fareParameter = findViewById(R.id.fare_parameter);
        prefs = this.getSharedPreferences("fare_parameter_data", Context.MODE_PRIVATE);
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
                baseLocation.setText(s);
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST2) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String s=convertLatitudeLongitudetoAddress(place.getLatLng().latitude, place.getLatLng().longitude);
                pickUpAddress.setText(s);
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST3) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String s=convertLatitudeLongitudetoAddress(place.getLatLng().latitude, place.getLatLng().longitude);
                dropoFFAddress.setText(s);
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