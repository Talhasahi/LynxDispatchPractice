package com.example.lynxdispatch;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class singlten_no_show_trip_class_t extends BaseAdapter {
    private ProgressDialog progressDialog;
    private Context context;
    private List<Integer> tripIdList;
    private List<String> clientNameList, pickupLocationList,
            dropoffLocationList, milageList, dateList, pickupTimeList, aptList, statusList, tripTypeList;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, cancel_reason;
    private ImageView edit_b;
    private LinearLayout cancel_layout;
    private RequestQueue requestQueue;
    private String f1, f2;
    private SharedPreferences sharedpreferences;
    singlten_no_show_trip_class_t(Context c, List<Integer> tripIdList_, List<String> clientNameList_,
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
        convertView = LayoutInflater.from(context).inflate(R.layout.singlten_no_show_trip_layout_t, null, false);
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

        return convertView;
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
