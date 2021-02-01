package com.example.lynxdispatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView forgetPassword, newAccount;
    private Button loginButton;
    private TextInputEditText email_e, password_e;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private RequestQueue requestQueue;
    private String FullName, AccessToken, TokenType, UserProfileAddress, UserEmail, SponserEmail;
    private int RoleId, Id;
    private long UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();


        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forget Passowrd...", Toast.LENGTH_SHORT).show();
            }
        });

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunc();
            }
        });

    }

    private void loginFunc() {
        String s = email_e.getText().toString().trim();
        String s1 = password_e.getText().toString();
        String emailValidation = "[a-zA-Z0-9._+-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(s) || !s.matches(emailValidation)) {
            email_e.setError("Invalid email format");
        } else if (TextUtils.isEmpty(s1)) {
            Toast.makeText(this, "Password: This field must be filled!", Toast.LENGTH_LONG).show();
        } else {

            sendLoginRequest(s, s1);

        }//else of checking empty fields in login username password
    }

    private void sendLoginRequest(final String username, final String password) {

        String url = "https://lynxdispatch-api.herokuapp.com/api/auth/signin";

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("username", username);
        postParam.put("password", password);

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
                            UserProfileAddress = response.getJSONObject("user").getString("userLogo");
                            FullName = response.getJSONObject("user").getString("firstname") + ' ' +
                                    response.getJSONObject("user").getString("lastname");
                            UserEmail = response.getJSONObject("user").getString("email");
                            Id = response.getJSONObject("user").getInt("id");
                            UserId = response.getJSONObject("user").getLong("userId");
                            RoleId = response.getJSONObject("user").getJSONArray("roles").
                                    getJSONObject(0).getInt("id");
                            AccessToken = response.getString("accessToken");
                            TokenType = response.getString("tokenType");
                            SponserEmail = response.getJSONObject("user").getString("dispatcherSponsor");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                        if (RoleId == 1) {
                            Intent intent = new Intent(LoginActivity.this, DriverHomeActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            editor.putInt("flagLogin", 1);
                            editor.putString("FullName", FullName);
                            editor.putString("UserEmail", UserEmail);
                            editor.putLong("UserId", UserId);
                            editor.putInt("RoleId", RoleId);
                            editor.putString("AccessToken", AccessToken);
                            editor.putString("TokenType", TokenType);
                            editor.putString("UserProfile", UserProfileAddress);
                            editor.putString("UserName", username);
                            editor.putString("Password", password);
                            editor.putString("SponserEmail", SponserEmail);
                            editor.apply();
                        } else if (RoleId == 2) {
                            Intent intent = new Intent(LoginActivity.this, DispatchHomeActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            editor.putInt("flagLogin", 2);
                            editor.putString("FullName", FullName);
                            editor.putString("UserEmail", UserEmail);
                            editor.putLong("UserId", UserId);
                            editor.putInt("RoleId", RoleId);
                            editor.putString("AccessToken", AccessToken);
                            editor.putString("TokenType", TokenType);
                            editor.putString("UserProfile", UserProfileAddress);
                            editor.putString("dispatcherSponser", SponserEmail);
                            editor.putString("UserName", username);
                            editor.putString("Password", password);
                            editor.putString("SponserEmail", SponserEmail);
                            editor.apply();
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
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }// end of request function...

    private void initialization() {
        forgetPassword = findViewById(R.id.textView);
        newAccount = findViewById(R.id.textView3);
        loginButton = findViewById(R.id.button);
        email_e = findViewById(R.id.email_login);
        password_e = findViewById(R.id.password_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        sharedpreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        editor = sharedpreferences.edit();

        int loginflag = sharedpreferences.getInt("flagLogin", 0);

        if (loginflag == 1) {
            Intent intent = new Intent(LoginActivity.this, DriverHomeActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else if (loginflag == 2) {
            Intent intent = new Intent(LoginActivity.this, DispatchHomeActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }//end of inialization function...


    private void checkInternetConnection(VolleyError error) {
        String message = null;
        String title = "Network Error";
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (TextUtils.isEmpty(error.getMessage())) {
            title = "Email & Password Error";
            message = "Please Enter Valid Email & Password!!!";
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
        AlertDialog.Builder b = new AlertDialog.Builder(LoginActivity.this);
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
}