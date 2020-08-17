package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FleetActivity extends AppCompatActivity {

    private Button backButton, add_new_vehicle;
    private ListView listView;
    private singlten_fleet_cars adp;
    private List<String> n, n1, n2;
    private TextView headings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet);

        inialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        add_new_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FleetActivity.this, addNewVehicleActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FleetActivity.this, ActiveDriversActivity.class);
                intent.putExtra("car_reg_no", n2.get(position));
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    private void inialization() {
        backButton = findViewById(R.id.backButton_FleetDispatcher);
        listView = findViewById(R.id.listview_fleetDispatcher);
        add_new_vehicle = findViewById(R.id.button_add_new_vehicle);
        headings = findViewById(R.id.textView10_fleet_dispatcher);

        n = new ArrayList<>();
        n1 = new ArrayList<>();
        n2 = new ArrayList<>();

        n.add("Fahad Ali Mughal");
        n.add("Fahad Ali");
        n.add("Fahad");
        n.add("Talha");
        n.add("Ali Mughal");
        n.add("Ali");
        n.add("Mughal");
        n.add("Fahad Mughal");
        n.add("Talha Sahi");

        n1.add("Fahad Ali Mughal");
        n1.add("Fahad Ali");
        n1.add("Fahad");
        n1.add("Talha");
        n1.add("Ali Mughal");
        n1.add("Ali");
        n1.add("Mughal");
        n1.add("Fahad Mughal");
        n1.add("Talha Sahi");

        n2.add("13216546578");
        n2.add("2134654");
        n2.add("32131351");
        n2.add("12334");
        n2.add("3216546");
        n2.add("123");
        n2.add("1324");
        n2.add("1231564");
        n2.add("4654897");


        adp = new singlten_fleet_cars(FleetActivity.this, n, n1, n2);
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