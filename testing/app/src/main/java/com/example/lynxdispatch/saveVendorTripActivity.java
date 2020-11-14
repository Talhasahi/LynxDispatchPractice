package com.example.lynxdispatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class saveVendorTripActivity extends AppCompatActivity {

    private AutoCompleteTextView spinner_vehicle, spinner_passenger;
    private Button backButton, createTrip;
    private TextInputEditText name, legId, contactNo, picupAddress, dropoffAddress, pickupTime,
            aptTime, mileage, customerRate, pickupDate;
    private String vehicle, pickupdate, no_of_passengers, pickuplatlang, dropofflatlang, currentDateTime;
    private int PLACE_PICKER_REQUEST = 1, PLACE_PICKER_REQUEST2 = 2, newCreatedTripID = 0;
    private DatePickerDialog picker;
    private TimePickerDialog timepicker;
    private SharedPreferences sharedpreferences;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_vendor_trip);

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
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                timepicker = new TimePickerDialog(saveVendorTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (((int) (Math.log10(minute) + 1) == 1)) {
                            pickupTime.setText(hourOfDay + ":0" + minute);
                        } else {
                            pickupTime.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, mHour, mMinute, false);
                timepicker.show();
            }
        });
        aptTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                timepicker = new TimePickerDialog(saveVendorTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (((int) (Math.log10(minute) + 1) == 1)) {
                            aptTime.setText(hourOfDay + ":0" + minute);
                        } else {
                            aptTime.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, mHour, mMinute, false);
                timepicker.show();
            }
        });
        pickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(saveVendorTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (((int) (Math.log10(dayOfMonth) + 1) == 1) && ((int) (Math.log10(monthOfYear + 1) + 1) == 1)) {
                                    pickupdate = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                } else if (((int) (Math.log10(monthOfYear + 1) + 1) == 1)) {
                                    pickupdate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                } else if (((int) (Math.log10(dayOfMonth) + 1) == 1)) {
                                    pickupdate = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                } else {
                                    pickupdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }
                                pickupDate.setText(pickupdate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        picupAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(saveVendorTripActivity.this), PLACE_PICKER_REQUEST);

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
                    startActivityForResult(builder.build(saveVendorTripActivity.this), PLACE_PICKER_REQUEST2);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    private void createNewTrip() {
        no_of_passengers = spinner_passenger.getText().toString();
        vehicle = spinner_vehicle.getText().toString();
        if (TextUtils.isEmpty(no_of_passengers) ||
                TextUtils.isEmpty(vehicle) ||
                TextUtils.isEmpty(name.getText().toString()) ||
                TextUtils.isEmpty(legId.getText().toString()) ||
                TextUtils.isEmpty(contactNo.getText().toString()) ||
                TextUtils.isEmpty(pickupTime.getText().toString()) ||
                TextUtils.isEmpty(picupAddress.getText().toString()) ||
                TextUtils.isEmpty(dropoffAddress.getText().toString()) ||
                TextUtils.isEmpty(pickupDate.getText().toString()) ||
                TextUtils.isEmpty(pickupDate.getText().toString())) {
            Toast.makeText(this, "Please Fill All Fields...", Toast.LENGTH_SHORT).show();
        } else {
            callApiForAddNewTrip();
        }
    }//sending request for creating new trip


    private void callApiForAddNewTrip() {
        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.uX");
        currentDateTime = df.format(currentTime);
        String url = "https://lynxdispatch-api.herokuapp.com/api/saveTrip";

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("appointmentTime", aptTime.getText().toString());
        postParam.put("assignedDriver", "NULL");
        postParam.put("clientName", name.getText().toString());
        postParam.put("companyNote", "Ok");
        postParam.put("createdAt", currentDateTime);
        postParam.put("customerSpecialRate", customerRate.getText().toString());
        postParam.put("date", pickupDate.getText().toString());
        postParam.put("dropoffLocation", dropofflatlang);
        postParam.put("milage", mileage.getText().toString());
        postParam.put("passengers", no_of_passengers);
        postParam.put("phoneNo1", contactNo.getText().toString());
        postParam.put("phoneNo2", contactNo.getText().toString());
        postParam.put("pickupLocation", pickuplatlang);
        postParam.put("pickupTime", pickupTime.getText().toString());
        postParam.put("status", "UNASSIGNED");
        postParam.put("tripCreatorEmail", sharedpreferences.getString("UserEmail", ""));
        postParam.put("tripType", "EXTERNAL");
        postParam.put("vehicleType", vehicle);


        progressDialog.show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            Toast.makeText(saveVendorTripActivity.this, response.getJSONObject("body") + "", Toast.LENGTH_LONG).show();
                            newCreatedTripID = response.getJSONObject("body").getInt("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (newCreatedTripID > 0) {
                            Toast.makeText(saveVendorTripActivity.this, "Trip Created", Toast.LENGTH_SHORT).show();
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        } else {
                            Toast.makeText(saveVendorTripActivity.this, "Trip Not Created", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                checkInternetConnection(error);
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", sharedpreferences.getString("TokenType", "") + " " + sharedpreferences.getString("AccessToken", ""));

                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }

    private void checkInternetConnection(VolleyError error) {
        String message = null;
        String title = "Network Error";
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        AlertDialog.Builder b = new AlertDialog.Builder(saveVendorTripActivity.this);
        b.setTitle(title);
        b.setMessage(message);
        b.setPositiveButton("Wifi Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        b.show();
    }


    private void inialization() {
        spinner_passenger = findViewById(R.id.spinner_passengers_vendortrip_Dispatcher);
        spinner_vehicle = findViewById(R.id.spinner_vehicle_vendortrip_Dispatcher);
        backButton = findViewById(R.id.backButton_go_create_vendortrip_dispatcher);
        createTrip = findViewById(R.id.button__vendortrip_dispatcher);
        name = findViewById(R.id.name__vendortrip_dispatcher);
        legId = findViewById(R.id.trip_id_vendortrip_dispatcher);
        contactNo = findViewById(R.id.contact_no_vendortrip_dispatcher);
        picupAddress = findViewById(R.id.pickup_address_vendortrip_dispatcher);
        dropoffAddress = findViewById(R.id.dropoff_address_vendortrip_dispatcher);
        pickupTime = findViewById(R.id.pickup_time_vendortrip_dispatcher);
        aptTime = findViewById(R.id.apt_time_vendortrip_dispatcher);
        mileage = findViewById(R.id.Mileage_vendortrip_dispatcher);
        customerRate = findViewById(R.id.CustomerRate_vendortrip_dispatcher);
        pickupDate = findViewById(R.id.date_vendortrip_dispatcher);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("clientName"));
        contactNo.setText(intent.getStringExtra("clientPhone"));
        picupAddress.setText(intent.getStringExtra("clientPickupAddr"));
        dropoffAddress.setText(intent.getStringExtra("clientDropoffAddr"));
        mileage.setText(intent.getStringExtra("clientMilaege"));
        pickupDate.setText(intent.getStringExtra("clientPickupDate"));
        pickupTime.setText(intent.getStringExtra("clientPickupTime"));
        legId.setText(intent.getStringExtra("clientLegid"));
        pickuplatlang = intent.getStringExtra("clientPickupLatLang");
        dropofflatlang = intent.getStringExtra("clientDropoffLatLang");

        progressDialog = new ProgressDialog(saveVendorTripActivity.this);
        progressDialog.setMessage("Creating...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        String[] no_of_passenger = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(saveVendorTripActivity.this,
                R.layout.list_item,
                no_of_passenger);
        spinner_passenger.setAdapter(adapter);

        String[] vehicles = new String[]{"Standard", "Premium", "SUV", "WAV", "BLS Stretcher"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(saveVendorTripActivity.this,
                R.layout.list_item,
                vehicles);
        spinner_vehicle.setAdapter(adapter1);

    }
}