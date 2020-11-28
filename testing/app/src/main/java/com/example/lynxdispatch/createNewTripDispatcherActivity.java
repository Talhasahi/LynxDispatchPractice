package com.example.lynxdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static java.net.Proxy.Type.HTTP;

public class createNewTripDispatcherActivity extends AppCompatActivity {
    private AutoCompleteTextView spinner_broker, spinner_vehicle, spinner_passenger;
    private Button backButton, createTrip, searchVendorTrip, addLeg;
    private ListView vendor_trips_listview;
    private TextInputEditText name, legId, contactNo, picupAddress, dropoffAddress, pickupTime,
            aptTime, mileage, customerRate, pickupDate, vendor_API_KEY;
    private String broker, callMessage = "", SessionKey = "", vehicle, pickupdate, no_of_passengers, pickuplatlang, dropofflatlang, currentDateTime;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private int PLACE_PICKER_REQUEST = 1, PLACE_PICKER_REQUEST2 = 2, newCreatedTripID = 0;
    private DatePickerDialog picker;
    private TimePickerDialog timepicker;
    private RadioButton vendor_trip_toggle, cashCall_trip_toggle;
    private ScrollView cashCAllTrip_s;
    private SharedPreferences sharedpreferences;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private ConstraintLayout vendorTrip_c;
    private double pickuplat, pickuplang, dropofflat, dropofflang;
    private List<String> clientName_l, clientPhone_l, pickupAddress_l, dropoffAddress_l, milaege_l,
            pickupDate_l, pickupTime_l, pickupLatLang_l, dropoff_LatLang_l, legID_l, way_l;
    private singlten_vendortrips adp;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip_dispatcher);
        inialization();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTrip();
            }
        });

        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                timepicker = new TimePickerDialog(createNewTripDispatcherActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (((int) (Math.log10(minute) + 1) == 1)) {
                            pickupTime.setText(hourOfDay + ":0" + minute);
                        } else {
                            pickupTime.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, mHour, mMinute, false);
                timepicker.show();
            }
        });

        //TDJ0PIIHVDYN5D2UXKE3DKJU5TXLD7DDCLUX3O2Y3DSPC994Y86UIPU73BJGG2IF
        aptTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                timepicker = new TimePickerDialog(createNewTripDispatcherActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (((int) (Math.log10(minute) + 1) == 1)) {
                            aptTime.setText(hourOfDay + ":0" + minute);
                        } else {
                            aptTime.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, mHour, mMinute, false);
                timepicker.show();
            }
        });
        pickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(createNewTripDispatcherActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (((int) (Math.log10(dayOfMonth) + 1) == 1) && ((int) (Math.log10(monthOfYear + 1) + 1) == 1)) {
                                    pickupdate = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                } else if (((int) (Math.log10(monthOfYear + 1) + 1) == 1)) {
                                    pickupdate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                } else if (((int) (Math.log10(dayOfMonth) + 1) == 1)) {
                                    pickupdate = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                } else {
                                    pickupdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }
                                pickupDate.setText(pickupdate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        picupAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(createNewTripDispatcherActivity.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        dropoffAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(createNewTripDispatcherActivity.this), PLACE_PICKER_REQUEST2);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        vendor_trip_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorTrip_c.setVisibility(View.VISIBLE);
                cashCAllTrip_s.setVisibility(View.GONE);
            }
        });

        cashCall_trip_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorTrip_c.setVisibility(View.GONE);
                cashCAllTrip_s.setVisibility(View.VISIBLE);
            }
        });

        spinner_broker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    vendor_API_KEY.setText("TDJ0PIIHVDYN5D2UXKE3DKJU5TXLD7DDCLUX3O2Y3DSPC994Y86UIPU73BJGG2IF");
                }
            }
        });

        searchVendorTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broker = spinner_broker.getText().toString();
                String api_key = vendor_API_KEY.getText().toString();
                if (TextUtils.isEmpty(broker) || TextUtils.isEmpty(api_key)) {
                    Toast.makeText(createNewTripDispatcherActivity.this, "Please, Fill All Fields...", Toast.LENGTH_SHORT).show();
                } else {
                    getSession(api_key);
                }

            }
        });

        vendor_trips_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(createNewTripDispatcherActivity.this);
                builder.setTitle("Confirmation Alert!");
                builder.setMessage("Do you want to save trip?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent newIntent = new Intent(createNewTripDispatcherActivity.this, saveVendorTripActivity.class);
                        newIntent.putExtra("clientName", clientName_l.get(position));
                        newIntent.putExtra("clientPhone", clientPhone_l.get(position));
                        newIntent.putExtra("clientPickupAddr", pickupAddress_l.get(position));
                        newIntent.putExtra("clientDropoffAddr", dropoffAddress_l.get(position));
                        newIntent.putExtra("clientPickupLatLang", pickupLatLang_l.get(position));
                        newIntent.putExtra("clientDropoffLatLang", dropoff_LatLang_l.get(position));
                        newIntent.putExtra("clientMilaege", milaege_l.get(position));
                        newIntent.putExtra("clientPickupDate", pickupDate_l.get(position));
                        newIntent.putExtra("clientPickupTime", pickupTime_l.get(position));
                        newIntent.putExtra("clientLegid", legID_l.get(position));
                        startActivity(newIntent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                });
                builder.show();
            }
        });


        addLeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(createNewTripDispatcherActivity.this, "Add LEG", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SearchVendorTrips(String API_KEY) {
        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = df.format(currentTime);
        Date nextDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        String nextDay = df.format(nextDate);
        String url = "https://www.medanswering.com/Provider_API.taf";
        final String reqXML = "<TPRequest>\n" +
                " <authentication>" + API_KEY + "</authentication>\n" +
                " <PullRoster version=\"2\">\n" +
                " <sessionIdentifier>" + SessionKey + "</sessionIdentifier>\n" +
                " <attributes>\n" +
                "<startdate>" + currentDate + "</startdate>\n" +
                "<enddate>" + nextDay + "</enddate>\n" +
                "<status>3</status>\n" +
                "<standingorder>No</standingorder>\n" +
                "<county>31</county>\n" +
                " </attributes>\n" +
                " </PullRoster>\n" +
                "</TPRequest>";

        progressDialog.show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//Volley request
        StringRequest request = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        parseXML(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        checkInternetConnection(error);
                    }
                }) {

            @Override
            public String getBodyContentType() {
                // set body content type
                return "application/xml; charset=UTF-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                try {
                    return reqXML.getBytes("UTF-8");
                } catch (UnsupportedEncodingException uee) {
                    // TODO consider if some other action should be taken
                    return null;
                }
            }

        };
