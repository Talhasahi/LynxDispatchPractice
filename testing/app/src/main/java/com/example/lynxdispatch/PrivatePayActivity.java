package com.example.lynxdispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivatePayActivity extends AppCompatActivity {

    private Button backButton;
    private ListView listView;
    private singlten_no_started adp;
    private SharedPreferences sharedpreferences;
    private ProgressDialog progressDialog;
    private List<Integer> tripIdList;
    private List<String> clientNameList, pickupLocationList, dropoffLocationList,
            milageList, dateList, tripTypeList, statusList, aptList, pickupTimeList, driverList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_pay);

        inialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        getTrips();

    }

    private void getTrips() {
        String url_ = String.format("https://lynxdispatch-api.herokuapp.com/api/trips?descending=%b&keyword=%s&status=%s", false, sharedpreferences.getString("SponserEmail", ""), "PENDING");

        progressDialog.show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                try {
                    JSONObject temp = new JSONObject(response);
                    if (temp.getInt("totalElements") == 0) {
                        Toast.makeText(PrivatePayActivity.this, "Trips Not Found...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PrivatePayActivity.this, "Trips Found...", Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray = temp.getJSONArray("content");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            tripIdList.add(jsonObject.getInt("id"));
                            clientNameList.add(jsonObject.getString("clientName"));
                            pickupLocationList.add(jsonObject.getString("pickupLocation"));
                            dropoffLocationList.add(jsonObject.getString("dropoffLocation"));
                            milageList.add(jsonObject.getString("milage"));
                            dateList.add(jsonObject.getString("date"));
                            pickupTimeList.add(jsonObject.getString("pickupTime"));
                            aptList.add(jsonObject.getString("appointmentTime"));
                            statusList.add(jsonObject.getString("status"));
                            driverList.add(jsonObject.getString("assignedDriver"));
                            tripTypeList.add(jsonObject.getString("tripType"));
                        }

                        adp = new singlten_no_started(PrivatePayActivity.this, tripIdList, clientNameList, pickupLocationList,
                                dropoffLocationList, milageList, dateList, pickupTimeList, aptList, statusList, driverList, tripTypeList);
                        listView.setAdapter(adp);
                        adp.notifyDataSetInvalidated();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
        RequestQueue requestQueue = Volley.newRequestQueue(PrivatePayActivity.this);
        requestQueue.add(stringRequest);
    }

    private void inialization() {
        backButton = findViewById(R.id.backButton_private_pay_trips);
        listView = findViewById(R.id.listview_private_pay_trips);

        progressDialog = new ProgressDialog(PrivatePayActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);

        tripIdList = new ArrayList<>();
        clientNameList = new ArrayList<>();
        pickupLocationList = new ArrayList<>();
        dropoffLocationList = new ArrayList<>();
        milageList = new ArrayList<>();
        dateList = new ArrayList<>();
        tripTypeList = new ArrayList<>();
        statusList = new ArrayList<>();
        aptList = new ArrayList<>();
        pickupTimeList = new ArrayList<>();
        driverList = new ArrayList<>();

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
        AlertDialog.Builder b = new AlertDialog.Builder(PrivatePayActivity.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}