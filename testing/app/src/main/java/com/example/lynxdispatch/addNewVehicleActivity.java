package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class addNewVehicleActivity extends AppCompatActivity {

    private Button backButton, addNewVehicle;
    private TextInputEditText regNo, color, model, vin, setDetail, odometer;

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
    }

    private void addVehicle() {
        if (TextUtils.isEmpty(regNo.getText().toString())) {
            regNo.setError("Registeration no must be filled.");
        } else if (TextUtils.isEmpty(model.getText().toString())) {
            model.setError("Model must be filled.");
        } else if (TextUtils.isEmpty(color.getText().toString())) {
            color.setError("Color must be filled.");
        } else if (TextUtils.isEmpty(vin.getText().toString())) {
            vin.setError("Vin must be filled.");
        } else if (TextUtils.isEmpty(odometer.getText().toString())) {
            odometer.setError("Odometer must be filled.");
        } else {
            Toast.makeText(this, "New Car Added", Toast.LENGTH_SHORT).show();
        }
    }

    private void inialization() {
        backButton = findViewById(R.id.backButton_go_to_addNewVehicle);
        addNewVehicle = findViewById(R.id.addnewVehicle_button);
        regNo = findViewById(R.id.reg_no_car);
        color = findViewById(R.id.car_color);
        model = findViewById(R.id.model_car);
        vin = findViewById(R.id.vin_car);
        setDetail = findViewById(R.id.set_detailCAr);
        odometer = findViewById(R.id.odometer_car);
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