package com.example.lynxdispatch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class DispatchHomeActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private Intent intent;
    private DrawerLayout drawerLayout;
    private boolean doubleBackToExitPressedOnce = false;
    private TextView username, car_model, car_no;
    private ImageView userprofile;
    private SharedPreferences sharedpreferences, sharedPreferences2;
    private SharedPreferences.Editor editor, editor2;
    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;
    private FloatingActionButton currentLocation, assistantButton, filterButton, phoneButton, calculatorButton;
    private GpsTracker gpsTracker;
    private int expire_bool, markReady_bool, approaching_bool, offline_bool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_home);

        initialization();

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsTracker = new GpsTracker(DispatchHomeActivity.this);
                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
                    LatLng addr = new LatLng(latitude, longitude);
                    mapAPI.clear();
                    mapAPI.addMarker(new MarkerOptions().position(addr).title("Current Location"));
                    mapAPI.moveCamera(CameraUpdateFactory.newLatLng(addr));
                    mapAPI.setMinZoomPreference(15.0f);
                } else {
                    gpsTracker.showSettingsAlert();
                }
            }
        });  //on map screen right side button...


        assistantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DispatchHomeActivity.this, DashboardDispatcherActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        }); //on map screen right side button...

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DispatchHomeActivity.this, ActiveDriversActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }); //on map screen right side button...
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DispatchHomeActivity.this);
                builder.setTitle("Filter Map Items");

                final TextView t1 = new TextView(DispatchHomeActivity.this);
                final TextView t2 = new TextView(DispatchHomeActivity.this);
                final TextView t3 = new TextView(DispatchHomeActivity.this);
                final TextView t4 = new TextView(DispatchHomeActivity.this);
                final TextView t5 = new TextView(DispatchHomeActivity.this);
                final TextView t6 = new TextView(DispatchHomeActivity.this);

                t1.setText("Trips");
                t1.setTextSize(17f);
                t1.setTypeface(t1.getTypeface(), Typeface.BOLD);
                t2.setText("Expired");
                t3.setText("Marked Ready");
                t4.setText("Approaching");
                t5.setText("Drivers");
                t5.setTextSize(17f);
                t5.setTypeface(t5.getTypeface(), Typeface.BOLD);
                t6.setText("Offline");

                final CheckBox c1 = new CheckBox(DispatchHomeActivity.this);
                final CheckBox c2 = new CheckBox(DispatchHomeActivity.this);
                final CheckBox c3 = new CheckBox(DispatchHomeActivity.this);
                final CheckBox c4 = new CheckBox(DispatchHomeActivity.this);

                expire_bool = sharedPreferences2.getInt("expire", 0);
                markReady_bool = sharedPreferences2.getInt("mark_ready", 0);
                approaching_bool = sharedPreferences2.getInt("approaching", 0);
                offline_bool = sharedPreferences2.getInt("offline", 0);
                if (expire_bool == 1) {
                    c1.setChecked(true);
                }
                if (markReady_bool == 1) {
                    c2.setChecked(true);
                }

                if (approaching_bool == 1) {
                    c3.setChecked(true);
                }

                if (offline_bool == 1) {
                    c4.setChecked(true);
                }


                final LinearLayout linearLayout = new LinearLayout(DispatchHomeActivity.this);
                final LinearLayout l1 = new LinearLayout(DispatchHomeActivity.this);
                final LinearLayout l2 = new LinearLayout(DispatchHomeActivity.this);
                final LinearLayout l3 = new LinearLayout(DispatchHomeActivity.this);
                final LinearLayout l4 = new LinearLayout(DispatchHomeActivity.this);
                final LinearLayout l5 = new LinearLayout(DispatchHomeActivity.this);
                final LinearLayout l6 = new LinearLayout(DispatchHomeActivity.this);

                linearLayout.setOrientation(LinearLayout.VERTICAL);
                l1.setOrientation(LinearLayout.HORIZONTAL);
                l3.setOrientation(LinearLayout.HORIZONTAL);
                l4.setOrientation(LinearLayout.HORIZONTAL);
                l5.setOrientation(LinearLayout.HORIZONTAL);
                l6.setOrientation(LinearLayout.HORIZONTAL);

                linearLayout.addView(l1);
                linearLayout.addView(l2);
                linearLayout.addView(l3);
                linearLayout.addView(l4);
                linearLayout.addView(l5);
                linearLayout.addView(l6);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;


                l1.addView(t1);
                l1.setGravity(Gravity.START);
                l1.setBackgroundColor(Color.GRAY);
                l1.setPadding(30, 30, 30, 30);

                l2.addView(t2);
                l2.addView(c1);
                t2.setMinWidth((int) (width / 1.5));
                l2.setPadding(30, 30, 30, 30);


                l3.addView(t3);
                t3.setMinWidth((int) (width / 1.5));
                l3.addView(c2);
                l3.setPadding(30, 30, 30, 30);

                l4.addView(t4);
                t4.setMinWidth((int) (width / 1.5));
                l4.addView(c3);
                l4.setPadding(30, 30, 30, 30);

                l5.addView(t5);
                l5.setGravity(Gravity.LEFT);
                l5.setBackgroundColor(Color.GRAY);
                l5.setPadding(30, 30, 30, 30);

                l6.addView(t6);
                t6.setMinWidth((int) (width / 1.5));
                l6.addView(c4);
                l6.setPadding(30, 30, 30, 30);


                builder.setView(linearLayout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (c1.isChecked()) {
                            expire_bool = 1;
                        } else {
                            expire_bool = 0;
                        }
                        if (c2.isChecked()) {
                            markReady_bool = 1;
                        } else {
                            markReady_bool = 0;
                        }
                        if (c3.isChecked()) {
                            approaching_bool = 1;
                        } else {
                            approaching_bool = 0;
                        }
                        if (c4.isChecked()) {
                            offline_bool = 1;
                        } else {
                            offline_bool = 0;
                        }

                        editor2.putInt("expire", expire_bool);
                        editor2.putInt("mark_ready", markReady_bool);
                        editor2.putInt("approaching", approaching_bool);
                        editor2.putInt("offline", offline_bool);
                        editor2.apply();

                        Toast.makeText(DispatchHomeActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();


            }
        }); //on map screen right side button...

        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DispatchHomeActivity.this, CalculateTripActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });//on map screen right side button...

    }

    private void initialization() {
        toolbar = findViewById(R.id.toolbar_dispatcher);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPIDispatcher);
        mapFragment.getMapAsync(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(DispatchHomeActivity.this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        View header_view = navigationView.getHeaderView(0);
        username = header_view.findViewById(R.id.nav_name_user);
        car_model = header_view.findViewById(R.id.nav_user_car);
        car_no = header_view.findViewById(R.id.nav_user_car_no);
        userprofile = header_view.findViewById(R.id.navProfile);
        currentLocation = findViewById(R.id.myLocationButtonDispatcher);
        assistantButton = findViewById(R.id.assistantDispatcher);
        filterButton = findViewById(R.id.filterDispatcher);
        phoneButton = findViewById(R.id.phoneCallDispatcher);
        calculatorButton = findViewById(R.id.calculatorDispatcher);
        navigationView.setNavigationItemSelectedListener(DispatchHomeActivity.this);

        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        editor = sharedpreferences.edit();
        sharedPreferences2 = getSharedPreferences("filter_data", MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();
        String fullname = sharedpreferences.getString("FullName", "");
        RequestOptions transcodeTypeRequestBuilder = new RequestOptions().error(R.mipmap.dispatch2);
        String imageUrl = sharedpreferences.getString("UserProfile", "");
        Glide.with(DispatchHomeActivity.this).load(imageUrl).apply(transcodeTypeRequestBuilder).into(userprofile);
        username.setText(fullname);
        car_model.setText("Toyota Prius");
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
                Intent intent = new Intent(DispatchHomeActivity.this, AssignedTripsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.notstarted:
                Intent intent1 = new Intent(DispatchHomeActivity.this, NotStartedTrips.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.noshowup:
                Intent intent2 = new Intent(DispatchHomeActivity.this, NoShowUp.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.compleated:
                Intent intent3 = new Intent(DispatchHomeActivity.this, CompletedTrips.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                drawerLayout.closeDrawer(Gravity.LEFT);
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
                intent = new Intent(DispatchHomeActivity.this, PaymentActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.dispatch_item9:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dispatch_item10:
                intent = new Intent(DispatchHomeActivity.this, LoginActivity.class);
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