package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class addNewVehicleActivity extends AppCompatActivity {

    private AutoCompleteTextView spinner_yearModel, spinner_typeCar;
    private Button backButton, addNewVehicle;
    private TextInputEditText regNo, color, model, vin, vehicleMake, vehiclePlate, regDate, regExpDate;
    private String year, type, regDate_s, regExpDate_s;
    private DatePickerDialog picker;

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


        regDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(addNewVehicleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (monthOfYear >= 10) {
                                    regDate_s = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                } else {
                                    regDate_s = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }
                                regDate.setText(regDate_s);
                            }
                        }, year, month, day);
                picker.show();
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
        if (year.equals("Select Year")) {
            Toast.makeText(this, "Year is not selected...", Toast.LENGTH_SHORT).show();
        } else if (type.equals("Select Type")) {
            Toast.makeText(this, "Type is not selected...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(regNo.getText().toString()) ||
                (regNo.getText().toString().length() < 5 || regNo.getText().toString().length() > 15)) {
            regNo.setError("Registeration no must be filled.(Between 5 to 15)");
        } else if (TextUtils.isEmpty(model.getText().toString())) {
            model.setError("Model must be filled.");
        } else if (TextUtils.isEmpty(color.getText().toString())) {
            color.setError("Color must be filled.");
        } else if (TextUtils.isEmpty(vin.getText().toString()) ||
                (vin.getText().toString().length() < 1 || vin.getText().toString().length() > 20)) {
            vin.setError("Vin must be filled. (Between  to 15)");
        } else if (TextUtils.isEmpty(vehicleMake.getText().toString())) {
            vehicleMake.setError("Vehicle Make must be filled.");
        } else if (TextUtils.isEmpty(vehiclePlate.getText().toString()) ||
                (vehiclePlate.getText().toString().length() < 1 || vehiclePlate.getText().toString().length() > 15)) {
            vehiclePlate.setError("Vehicle Plate must be filled. (Between 1 to 15)");
        } else {
            Toast.makeText(this, "New Car Added", Toast.LENGTH_SHORT).show();
        }
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
        regDate = findViewById(R.id.registrationDate_car);
        regExpDate = findViewById(R.id.registrationExpDate_car);

        String[] brokers = new String[]{"2020"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(addNewVehicleActivity.this,
                R.layout.list_item,
                brokers);
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