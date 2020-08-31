package com.example.lynxdispatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class SingltenSaveTripListAdapter extends BaseAdapter {
    private Context context;
    private List<String> name, name1, name2, name3,name4,tripType,dropOf,NoOfPasanger,baseToBase,Vehicle,Description,AppointmentDate,AppointmentTime;
    private TextView t1, t2, t3, t4;
    SingltenSaveTripListAdapter(Context c, List<String> s1, List<String> s2, List<String> s3, List<String> s4,List<String> s5,List<String> tripType_selected,List<String> dropOf_saved,List<String> No0fPasanger_saved,List<String> baseTobase_saved,   List<String> Description_List,List<String> AppointmentDate_List,List<String> AppointmentTime_List,List<String> Vehicle_List) {
        name = s1;
        name1 = s2;
        name2 = s3;
        name3 = s4;
        name4 = s5;
        tripType = tripType_selected;
        dropOf = dropOf_saved;
        NoOfPasanger = No0fPasanger_saved;
        baseToBase = baseTobase_saved;
        Vehicle = Vehicle_List;
        Description= Description_List;
        AppointmentDate = AppointmentDate_List;
        AppointmentTime = AppointmentTime_List;
        context = c;
    }
    @Override
    public int getCount() {
        return name.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.singlten_save_trip_list_view, null, false);


        t1 = convertView.findViewById(R.id.name_saved);
        t2 = convertView.findViewById(R.id.pickup_time_saved);
        t3 = convertView.findViewById(R.id.pickUpLocation_saved);
        t4 = convertView.findViewById(R.id.save_trip_Phone_No);


        t1.setText(name.get(position));
        t2.setText(name1.get(position));
        t3.setText(name2.get(position));
        t4.setText(name3.get(position));



        return convertView;
    }
}
