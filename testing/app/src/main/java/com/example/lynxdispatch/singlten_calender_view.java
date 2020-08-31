package com.example.lynxdispatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class singlten_calender_view extends RecyclerView.Adapter<singlten_calender_view.MyViewHolder> {
    Context c;
    ArrayList Month, Day, Year, no_trips;
    private OnCalendarItemListener mOnCalendarItemListener;

    singlten_calender_view(Context context, ArrayList Month_list, ArrayList Day_list,
                           ArrayList Year_list, ArrayList no_trips_list,OnCalendarItemListener onCalendarItemListener) {
        this.c = context;
        this.Month = Month_list;
        this.Day = Day_list;
        this.Year = Year_list;
        this.no_trips = no_trips_list;
        mOnCalendarItemListener=onCalendarItemListener;
    }

    @NonNull
    @Override
    public singlten_calender_view.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View v = layoutInflater.inflate(R.layout.singlten_calenderview_layout, parent, false);

        return new MyViewHolder(v,mOnCalendarItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull singlten_calender_view.MyViewHolder holder, int position) {
        holder.t1.setText(String.valueOf(Month.get(position)));
        holder.t2.setText(String.valueOf(Day.get(position)));
        holder.t3.setText(String.valueOf(Year.get(position)));
        holder.t4.setText(String.valueOf(no_trips.get(position)));
    }


    @Override
    public int getItemCount() {
        return Month.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1, t2, t3, t4;
        OnCalendarItemListener onCalendarItemListener;

        public MyViewHolder(@NonNull View itemView, OnCalendarItemListener onCalendarClick) {
            super(itemView);

            t1 = itemView.findViewById(R.id.month_singlten);
            t2 = itemView.findViewById(R.id.day_singlten);
            t3 = itemView.findViewById(R.id.year_singlten);
            t4 = itemView.findViewById(R.id.no_of_trips_singlten);

            this.onCalendarItemListener = onCalendarClick;

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onCalendarItemListener.onCalenderClick(getAdapterPosition());
        }
    }

    public interface OnCalendarItemListener {
        void onCalenderClick(int position);
    }


}
