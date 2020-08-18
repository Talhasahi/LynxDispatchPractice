package com.example.lynxdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FareParameters extends AppCompatActivity {
    private Button backButton,updateButton;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private TextInputEditText baseLocation,baseFare,perMinute,perMile;
    private int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_parameters);

        initialization();

        baseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(FareParameters.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FareParameters.this, NewTripEstimate.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  baselocation =  baseLocation.getText().toString();
                String  basefare = baseFare.getText().toString();
                String  permnute = perMinute.getText().toString();
                String  permile = perMile.getText().toString();
                boolean fieldsOK = validate(new EditText[]{baseLocation, baseFare, perMinute,perMile});
                if (fieldsOK) {
                    editor.putString("baselocation", baselocation);
                    editor.putString("basefare", basefare);
                    editor.putString("permnute", permnute);
                    editor.putString("permile", permile);
                    editor.apply();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(FareParameters.this);
                    builder1.setTitle("Passenger Informed");
                    builder1.setMessage("You Perferences are saved successfully.You can update these any time");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent intent = new Intent(FareParameters.this, NewTripEstimate.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{

                }

            }
        });

    }
    private void initialization() {
        backButton = findViewById(R.id.backButton_fareparameter);
        updateButton = findViewById(R.id.update_fareparameter);
        baseLocation = findViewById(R.id.base_location);
        baseFare = findViewById(R.id.base_fare);
        perMinute = findViewById(R.id.per_minute);
        perMile  = findViewById(R.id.perMile);
        //fOR  sharedpreferences
        sharedpreferences = getSharedPreferences("fare_parameter_data", MODE_PRIVATE);
        editor = sharedpreferences.edit();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FareParameters.this, NewTripEstimate.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                Toast.makeText(this, "Please Fill all Fields", Toast.LENGTH_SHORT).show();
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