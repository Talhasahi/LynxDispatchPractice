package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TripsInProgressActivity extends AppCompatActivity {

    private Button backButton;
    private ListView listView;
    private singlten_trip_status_class adp;
    private List<String> l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_in_progress);

        inialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    private void inialization() {
        backButton = findViewById(R.id.backButton_trip_in_progress);
        listView = findViewById(R.id.listview_tripsInProgress);

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
        l10.add("Started");
        l10.add("Onway");
        l10.add("Onway");

        l11.add("");
        l11.add("");
        l11.add("");
        l11.add("");

        adp = new singlten_trip_status_class(TripsInProgressActivity.this, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, 2);
        listView.setAdapter(adp);
        adp.notifyDataSetInvalidated();
    }
}