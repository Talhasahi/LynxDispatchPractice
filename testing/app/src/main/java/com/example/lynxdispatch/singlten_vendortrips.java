package com.example.lynxdispatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class singlten_vendortrips extends BaseAdapter {

    List<String> name, pickup, dropoff, way;
    Context c;
    TextView t1, t2, t3, t4;

    singlten_vendortrips(Context context, List<String> l1, List<String> l2, List<String> l3, List<String> l4) {
        c = context;
        name = l1;
        pickup = l2;
        dropoff = l3;
        way = l4;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(c).inflate(R.layout.singlten_vendortrips_layout, null, false);


        t1 = convertView.findViewById(R.id.textView15);
        t2 = convertView.findViewById(R.id.textView16);
        t3 = convertView.findViewById(R.id.textView17);
        t4 = convertView.findViewById(R.id.textView18);

        t1.setText(name.get(position));
        t2.setText(pickup.get(position));
        t3.setText(dropoff.get(position));
        t4.setText(way.get(position) + " Way");

        return convertView;
    }
}
