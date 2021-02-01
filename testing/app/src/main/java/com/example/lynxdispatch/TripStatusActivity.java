package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TripStatusActivity extends AppCompatActivity {

    private Button backButton;
    private RadioButton remaining, assigned, finished;
    private TextView currentDate, tripsCount;
    private ListView listView;
    private singlten_trip_status_class adp;
    private List<Integer> l1;
    private List<String> l2, l3, l4, l5, l6, l7, l8, l9, l10, l11;
    private int flagTripsStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_status);

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
                tripsCount.setText("( " + 85 + "/" + 158 + " )");
                adp = new singlten_trip_status_class(TripStatusActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });

        assigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTripsStatus = 2;
                tripsCount.setText("( " + 26 + "/" + 158 + " )");
                adp = new singlten_trip_status_class(TripStatusActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTripsStatus = 3;
                tripsCount.setText("( " + 47 + "/" + 158 + " )");
                adp = new singlten_trip_status_class(TripStatusActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10);
                listView.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TripStatusActivity.this, TripDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    private void inialization() {
        backButton = findViewById(R.id.backButton_trip_status);
        remaining = findViewById(R.id.remaining_tripStatus);
        assigned = findViewById(R.id.assigned_tripStatus);
        finished = findViewById(R.id.finished_tripStatus);
        currentDate = findViewById(R.id.Trip_status_currentDate);
        tripsCount = findViewById(R.id.Trip_status_tripsCount);
        listView = findViewById(R.id.listview_tripStatus);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate.setText(df.format(currentTime));
        tripsCount.setText("( " + 85 + "/" + 158 + " )");
        remaining.setText("Remaining (" + 85 + ")");
        assigned.setText("Assigned (" + 26 + ")");
        finished.setText("Finished (" + 47 + ")");
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


        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(4);

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

        adp = new singlten_trip_status_class(TripStatusActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10);
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