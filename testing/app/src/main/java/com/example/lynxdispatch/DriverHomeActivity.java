package com.example.lynxdispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class DriverHomeActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private boolean doubleBackToExitPressedOnce = false;
    private TextView username, car_model, car_no;
    private ImageView userprofile;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;
    private FloatingActionButton button;
    private GpsTracker gpsTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        inialization();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsTracker = new GpsTracker(DriverHomeActivity.this);
                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
                    LatLng Skt = new LatLng(latitude, longitude);
                    mapAPI.addMarker(new MarkerOptions().position(Skt).title("Skt"));
                    mapAPI.moveCamera(CameraUpdateFactory.newLatLng(Skt));
                    mapAPI.setMinZoomPreference(6.0f);
                } else {
                    gpsTracker.showSettingsAlert();
                }
            }
        });


    }

    private void inialization() {
        toolbar = findViewById(R.id.toolbar_driver);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPIDriver);
        mapFragment.getMapAsync(this);


        drawerLayout = findViewById(R.id.drawerLayout_driver);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(DriverHomeActivity.this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_driver);
        navigationView.setItemIconTintList(null);
        View header_view = navigationView.getHeaderView(0);
        username = header_view.findViewById(R.id.nav_name_user);
        car_model = header_view.findViewById(R.id.nav_user_car);
        car_no = header_view.findViewById(R.id.nav_user_car_no);
        userprofile = header_view.findViewById(R.id.navProfile);
        button = findViewById(R.id.myLocationButtonDriver);
        navigationView.setNavigationItemSelectedListener(DriverHomeActivity.this);

        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        editor = sharedpreferences.edit();
        RequestOptions transcodeTypeRequestBuilder = new RequestOptions().error(R.mipmap.dispatch2);
        String imageUrl = sharedpreferences.getString("UserProfile", "");
        Glide.with(DriverHomeActivity.this).load(imageUrl).apply(transcodeTypeRequestBuilder).into(userprofile);
        String fullname = sharedpreferences.getString("FullName", "");
        username.setText(fullname);
        car_model.setText("Toyota Prado");
        car_no.setText("LEH-5230");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.dispatch_item1:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.dispatch_item2:
                Toast.makeText(this, "Assigned Trips", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dispatch_item3:
                Toast.makeText(this, "Trip History", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dispatch_item4:
                Toast.makeText(this, "Earnings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dispatch_item5:
                Toast.makeText(this, "Helping Material", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dispatch_item6:
                Toast.makeText(this, "Vehicle Expense", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dispatch_item7:
                Toast.makeText(this, "Support", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dispatch_item8:
                drawerLayout.closeDrawer(Gravity.LEFT);
                intent = new Intent(DriverHomeActivity.this, PaymentActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.dispatch_item9:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dispatch_item10:
                intent = new Intent(DriverHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                editor.putInt("flagLogin", 0);
                editor.putString("FullName", "");
                editor.putInt("UserId", 0);
                editor.putInt("RoleId", 0);
                editor.putString("AccessToken", "");
                editor.putString("TokenType", "");
                editor.apply();
                break;
        }


        return false;
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        if (drawerLayout.isShown() == true) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            this.doubleBackToExitPressedOnce = false;
        }
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }//end of back pressed

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
    }
}