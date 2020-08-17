package com.example.lynxdispatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FareParameters extends AppCompatActivity {
    private Button backButton,updateButton;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private EditText baseLocation,baseFare,perMinute,perMile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_parameters);

        initialization();

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
                currentField.setError("Do not leave empty");
                return false;
            }
        }
        return true;
    }
}