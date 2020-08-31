package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalTripDetailSaved extends AppCompatActivity {
   Button backButton;
   TextView name,dates,times,pickUpaddress,dropOffAddress,Vehical,appointmentTimes,appointmentDates,number,detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trip_detail_saved);
        initialization();
        getdataFromCalculateTrip();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    private void initialization() {
        dates = findViewById(R.id.Date_saved);
        times = findViewById(R.id.times_personal_saved);
        backButton = findViewById(R.id.backButton_save_personal_trip_saved);
        name = findViewById(R.id.name_personal_save);
        pickUpaddress = findViewById(R.id.address_saved);
        dropOffAddress = findViewById(R.id.drop_Off_Address);
        Vehical = findViewById(R.id.vehicle);
        appointmentTimes = findViewById(R.id.apointment_time);
        appointmentDates = findViewById(R.id.appointmentDate);
        Vehical = findViewById(R.id.vehicle);
       number = findViewById(R.id.contact_No_saved);
        detail = findViewById(R.id.discription);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
    private void getdataFromCalculateTrip() {
        Bundle b = getIntent().getExtras();
        String     name_saved = b.getString("name_saved", null);
        String     PickUpTime = b.getString("PickUpTime", null);
        String    No_Of_pasanger = b.getString("No_Of_pasanger", null);
        String    pickUp = b.getString("pickUp", null);
        String    DropOff = b.getString("DropOff", null);
        String     base_Location = b.getString("base_Location", null);
        String    date = b.getString("date", null);
        String    contctNo = b.getString("contctNo", null);
        String     AppointmentTime = b.getString("AppointmentTime", null);
        String    AppointmentDate = b.getString("AppointmentDate", null);
        String    Vehicle_List = b.getString("Vehicle_List", null);
        String    Description_List = b.getString("Description_List", null);
        dates.setText(date);
        name.setText(name_saved);
        pickUpaddress.setText(pickUp);
        dropOffAddress.setText(DropOff);
        Vehical.setText(Vehicle_List+", LM: "+"0.97"+" Miles,"+" BTB: "+"3.23"+" Miles");
        detail.setText(Description_List);
        number.setText(contctNo);

        times.setText(PickUpTime);

        if ("".equals(AppointmentDate)){

            appointmentTimes.setText("N/A");
            appointmentDates.setText("Appointment :");
        }
        else {
            appointmentTimes.setText(AppointmentTime);
            appointmentDates.setText(AppointmentDate);
        }

    }
}