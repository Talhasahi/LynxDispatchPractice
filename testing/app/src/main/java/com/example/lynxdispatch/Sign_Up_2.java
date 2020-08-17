package com.example.lynxdispatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sign_Up_2 extends AppCompatActivity {

    private ProgressDialog progressDialog1;
    private RequestQueue requestQueue;
    Button signButton, back_Button;
    private TextInputEditText address, country, state, city, zip_code, contact_no;
    private String first_name, last_name, email, role, password, address_String, country_String, state_String, city_String, zip_code_String, contact_no_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_2);
        //This Is Talha Comment.
        //This Is Talha Comment 2
        getDataFrom_SignUp();
        initialization();
        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBack_Button();
            }
        });
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signUp();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
   //Talha Comment
    private void initialization() {
        progressDialog1 = new ProgressDialog(Sign_Up_2.this);
        progressDialog1.setMessage("Loading...");
        progressDialog1.setCancelable(false);
        progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        back_Button = findViewById(R.id.backButton);
        address = findViewById(R.id.id_address_signUp);
        signButton = findViewById(R.id.signUp_button);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        zip_code = findViewById(R.id.zip_Code);
        contact_no = findViewById(R.id.contact_No);

    }

    private void setBack_Button() {

        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void signUp() throws JSONException {
        address_String = address.getText().toString();
        contact_no_String = contact_no.getText().toString();
        country_String = country.getText().toString();
        zip_code_String = zip_code.getText().toString();
        state_String = state.getText().toString();
        city_String = city.getText().toString();
        boolean fieldsOK = validate(new EditText[]{contact_no, country, state, city, address, zip_code});
        if (fieldsOK) {
            sendLoginRequest();
        } else {

        }
    }

    private void getDataFrom_SignUp() {
        Bundle b = getIntent().getExtras();
        first_name = b.getString("first_name");
        last_name = b.getString("last_name");
        email = b.getString("email");
        role = b.getString("role");
        password = b.getString("pssword");


    }

    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                currentField.setError("Do not leave empty");
                return false;
            }
        }
        return true;
    }

    private void sendLoginRequest() throws JSONException {

        String url = "https://lynxdispatch-api.herokuapp.com/api/auth/signup";

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        try {
            JSONArray arrays = new JSONArray();
            obj.put("firstname", first_name);
            obj.put("lastname", last_name);
            obj.put("username", email.toLowerCase());
            array.put(role.toString().toLowerCase());
            obj.put("role", array);
            obj.put("email", email.toLowerCase());
            obj.put("password", password);
            obj.put("country", country_String);
            obj.put("state", state_String);
            obj.put("city", city_String);
            obj.put("zipcode", Integer.parseInt(zip_code_String));
            obj.put("contactNo", contact_no_String);
            obj.put("address", address_String);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = obj.toString();

        // Toast.makeText(getApplicationContext(), "params-"+obj, Toast.LENGTH_LONG).show();
        progressDialog1.show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(requestBody),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog1.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        try {
                            String msg = response.getString("message");
                            if (msg.equals("User Registered Success")) {
                                Toast.makeText(Sign_Up_2.this, msg, Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Sign_Up_2.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    }
                                }, 1000);


                            } else {
                                Toast.makeText(Sign_Up_2.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog1.dismiss();
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
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }// end of request function...

    private void checkInternetConnection(VolleyError error) {
        String message = null;
        String title = "Network Error";
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (TextUtils.isEmpty(error.getMessage())) {
            title = "Unknown Error";
            message = "User Not Registered!!!";
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
        AlertDialog.Builder b = new AlertDialog.Builder(Sign_Up_2.this);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}