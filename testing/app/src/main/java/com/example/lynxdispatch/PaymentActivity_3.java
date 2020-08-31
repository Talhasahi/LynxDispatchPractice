package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.Calendar;

public class PaymentActivity_3 extends AppCompatActivity {

    private Button backButton, nextButton;
    private TextInputEditText NameCard, CardNo, ExpiryDate, SecurityCode;
    private DatePickerDialog picker;
    private String selectedExpirydate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_3);

        inialization();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPaymentPage();
            }
        });

        ExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(PaymentActivity_3.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if ((monthOfYear+1)<10){
                                    selectedExpirydate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }else{
                                    selectedExpirydate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                ExpiryDate.setText(selectedExpirydate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    private void nextPaymentPage() {
        String s = NameCard.getText().toString();
        String s1 = CardNo.getText().toString().trim();
        String s2 = ExpiryDate.getText().toString().trim();
        String s3 = SecurityCode.getText().toString().trim();


        if (TextUtils.isEmpty(s)) {
            NameCard.setError("Please fill card name");
        } else {
            if (s1.matches("[0-9]+") && s1.length() == 16 && !TextUtils.isEmpty(s1)) {

                if (s2.matches("\\d{4}/[01]\\d/[0-3]\\d") && !TextUtils.isEmpty(s2)) {

                    if (s3.matches("[0-9]+") && (s3.length() == 3 || s3.length() == 4) && !TextUtils.isEmpty(s3)) {
                        Intent intent = new Intent(PaymentActivity_3.this, PaymentActivity3_2.class);
                        intent.putExtra("Person_name", s);
                        intent.putExtra("Card_no", s1);
                        intent.putExtra("Expiry_date", s2);
                        intent.putExtra("Security_code", s3);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    } else {
                        Toast.makeText(this, "Input is only integers & 3 or 4 digits", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    ExpiryDate.setError("Date formate is not correct e.g (YYYY/MM/DD)");
                }

            } else {
                CardNo.setError("Input is only integers & 16 digits");
            }
        }


    }

    private void inialization() {
        NameCard = findViewById(R.id.name_payment3);
        CardNo = findViewById(R.id.card_number_payment3);
        ExpiryDate = findViewById(R.id.expiry_date_payment3);
        SecurityCode = findViewById(R.id.security_code_payment3);
        backButton = findViewById(R.id.backButton_go_to_payment3);
        nextButton = findViewById(R.id.next_button_payment3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}