package com.example.lynxdispatch;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class singlten_trip_status_class extends BaseAdapter {

    private Context context;
    private List<Integer> tripIdList;
    private List<String> clientNameList, pickupLocationList,
            dropoffLocationList, milageList, dateList, pickupTimeList, aptList, statusList, tripTypeList;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, cancel_reason;
    private Button cancel_b, forward_b;
    private ImageView edit_b;
    private LinearLayout cancel_layout;

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
                    Toast.makeText(context, "Trip Started.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return convertView;
    }
}
