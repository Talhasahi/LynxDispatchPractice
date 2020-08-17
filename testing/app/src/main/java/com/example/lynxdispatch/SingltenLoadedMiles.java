package com.example.lynxdispatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SingltenLoadedMiles extends BaseAdapter {
    private Context context;
    private List<String> name, name1, name2, name3,name4;
    private TextView t1, t2, t3, t4,t5;
    SingltenLoadedMiles(Context c, List<String> s1, List<String> s2, List<String> s3, List<String> s4,List<String> s5) {
        name = s1;
        name1 = s2;
        name2 = s3;
        name3 = s4;
        name4 = s5;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.singlten_loaded_miles, null, false);


        t1 = convertView.findViewById(R.id.logs);
        t2 = convertView.findViewById(R.id.pickUpLocation);
        t3 = convertView.findViewById(R.id.total_miles);
        t4 = convertView.findViewById(R.id.total_time);
        t5 = convertView.findViewById(R.id.drop_Off_Location);

        t1.setText(name.get(position));
        t2.setText(name1.get(position));
        t3.setText(name2.get(position));
        t4.setText(name3.get(position));
        t5.setText(name4.get(position));


        return convertView;
    }
}
