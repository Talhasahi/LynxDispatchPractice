package com.example.lynxdispatch;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class singlten_no_started extends BaseAdapter {
    private ProgressDialog progressDialog;
    private Context context;
    private List<Integer> tripIdList;
    private List<String> clientNameList, pickupLocationList,
            dropoffLocationList, milageList, dateList, pickupTimeList, aptList, statusList, driverList, tripTypeList;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
    private ImageView edit_b;

    singlten_no_started(Context c, List<Integer> tripIdList_, List<String> clientNameList_,
                        List<String> pickupLocationList_, List<String> dropoffLocationList_,
                        List<String> milageList_, List<String> dateList_, List<String> pickupTimeList_,
                        List<String> aptList_, List<String> statusList_, List<String> driverList_, List<String> tripTypeList_) {
        tripIdList = tripIdList_;
        clientNameList = clientNameList_;
        pickupLocationList = pickupLocationList_;
        dropoffLocationList = dropoffLocationList_;
        milageList = milageList_;
        dateList = dateList_;
        pickupTimeList = pickupTimeList_;
        aptList = aptList_;
        statusList = statusList_;
        driverList = driverList_;
        tripTypeList = tripTypeList_;
        context = c;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

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
        convertView = LayoutInflater.from(context).inflate(R.layout.singlten_no_started_layout, null, false);
        t1 = convertView.findViewById(R.id.singlten_not_started_customerName);
        t2 = convertView.findViewById(R.id.singlten_not_started_Date);
        t3 = convertView.findViewById(R.id.singlten_not_started_Id);
        t4 = convertView.findViewById(R.id.singlten_not_started_picupaddress);
        t5 = convertView.findViewById(R.id.singlten_not_started_dropoffadd);
        t6 = convertView.findViewById(R.id.singlten_not_started_distance);
        t7 = convertView.findViewById(R.id.singlten_not_started_appointmentNo);
        t8 = convertView.findViewById(R.id.singlten_not_started_driverName);
        t9 = convertView.findViewById(R.id.singlten_not_started_tripDate);
        t10 = convertView.findViewById(R.id.singlten_not_started_status);
        edit_b = convertView.findViewById(R.id.singlten_not_started_edit);

        t1.setText(clientNameList.get(position));
        t2.setText(dateList.get(position));
        t3.setText(tripTypeList.get(position));
        t4.setText(pickupLocationList.get(position));
        t5.setText(dropoffLocationList.get(position));
        if (milageList.get(position).equals("")) {
            t6.setText("N/A");
        } else {
            t6.setText(milageList.get(position));
        }
        t7.setText("Appointment: " + aptList.get(position));
        String[] separated = driverList.get(position).split("@");
        t8.setText(separated[0]);
        t9.setText(pickupTimeList.get(position));
        t10.setText(statusList.get(position));

        return convertView;
    }


}