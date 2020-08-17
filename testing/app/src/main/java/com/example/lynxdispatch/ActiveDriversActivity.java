package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ActiveDriversActivity extends AppCompatActivity {

    private Button backButton;
    private ListView listView;
    private singlten_active_drivers adp;
    private List<String> n, n1, n2, n3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_drivers);

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
        backButton = findViewById(R.id.backButton_ActiveDriverDispatcher);
        listView = findViewById(R.id.listview_activedriversDispatcher);

        n = new ArrayList<>();
        n1 = new ArrayList<>();
        n2 = new ArrayList<>();
        n3 = new ArrayList<>();

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

        n2.add("Fahad Ali Mughal");
        n2.add("Fahad Ali");
        n2.add("Fahad");
        n2.add("Talha");
        n2.add("Ali Mughal");
        n2.add("Ali");
        n2.add("Mughal");
        n2.add("Fahad Mughal");
        n2.add("Talha Sahi");

        n3.add("Fahad Ali Mughal");
        n3.add("Fahad Ali");
        n3.add("Fahad");
        n3.add("Talha");
        n3.add("Ali Mughal");
        n3.add("Ali");
        n3.add("Mughal");
        n3.add("Fahad Mughal");
        n3.add("Talha Sahi");

        adp = new singlten_active_drivers(ActiveDriversActivity.this, n, n1, n2, n3);
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