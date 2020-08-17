package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class ForgetPassActivity extends AppCompatActivity {

    private Button sendButton, backButton;
    private TextInputEditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        inialization();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendForgetPassEmail();
            }
        });

    }

    private void sendForgetPassEmail() {
        String getemail = email.getText().toString();
    }

    private void inialization() {
        email = findViewById(R.id.email_forget_pass);
        sendButton = findViewById(R.id.forget_pass_send_button);
        backButton = findViewById(R.id.forget_pass_backbutton);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}