//
//Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

//Adding request to the queue
        requestQueue.add(request);

    }

    private void parseXML(String response) {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = parserFactory.newPullParser();
            InputStream is = convertStringToDocument(response);
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(is, null);
            int session_N = xmlPullParser.getEventType();
            String name = "", addr = "", addr1 = "", latlang = "", latlang2 = "";
            while (session_N != XmlPullParser.END_DOCUMENT) {
                String eltName = null;
                switch (session_N) {
                    case XmlPullParser.START_TAG:
                        eltName = xmlPullParser.getName();
                        if ("TPResponse".equals(eltName)) {

                        } else {

                            if ("PullRoster".equals(eltName)) {

                            } else {
                                if ("responseMessage".equals(eltName)) {
                                    callMessage = xmlPullParser.nextText();
                                }
                                if ("trips".equals(eltName)) {

                                } else {
                                    if ("trip".equals(eltName)) {

                                    } else {
                                        if ("header".equals(eltName)) {

                                        } else {
                                            if ("recipient_first".equals(eltName)) {
                                                name = xmlPullParser.nextText();
                                            }
                                            if ("recipient_last".equals(eltName)) {
                                                name += " " + xmlPullParser.nextText();
                                                clientName_l.add(name);
                                            }
                                            if ("recipient_phone".equals(eltName)) {
                                                clientPhone_l.add(xmlPullParser.nextText());
                                            }
                                            if ("trips_approved".equals(eltName)) {
                                                way_l.add(xmlPullParser.nextText());
                                            }
                                        }
                                        if ("legs".equals(eltName)) {

                                        } else {
                                            if ("leg".equals(eltName)) {

                                            } else {
                                                if ("leg_id".equals(eltName)) {
                                                    legID_l.add(xmlPullParser.nextText());
                                                }
                                                if ("pickup_date".equals(eltName)) {
                                                    pickupDate_l.add(xmlPullParser.nextText());
                                                }
                                                if ("pickup_time".equals(eltName)) {
                                                    pickupTime_l.add(xmlPullParser.nextText());
                                                }
                                                if ("pickup_address".equals(eltName)) {
                                                    addr = xmlPullParser.nextText();
                                                }
                                                if ("pickup_city".equals(eltName)) {
                                                    addr += ", " + xmlPullParser.nextText();
                                                    pickupAddress_l.add(addr);
                                                }
                                                if ("pickup_lat".equals(eltName)) {
                                                    latlang = xmlPullParser.nextText();
                                                }
                                                if ("pickup_long".equals(eltName)) {
                                                    latlang += xmlPullParser.nextText();
                                                    pickupLatLang_l.add(latlang);
                                                }
                                                if ("dropoff_address".equals(eltName)) {
                                                    addr1 = xmlPullParser.nextText();
                                                }
                                                if ("dropoff_city".equals(eltName)) {
                                                    addr1 += ", " + xmlPullParser.nextText();
                                                    dropoffAddress_l.add(addr1);
                                                }
                                                if ("dropoff_lat".equals(eltName)) {
                                                    latlang2 = xmlPullParser.nextText();
                                                }
                                                if ("dropoff_long".equals(eltName)) {
                                                    latlang2 += xmlPullParser.nextText();
                                                    dropoff_LatLang_l.add(latlang2);
                                                }
                                                if ("mileage".equals(eltName)) {
                                                    milaege_l.add(xmlPullParser.nextText());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }
                session_N = xmlPullParser.next();
            }
            if (clientName_l.isEmpty()) {
                Toast.makeText(this, "Vendor Trip Not Found...", Toast.LENGTH_SHORT).show();
            } else {
                adp = new singlten_vendortrips(createNewTripDispatcherActivity.this, clientName_l, pickupAddress_l, dropoffAddress_l, way_l);
                vendor_trips_listview.setAdapter(adp);
                adp.notifyDataSetInvalidated();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getSession(final String API_KEY) {
        String url = "https://www.medanswering.com/Provider_API.taf";
        final String reqXML = "<TPRequest>\n" +
                "<authentication>" + API_KEY + "</authentication>\n" +
                "<startSession>\n" +
                "<attributes>\n" +
                "<attribute>\n" +
                "<name>CLIENT IP</name>\n" +
                "<value>10.10.10.10</value>\n" +
                "</attribute>\n" +
                "</attributes>\n" +
                "</startSession>\n" +
                "</TPRequest>";

        progressDialog.show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//Volley request
        StringRequest request = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        XmlPullParserFactory parserFactory;
                        try {
                            parserFactory = XmlPullParserFactory.newInstance();
                            XmlPullParser xmlPullParser = parserFactory.newPullParser();
                            InputStream is = convertStringToDocument(response);
                            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                            xmlPullParser.setInput(is, null);
                            int session_N = xmlPullParser.getEventType();
                            while (session_N != XmlPullParser.END_DOCUMENT) {
                                String eltName = null;
                                switch (session_N) {
                                    case XmlPullParser.START_TAG:
                                        eltName = xmlPullParser.getName();
                                        if ("TPRequest".equals(eltName)) {

                                        } else {
                                            if ("startSession".equals(eltName)) {

                                            } else {
                                                if ("sessionIdentifier".equals(eltName)) {
                                                    SessionKey = xmlPullParser.nextText();
                                                }
                                            }
                                        }
                                        break;
                                }
                                session_N = xmlPullParser.next();
                            }
                            SearchVendorTrips(API_KEY);
                        } catch (XmlPullParserException | IOException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        checkInternetConnection(error);
                    }
                }) {

            @Override
            public String getBodyContentType() {
                // set body content type
                return "application/xml; charset=UTF-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                try {
                    return reqXML.getBytes("UTF-8");
                } catch (UnsupportedEncodingException uee) {
                    // TODO consider if some other action should be taken
                    return null;
                }
            }

        };
//
//Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

//Adding request to the queue
        requestQueue.add(request);

    }

    private static InputStream convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(doc);
            Result outputTarget = new StreamResult(outputStream);
            TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
            return is;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                pickuplat = place.getLatLng().latitude;
                pickuplang = place.getLatLng().longitude;
                String s = convertLatitudeLongitudetoAddress(place.getLatLng().latitude, place.getLatLng().longitude);
                picupAddress.setText(s);
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST2) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                StringBuilder stringBuilder = new StringBuilder();
//                String latitude = String.valueOf(place.getLatLng().latitude);
//                String longitude = String.valueOf(place.getLatLng().longitude);
//                stringBuilder.append("Latitude:");
//                stringBuilder.append(latitude);
//                stringBuilder.append("Longitude:");
//                stringBuilder.append(longitude);
                dropofflat = place.getLatLng().latitude;
                dropofflang = place.getLatLng().longitude;
                String s = convertLatitudeLongitudetoAddress(place.getLatLng().latitude, place.getLatLng().longitude);
                dropoffAddress.setText(s);

                Location location1 = new Location("");
                location1.setLatitude(pickuplat);
                location1.setLongitude(pickuplang);

                Location location2 = new Location("");
                location2.setLatitude(dropofflat);
                location2.setLongitude(dropofflang);

                float distanceInMeters = (float) ((location1.distanceTo(location2)) / 1609.344);

                mileage.setText(distanceInMeters + "");

            }
        }
    }

    private String convertLatitudeLongitudetoAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            Log.e("tag", "Unable connect to Geocoder", e);
        }
        return null;
    }

    private void createNewTrip() {
        no_of_passengers = spinner_passenger.getText().toString();
        vehicle = spinner_vehicle.getText().toString();
        pickuplatlang = pickuplat + "," + pickuplang;
        dropofflatlang = pickuplat + "," + pickuplang;
        if (TextUtils.isEmpty(no_of_passengers) ||
                TextUtils.isEmpty(vehicle) ||
                TextUtils.isEmpty(name.getText().toString()) ||
                TextUtils.isEmpty(legId.getText().toString()) ||
                TextUtils.isEmpty(contactNo.getText().toString()) ||
                TextUtils.isEmpty(pickupTime.getText().toString()) ||
                TextUtils.isEmpty(picupAddress.getText().toString()) ||
                TextUtils.isEmpty(dropoffAddress.getText().toString()) ||
                TextUtils.isEmpty(pickupDate.getText().toString()) ||
                TextUtils.isEmpty(pickupDate.getText().toString())) {
            Toast.makeText(this, "Please Fill All Fields...", Toast.LENGTH_SHORT).show();
        } else {
            callApiForAddNewTrip();
        }
    }//sending request for creating new trip


    private void callApiForAddNewTrip() {
        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.uX");
        currentDateTime = df.format(currentTime);
        String url = "https://lynxdispatch-api.herokuapp.com/api/saveTrip";

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("appointmentTime", aptTime.getText().toString());
        postParam.put("assignedDriver", "NULL");
        postParam.put("clientName", name.getText().toString());
        postParam.put("companyNote", "Ok");
        postParam.put("createdAt", currentDateTime);
        postParam.put("customerSpecialRate", customerRate.getText().toString());
        postParam.put("date", pickupDate.getText().toString());
        postParam.put("dropoffLocation", dropofflatlang);
        postParam.put("milage", "12");
        postParam.put("passengers", no_of_passengers);
        postParam.put("phoneNo1", contactNo.getText().toString());
        postParam.put("phoneNo2", contactNo.getText().toString());
        postParam.put("pickupLocation", pickuplatlang);
        postParam.put("pickupTime", pickupTime.getText().toString());
        postParam.put("status", "UNASSIGNED");
        postParam.put("tripCreatorEmail", sharedpreferences.getString("UserEmail", ""));
        postParam.put("tripType", "INTERNAL");
        postParam.put("vehicleType", vehicle);


        progressDialog.show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            Toast.makeText(createNewTripDispatcherActivity.this, response.getJSONObject("body") + "", Toast.LENGTH_LONG).show();
                            newCreatedTripID = response.getJSONObject("body").getInt("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (newCreatedTripID > 0) {
                            Toast.makeText(createNewTripDispatcherActivity.this, "Trip Created", Toast.LENGTH_SHORT).show();
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        } else {
                            Toast.makeText(createNewTripDispatcherActivity.this, "Trip Not Created", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                checkInternetConnection(error);
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", sharedpreferences.getString("TokenType", "") + " " + sharedpreferences.getString("AccessToken", ""));

                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

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
        AlertDialog.Builder b = new AlertDialog.Builder(createNewTripDispatcherActivity.this);
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
                startActivity(intent);
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        b.show();
    }


    private void inialization() {
        spinner_broker = findViewById(R.id.spinner_vendor_Dispatcher);
        spinner_passenger = findViewById(R.id.spinner_passengers_Dispatcher);
        spinner_vehicle = findViewById(R.id.spinner_vehicle_Dispatcher);
        backButton = findViewById(R.id.backButton_go_create_new_trip_dispatcher);
        createTrip = findViewById(R.id.button_create_trip_dispatcher);
        name = findViewById(R.id.name_create_trip_dispatcher);
        legId = findViewById(R.id.name_trip_id_dispatcher);
        contactNo = findViewById(R.id.name_contact_no_dispatcher);
        picupAddress = findViewById(R.id.name_pickup_address_dispatcher);
        dropoffAddress = findViewById(R.id.name_dropoff_address_dispatcher);
        pickupTime = findViewById(R.id.name_pickup_time_dispatcher);
        aptTime = findViewById(R.id.name_apt_time_dispatcher);
        mileage = findViewById(R.id.Mileage_dispatcher);
        customerRate = findViewById(R.id.CustomerRate_dispatcher);
        pickupDate = findViewById(R.id.date_create_trip_dispatcher);
        vendor_trip_toggle = findViewById(R.id.vendor_trip_toggle);
        cashCall_trip_toggle = findViewById(R.id.cash_call_trip_toggle);
        cashCAllTrip_s = findViewById(R.id.CashCAllTrips_Scroll);
        vendorTrip_c = findViewById(R.id.VendorTrips_constraint);
        vendor_trips_listview = findViewById(R.id.vendor_trip_listview);
        vendor_API_KEY = findViewById(R.id.API_KEY_Vendor_Trip_dispatcher);
        searchVendorTrip = findViewById(R.id.button_search_vendor_trip_dispatcher);
        addLeg = findViewById(R.id.addLeg_createtrip);
        clientName_l = new ArrayList<>();
        clientPhone_l = new ArrayList<>();
        pickupAddress_l = new ArrayList<>();
        dropoffAddress_l = new ArrayList<>();
        milaege_l = new ArrayList<>();
        pickupDate_l = new ArrayList<>();
        pickupTime_l = new ArrayList<>();
        pickupLatLang_l = new ArrayList<>();
        dropoff_LatLang_l = new ArrayList<>();
        legID_l = new ArrayList<>();
        way_l = new ArrayList<>();

        progressDialog = new ProgressDialog(createNewTripDispatcherActivity.this);
        progressDialog.setMessage("Waiting...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


//         Construct SwitchDateTimePicker
//        dateTimeDialogFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
//        if (dateTimeDialogFragment == null) {
//            dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
//                    getString(R.string.label_datetime_dialog),
//                    getString(android.R.string.ok),
//                    getString(android.R.string.cancel),
//                    "Clean" // Optional
//            );
//        }
//        dateTimeDialogFragment1 = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
//        if (dateTimeDialogFragment1 == null) {
//            dateTimeDialogFragment1 = SwitchDateTimeDialogFragment.newInstance(
//                    getString(R.string.label_datetime_dialog),
//                    getString(android.R.string.ok),
//                    getString(android.R.string.cancel),
//                    "Clean" // Optional
//            );
//        }
//        // Init format
//        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
//        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
//            @Override
//            public void onPositiveButtonClick(Date date) {
//                pickupTime.setText(myDateFormat.format(date));
//            }
//
//            @Override
//            public void onNegativeButtonClick(Date date) {
//                // Do nothing
//            }
//
//            @Override
//            public void onNeutralButtonClick(Date date) {
//                // Optional if neutral button does'nt exists
//                pickupTime.setText("");
//            }
//        });


        String[] vendors = new String[]{"MAS"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(createNewTripDispatcherActivity.this,
                R.layout.list_item,
                vendors);
        spinner_broker.setAdapter(adapter2);

        String[] no_of_passenger = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(createNewTripDispatcherActivity.this,
                R.layout.list_item,
                no_of_passenger);
        spinner_passenger.setAdapter(adapter);

        String[] vehicles = new String[]{"Standard", "Premium", "SUV", "WAV", "BLS Stretcher"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(createNewTripDispatcherActivity.this,
                R.layout.list_item,
                vehicles);
        spinner_vehicle.setAdapter(adapter1);

    }
//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) activity.getSystemService(
//                        Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(
//                activity.getCurrentFocus().getWindowToken(), 0);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}