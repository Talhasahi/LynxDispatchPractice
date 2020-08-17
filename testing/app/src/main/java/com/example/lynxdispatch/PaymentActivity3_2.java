package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class PaymentActivity3_2 extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    private String s, s1, s2, s3, s4, s5, s6, s7, s8;
    private Button backButton, saveButton;
    private TextInputEditText FirstName, LastName, City, ZipCode, State;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_activity3_2);

        inialization();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveinfo();
            }
        });
    }

    private void saveinfo() {
        s4 = FirstName.getText().toString();
        s5 = LastName.getText().toString();
        s6 = City.getText().toString().trim();
        s7 = ZipCode.getText().toString().trim();
        s8 = State.getText().toString().trim();
        if (!s4.equals("")) {
            if (!s5.equals("")) {
                if (!s6.equals("")) {
                    if (s6.matches("([a-zA-Z]+|[a-zA-Z]+\\\\s[a-zA-Z]+)")) {
                        if (!s7.equals("")) {
                            if (s7.matches("^[0-9]{5}(?:-[0-9]{4})?$")) {
                                if (!s8.equals("")) {
                                    callApi();
                                } else {
                                    State.setError("Please fill state");
                                }
                            } else {
                                ZipCode.setError("Invalid zip code");
                            }
                        } else {
                            ZipCode.setError("Please fill zip code");
                        }
                    } else {
                        City.setError("Invalid city name");
                    }
                } else {
                    City.setError("Please fill city");
                }
            } else {
                LastName.setError("Please fill last name");
            }
        } else {
            FirstName.setError("Please fill first name");
        }

    }

    private void callApi() {
        Toast.makeText(this, "Dummy Saved!!!\n" + s4 + "\n" + s5 + "\n" + s6 + "\n" + s7 + "\n" + s8 + "\n", Toast.LENGTH_LONG).show();

        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);

        int loginflag = sharedpreferences.getInt("flagLogin", 0);

        if (loginflag == 1) {
            Intent intent = new Intent(PaymentActivity3_2.this, DriverHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else if (loginflag == 2) {
            Intent intent = new Intent(PaymentActivity3_2.this, DispatchHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

    }

    private void inialization() {
        Intent intent = getIntent();
        s = intent.getStringExtra("Person_name");
        s1 = intent.getStringExtra("Card_no");
        s2 = intent.getStringExtra("Expiry_date");
        s3 = intent.getStringExtra("Security_code");

        Toast.makeText(this, s + "\n" + s1 + "\n" + s2 + "\n" + s3 + "\n", Toast.LENGTH_LONG).show();

        backButton = findViewById(R.id.backButton_go_to_payment3_2);
        saveButton = findViewById(R.id.save_button_payment3_2);
        FirstName = findViewById(R.id.first_Name_payment3_2);
        LastName = findViewById(R.id.last_Name_payment3_2);
        City = findViewById(R.id.city_payment3_2);
        ZipCode = findViewById(R.id.zip_code_payment3_2);
        State = findViewById(R.id.state_payment3_2);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}