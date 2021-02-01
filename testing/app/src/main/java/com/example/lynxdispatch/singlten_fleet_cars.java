package com.example.lynxdispatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class singlten_fleet_cars extends BaseAdapter {

    private Context context;
    private List<String> car, assign, reg;
    private TextView t1, t2, t3;
    private CircleImageView avatar;

    singlten_fleet_cars(Context c, List<String> s1, List<String> s2, List<String> s3) {
        car = s1;
        assign = s2;
        reg = s3;
        context = c;
    }

    @Override
    public int getCount() {
        return car.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.singlten_fleet_dispatcher, null, false);


        t1 = convertView.findViewById(R.id.fleet_car_singlten);
        t2 = convertView.findViewById(R.id.fleet_assign_singlten);
        t3 = convertView.findViewById(R.id.fleet_reg_singlten);
        avatar = convertView.findViewById(R.id.fleet_avatar_singlten);
        t1.setText(car.get(position));
        if (assign.get(position) == "null") {
            t2.setText("UNASSIGNED");
        } else {
            t2.setText(assign.get(position));
        }
        t3.setText(reg.get(position));


        return convertView;
    }
}
