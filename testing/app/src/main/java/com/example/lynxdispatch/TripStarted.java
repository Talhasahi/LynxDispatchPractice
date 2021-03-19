package com.example.lynxdispatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripStarted extends AppCompatActivity implements OnMapReadyCallback {
    //This activity belongs to talha.
    String startingF1, startingF2;
    int tripId;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedpreferences;
    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;
    private Polyline polyline = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    MarkerOptions markerOptions1;
    Marker marker;
    LatLng location;
    LatLng location2;
    LatLng location3;
    LatLng location4;
    LatLng location5;
    LatLng location6;
    LatLng location7;
    Button endTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_started);
        inialization();
        getIntentData();
        setmap();
        location2 = new LatLng(33.61, 73.061);
        location3 = new LatLng(33.611, 73.061);
        location4 = new LatLng(33.6112, 73.0611);
        location5 = new LatLng(33.6113, 73.0611);
        location6 = new LatLng(33.6114, 73.0611);
        location7 = new LatLng(33.6115, 73.0611);
        location = new LatLng(Double.parseDouble(startingF1), Double.parseDouble(startingF2));
        latLngList.add(location);
        latLngList.add(location2);
        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTrips(tripId);
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (polyline != null) polyline.remove();
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions
                        .addAll(latLngList);
                polyline = mapAPI.addPolyline(polylineOptions);
                polyline.setColor(Color.rgb(235, 52, 79));
                polyline.setWidth(8);
                if (marker != null) {
                    marker.remove();
                    MarkerOptions markerOptions = new MarkerOptions().position(location2);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mya));
                    marker = mapAPI.addMarker(markerOptions);
                    mapAPI.moveCamera(CameraUpdateFactory.newLatLng(location2));
                    mapAPI.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                    latLngList.add(location3);

                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (polyline != null) polyline.remove();
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.addAll(latLngList);
                        polyline = mapAPI.addPolyline(polylineOptions);
                        polyline.setColor(Color.rgb(235, 52, 79));
                        polyline.setWidth(8);
                        marker.remove();
                        MarkerOptions markerOptions2 = new MarkerOptions().position(location3);
                        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.mya));
                        marker = mapAPI.addMarker(markerOptions2);
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(location3));
                        mapAPI.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                        latLngList.add(location4);
                    }
                }, 1000);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (polyline != null) polyline.remove();
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.addAll(latLngList);
                        polyline = mapAPI.addPolyline(polylineOptions);
                        polyline.setColor(Color.rgb(235, 52, 79));
                        polyline.setWidth(8);
                        marker.remove();
                        MarkerOptions markerOptions2 = new MarkerOptions().position(location4);
                        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.mya));
                        marker = mapAPI.addMarker(markerOptions2);
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(location4));
                        mapAPI.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                        latLngList.add(location5);
                    }
                }, 2000);

                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (polyline != null) polyline.remove();
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.addAll(latLngList);
                        polyline = mapAPI.addPolyline(polylineOptions);
                        polyline.setColor(Color.rgb(235, 52, 79));
                        polyline.setWidth(8);
                        marker.remove();
                        MarkerOptions markerOptions2 = new MarkerOptions().position(location5);
                        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.mya));
                        marker = mapAPI.addMarker(markerOptions2);
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(location5));
                        mapAPI.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                        latLngList.add(location6);

                    }
                }, 3000);
                Handler handler4 = new Handler();
                handler4.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (polyline != null) polyline.remove();
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.addAll(latLngList);
                        polyline = mapAPI.addPolyline(polylineOptions);
                        polyline.setColor(Color.rgb(235, 52, 79));
                        polyline.setWidth(8);
                        marker.remove();
                        MarkerOptions markerOptions2 = new MarkerOptions().position(location6);
                        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.mya));
                        marker = mapAPI.addMarker(markerOptions2);
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(location6));
                        mapAPI.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                        latLngList.add(location7);

                    }
                }, 4000);
                Handler handler5 = new Handler();
                handler5.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (polyline != null) polyline.remove();
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.addAll(latLngList);
                        polyline = mapAPI.addPolyline(polylineOptions);
                        polyline.setColor(Color.rgb(235, 52, 79));
                        polyline.setWidth(8);
                        marker.remove();
                        MarkerOptions markerOptions2 = new MarkerOptions().position(location7);
                        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.mya));
                        marker = mapAPI.addMarker(markerOptions2);
                        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(location7));
                        mapAPI.animateCamera(CameraUpdateFactory.zoomTo(18.0f));


                    }
                }, 5000);
            }
        }, 3000);

    }

    private void inialization() {
        progressDialog = new ProgressDialog(TripStarted.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        endTrip = findViewById(R.id.endtrip);
    }

    private void setmap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void getIntentData() {
        Intent intent = getIntent();
        startingF1 = intent.getStringExtra("F1");
        startingF2 = intent.getStringExtra("F2");
        tripId = intent.getIntExtra("Tripid", 0);
        Toast.makeText(this, String.valueOf(tripId), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
        markerOptions1 = new MarkerOptions().position(location);
        markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.mya));
        marker = mapAPI.addMarker(markerOptions1);
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(location));
        mapAPI.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
    }
    private void endTrips(final int id) {
        Toast.makeText(this, "End Trip Call", Toast.LENGTH_SHORT).show();
        String url_ = String.format("https://lynxdispatch-api.herokuapp.com/api/update/status/mark-complete?tripId=" + id);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url_, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(TripStarted.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                checkInternetConnection(error);
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", sharedpreferences.getString("TokenType", "") + " " + sharedpreferences.getString("AccessToken", ""));
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(TripStarted.this);
        requestQueue.add(stringRequest);
    }

    private void checkInternetConnection(VolleyError error) {
        String message = null;
        String title = "Network Error";
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        AlertDialog.Builder b = new AlertDialog.Builder(TripStarted.this);
        b.setTitle(title);
        b.setMessage(message);
        b.setPositiveButton("Wifi Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        b.show();
    }
}