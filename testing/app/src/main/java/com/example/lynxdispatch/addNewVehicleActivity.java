package com.example.lynxdispatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addNewVehicleActivity extends AppCompatActivity {

    private AutoCompleteTextView spinner_yearModel, spinner_typeCar;
    private Button backButton, addNewVehicle;
    private TextInputEditText regNo, color, model, vin, vehicleMake, vehiclePlate, regExpDate;
    private String year, type, regExpDate_s;
    private DatePickerDialog picker;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private int flagAddedVehicle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vehicle);
        inialization();

        addNewVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVehicle();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addNewVehicleActivity.this, FleetActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        regExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(addNewVehicleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (monthOfYear >= 10) {
                                    regExpDate_s = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                } else {
                                    regExpDate_s = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }
                                regExpDate.setText(regExpDate_s);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    private void addVehicle() {
        year = spinner_yearModel.getText().toString();
        type = spinner_typeCar.getText().toString();
        if (TextUtils.isEmpty(vehicleMake.getText().toString())) {
            vehicleMake.setError("Vehicle Make must be filled.");
        } else if (type.equals("Select Type")) {
            Toast.makeText(this, "Select Type of Vehicle", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(model.getText().toString())) {
            model.setError("Model must be filled.");
        } else if (year.equals("Select Year")) {
            Toast.makeText(this, "Select Year of Vehicle", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(regNo.getText().toString()) ||
                (regNo.getText().toString().length() < 5 || regNo.getText().toString().length() > 15)) {
            regNo.setError("Registeration no must be filled.(Between 5 to 15)");
        } else if (TextUtils.isEmpty(vehiclePlate.getText().toString()) ||
                (vehiclePlate.getText().toString().length() < 1 || vehiclePlate.getText().toString().length() > 15)) {
            vehiclePlate.setError("Vehicle Plate must be filled. (Between 1 to 15)");
        } else if (TextUtils.isEmpty(regExpDate_s)) {
            Toast.makeText(this, "Select Registration Expiry Date", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(vin.getText().toString()) ||
                (vin.getText().toString().length() < 1 || vin.getText().toString().length() > 20)) {
            vin.setError("Vin must be filled. (Between 1 to 20)");
        } else if (TextUtils.isEmpty(color.getText().toString())) {
            color.setError("Color must be filled.");
        } else {
            callApiForAddNewVehicle();
        }
    }

    private void callApiForAddNewVehicle() {

        String url = "https://lynxdispatch-api.herokuapp.com/api/vehicle";

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("make", vehicleMake.getText().toString().trim());
        postParam.put("type", type);
        postParam.put("model", model.getText().toString().trim());
        postParam.put("year", year);
        postParam.put("registration", regNo.getText().toString().trim());
        postParam.put("plate", vehiclePlate.getText().toString().trim());
        postParam.put("identifationNumber", vin.getText().toString().trim());
        postParam.put("color", color.getText().toString().trim());
        postParam.put("status", 0 + "");
        postParam.put("registrationExpDate", regExpDate_s);

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
                            flagAddedVehicle = response.getJSONObject("body").getInt("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (flagAddedVehicle > 0) {
                            Toast.makeText(addNewVehicleActivity.this, "New Vehicle Added Successfully", Toast.LENGTH_LONG).show();
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        } else {
                            Toast.makeText(addNewVehicleActivity.this, "New Vehicle Not Added Successfully", Toast.LENGTH_LONG).show();
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
                headers.put("Content-Type", "application/json; charset=utf-8");
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
        AlertDialog.Builder b = new AlertDialog.Builder(addNewVehicleActivity.this);
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
        backButton = findViewById(R.id.backButton_go_to_addNewVehicle);
        addNewVehicle = findViewById(R.id.addnewVehicle_button);
        regNo = findViewById(R.id.reg_no_car);
        color = findViewById(R.id.car_color);
        model = findViewById(R.id.modelvehicle_car);
        vin = findViewById(R.id.vin_car);
        spinner_yearModel = findViewById(R.id.yearvehicle_car);
        spinner_typeCar = findViewById(R.id.type_car);
        vehicleMake = findViewById(R.id.makeVehicle_car);
        vehiclePlate = findViewById(R.id.vehicle_PlateNo);
        regExpDate = findViewById(R.id.registrationExpDate_car);

        progressDialog = new ProgressDialog(addNewVehicleActivity.this);
        progressDialog.setMessage("Adding...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        List<String> years = new ArrayList<>();
        for (int i = 2022; i >= 1990; i--) {
            years.add(i + "");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(addNewVehicleActivity.this,
                R.layout.list_item,
                years);
        spinner_yearModel.setText("Select Year");
        spinner_yearModel.setAdapter(adapter);

        String[] vehicles = new String[]{"Standard", "BLS Stretcher", "Premium", "SUV", "WAV"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(addNewVehicleActivity.this,
                R.layout.list_item,
                vehicles);
        spinner_typeCar.setText("Select Type");
        spinner_typeCar.setAdapter(adapter1);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(addNewVehicleActivity.this, FleetActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}