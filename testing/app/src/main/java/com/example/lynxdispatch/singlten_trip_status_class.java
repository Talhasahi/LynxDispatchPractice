package com.example.lynxdispatch;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class singlten_trip_status_class extends BaseAdapter {
    private ProgressDialog progressDialog;
    private Context context;
    private List<Integer> tripIdList;
    private List<String> clientNameList, pickupLocationList,
            dropoffLocationList, milageList, dateList, pickupTimeList, aptList, statusList, tripTypeList;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, cancel_reason;
    private Button cancel_b, forward_b;
    private ImageView edit_b;
    private LinearLayout cancel_layout;
    private RequestQueue requestQueue;
    private String f1, f2;
    private SharedPreferences sharedpreferences;
    singlten_trip_status_class(Context c, List<Integer> tripIdList_, List<String> clientNameList_,
                               List<String> pickupLocationList_, List<String> dropoffLocationList_,
                               List<String> milageList_, List<String> dateList_, List<String> pickupTimeList_,
                               List<String> aptList_, List<String> statusList_, List<String> tripTypeList_) {
        tripIdList = tripIdList_;
        clientNameList = clientNameList_;
        pickupLocationList = pickupLocationList_;
        dropoffLocationList = dropoffLocationList_;
        milageList = milageList_;
        dateList = dateList_;
        pickupTimeList = pickupTimeList_;
        aptList = aptList_;
        statusList = statusList_;
        tripTypeList = tripTypeList_;
        context = c;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sharedpreferences = context.getSharedPreferences("login_data", MODE_PRIVATE);

    }

    @Override
    public int getCount() {
        return tripIdList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.singlten_trip_status_layout, null, false);
        t1 = convertView.findViewById(R.id.singlten_tripStatus_customerName);
        t2 = convertView.findViewById(R.id.singlten_tripStatus_Date);
        t3 = convertView.findViewById(R.id.singlten_tripStatus_Id);
        t4 = convertView.findViewById(R.id.singlten_tripStatus_picupaddress);
        t5 = convertView.findViewById(R.id.singlten_tripStatus_dropoffadd);
        t6 = convertView.findViewById(R.id.singlten_tripStatus_distance);
        t7 = convertView.findViewById(R.id.singlten_tripStatus_appointmentNo);
        t8 = convertView.findViewById(R.id.singlten_tripStatus_driverName);
        t9 = convertView.findViewById(R.id.singlten_tripStatus_tripDate);
        t10 = convertView.findViewById(R.id.singlten_tripStatus_status);
        cancel_reason = convertView.findViewById(R.id.singlten_tripStatus_reason_cancel);
        cancel_layout = convertView.findViewById(R.id.singlten_tripStatus_layout_cancel);
        edit_b = convertView.findViewById(R.id.singlten_tripStatus_edit);
        cancel_b = convertView.findViewById(R.id.singlten_tripStatus_cancel_b);
        forward_b = convertView.findViewById(R.id.singlten_tripStatus_forward_b);

        if (!statusList.get(position).equals("UNASSIGNED")) {
            forward_b.setVisibility(View.INVISIBLE);
            cancel_b.setVisibility(View.INVISIBLE);
        }
        if (statusList.get(position).equals("NOT_STARTED")) {
            cancel_b.setVisibility(View.VISIBLE);
            cancel_b.setText("Start");
        }

        t1.setText(clientNameList.get(position));
        t2.setText(dateList.get(position));
        t3.setText(tripTypeList.get(position));
        t4.setText(pickupLocationList.get(position));
        t5.setText(dropoffLocationList.get(position));
        t6.setText(milageList.get(position));
        t7.setText("Appointment: " + aptList.get(position));
        t8.setText("No Driver");
        t9.setText(pickupTimeList.get(position));
        t10.setText(statusList.get(position));
        //cancel_reason.setText("Reason: ");


        forward_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActiveDriversActivity.class);
                intent.putExtra("trip_Id", tripIdList.get(position));
                ((AppCompatActivity) context).startActivity(intent);
                ((AppCompatActivity) context).finish();
            }
        });
        cancel_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusList.get(position).equals("NOT_STARTED")) {
                   startTrips(tripIdList.get(position));
                }
            }
        });
        return convertView;
    }
    private void sendRequest(final int id) {
        String url = "https://api.thingspeak.com/channels/1287872/feeds.json?api_key=78B9OSG42R4R0PNM&results=2";
        progressDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("feeds");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < 1; i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                f1 = jsonObject.getString("field1");
                                f2 = jsonObject.getString("field2");
                                if (!f1.equals("") && !f2.equals("")) {
                                    Intent intent = new Intent(context, TripStarted.class);
                                    intent.putExtra("F1", f1);
                                    intent.putExtra("F2", f2);
                                    intent.putExtra("Tripid", id);
                                    context.startActivity(intent);
                                } else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);

    }
    private void startTrips(final int id) {
        String url_ = String.format("https://lynxdispatch-api.herokuapp.com/api/start-trip?tripId=" + id);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url_, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equals("Success")) {
                    sendRequest(id);
                } else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
        AlertDialog.Builder b = new AlertDialog.Builder(context);
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
                context.startActivity(intent);
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
