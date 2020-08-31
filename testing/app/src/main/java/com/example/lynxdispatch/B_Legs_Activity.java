package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class B_Legs_Activity extends AppCompatActivity {

    private Button backButton;
    private RadioButton remaining, assigned, finished;
    private TextView currentDate, tripsCount;
    private ListView listView;
    private singlten_trip_status_class adp;
    private List<String> l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11;
    private int flagTripsStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b__legs_);

        inialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        remaining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTripsStatus = 1;
                tripsCount.setText("( " + 61 + "/" + 72 + " )");
                adp = new singlten_trip_status_class(B_Legs_Activity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, flagTripsStatus);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });

        assigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTripsStatus = 2;
                tripsCount.setText("( " + 6 + "/" + 72 + " )");
                adp = new singlten_trip_status_class(B_Legs_Activity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, flagTripsStatus);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTripsStatus = 3;
                tripsCount.setText("( " + 5 + "/" + 72 + " )");
                adp = new singlten_trip_status_class(B_Legs_Activity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, flagTripsStatus);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });

    }

    private void inialization() {
        backButton = findViewById(R.id.backButton_b_legs);
        remaining = findViewById(R.id.remaining_b_legs);
        assigned = findViewById(R.id.assigned_b_legs);
        finished = findViewById(R.id.finished_b_legs);
        currentDate = findViewById(R.id.b_legs_currentDate);
        tripsCount = findViewById(R.id.b_legs_tripsCount);
        listView = findViewById(R.id.listview_b_legs);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate.setText(df.format(currentTime));
        tripsCount.setText("( " + 61 + "/" + 72 + " )");
        remaining.setText("Remaining (" + 61 + ")");
        assigned.setText("Assigned (" + 6 + ")");
        finished.setText("Finished (" + 5 + ")");
        flagTripsStatus = 1;

        l1 = new ArrayList<>();
        l2 = new ArrayList<>();
        l3 = new ArrayList<>();
        l4 = new ArrayList<>();
        l5 = new ArrayList<>();
        l6 = new ArrayList<>();
        l7 = new ArrayList<>();
        l8 = new ArrayList<>();
        l9 = new ArrayList<>();
        l10 = new ArrayList<>();
        l11 = new ArrayList<>();

        l1.add("Fahad Ali Mughal");
        l1.add("Fahad Ali");
        l1.add("Fahad");
        l1.add("Talha");

        l2.add("Fahad Ali Mughal");
        l2.add("Fahad Ali");
        l2.add("Fahad");
        l2.add("Talha");

        l3.add("Fahad Ali Mughal");
        l3.add("Fahad Ali");
        l3.add("Fahad");
        l3.add("Talha");

        l4.add("Fahad Ali Mughal");
        l4.add("Fahad Ali");
        l4.add("Fahad");
        l4.add("Talha");

        l5.add("Fahad Ali Mughal");
        l5.add("Fahad Ali");
        l5.add("Fahad");
        l5.add("Talha");

        l6.add("Fahad Ali Mughal");
        l6.add("Fahad Ali");
        l6.add("Fahad");
        l6.add("Talha");

        l7.add("Fahad Ali Mughal");
        l7.add("Fahad Ali");
        l7.add("Fahad");
        l7.add("Talha");

        l8.add("Fahad Ali Mughal");
        l8.add("Fahad Ali");
        l8.add("Fahad");
        l8.add("Talha");

        l9.add("Fahad Ali Mughal");
        l9.add("Fahad Ali");
        l9.add("Fahad");
        l9.add("Talha");

        l10.add("Started");
        l10.add("Offered");
        l10.add("Accepted");
        l10.add("Cancelled");

        l11.add("");
        l11.add("");
        l11.add("");
        l11.add("JustCancel");

        adp = new singlten_trip_status_class(B_Legs_Activity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, flagTripsStatus);
        listView.setAdapter(adp);
        adp.notifyDataSetInvalidated();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


}