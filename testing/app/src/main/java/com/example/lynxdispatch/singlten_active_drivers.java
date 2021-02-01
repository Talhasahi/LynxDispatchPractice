package com.example.lynxdispatch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class singlten_active_drivers extends BaseAdapter {

    private Context context;
    private List<String> driverEmailList, driverNameList, driverUserlogoList;
    private TextView t1, t2, t3, t4;
    private ImageView phone, msg;
    private CircleImageView avatar;

    singlten_active_drivers(Context c, List<String> s1, List<String> s2, List<String> s3) {
        driverEmailList = s1;
        driverNameList = s2;
        driverUserlogoList = s3;
        context = c;
    }

    @Override
    public int getCount() {
        return driverNameList.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.singlten_active_drivers_layout, null, false);


        t1 = convertView.findViewById(R.id.driver_name_singlten);
        t2 = convertView.findViewById(R.id.driver_name1_singlten);
        //t3 = convertView.findViewById(R.id.driver_name2_singlten);
        //t4 = convertView.findViewById(R.id.driver_name3_singlten);
        msg = convertView.findViewById(R.id.driver_msg_singlten);
        phone = convertView.findViewById(R.id.driver_call_singlten);
        avatar = convertView.findViewById(R.id.driver_avatar_singlten);

        t1.setText(driverNameList.get(position));
        t2.setText(driverEmailList.get(position));
        RequestOptions transcodeTypeRequestBuilder = new RequestOptions().error(R.drawable.avatar);
        Glide.with(context).load(driverUserlogoList.get(position)).apply(transcodeTypeRequestBuilder).into(avatar);


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(context, CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, CALL_PHONE))) {

                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{CALL_PHONE}, 1);
                    }
                } else {
                    Uri sms_uri = Uri.parse("tel:923028863134");
                    Intent sms_intent = new Intent(Intent.ACTION_DIAL, sms_uri);
                    context.startActivity(sms_intent);
                }


            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("ChatPersonName", driverNameList.get(position));
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
