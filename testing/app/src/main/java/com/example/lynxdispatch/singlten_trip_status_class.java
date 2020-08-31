package com.example.lynxdispatch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class singlten_trip_status_class extends BaseAdapter {

    private Context context;
    private List<String> n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, cancel_reason;
    private Button cancel_b, forward_b;
    private ImageView edit_b;
    private LinearLayout cancel_layout;
    private int n;

    singlten_trip_status_class(Context c, List<String> nn1, List<String> nn2, List<String> nn3,
                               List<String> nn4, List<String> nn5, List<String> nn6,
                               List<String> nn7, List<String> nn8, List<String> nn9, List<String> nn10,
                               List<String> nn11, int num1) {

        n = num1;
        n1 = nn1;
        n2 = nn2;
        n3 = nn3;
        n4 = nn4;
        n5 = nn5;
        n6 = nn6;
        n7 = nn7;
        n8 = nn8;
        n9 = nn9;
        n10 = nn10;
        n11 = nn11;
        context = c;
    }

    @Override
    public int getCount() {
        return n1.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
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

        if (n == 1) {
            cancel_b.setVisibility(View.VISIBLE);
            forward_b.setVisibility(View.VISIBLE);
            edit_b.setVisibility(View.VISIBLE);
        } else {
            cancel_b.setVisibility(View.GONE);
            forward_b.setVisibility(View.GONE);
            edit_b.setVisibility(View.GONE);
        }

        if (n10.get(position).equals("Offered")) {
            cancel_b.setVisibility(View.VISIBLE);
            forward_b.setVisibility(View.VISIBLE);
            edit_b.setVisibility(View.VISIBLE);
        } else if (n10.get(position).equals("Accepted")) {
            cancel_b.setVisibility(View.VISIBLE);
            forward_b.setVisibility(View.VISIBLE);
            edit_b.setVisibility(View.VISIBLE);
        } else if (n10.get(position).equals("Started")) {
            cancel_b.setVisibility(View.GONE);
            forward_b.setVisibility(View.GONE);
            edit_b.setVisibility(View.GONE);
        } else if (n10.get(position).equals("Cancelled")) {
            cancel_b.setVisibility(View.GONE);
            forward_b.setVisibility(View.GONE);
            edit_b.setVisibility(View.GONE);
            cancel_layout.setVisibility(View.VISIBLE);
        } else {
            cancel_b.setVisibility(View.GONE);
            forward_b.setVisibility(View.GONE);
            edit_b.setVisibility(View.GONE);
            cancel_layout.setVisibility(View.GONE);
        }

        t1.setText(n1.get(position));
        t2.setText(n2.get(position));
        t3.setText(n3.get(position));
        t4.setText(n4.get(position));
        t5.setText(n5.get(position));
        t6.setText(n6.get(position));
        t7.setText("Appointment: " + n7.get(position));
        t8.setText(n8.get(position));
        t9.setText(n9.get(position));
        t10.setText(n10.get(position));
        cancel_reason.setText("Reason: " + n11.get(position));


        return convertView;
    }
}
