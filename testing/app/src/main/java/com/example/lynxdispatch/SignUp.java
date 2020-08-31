package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity {
    Button next_Step, back_button;
    private TextInputEditText first_name, last_name, email, password;
    private TextView already_Account;
    private AutoCompleteTextView spinner_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This comment is added by fahad1
        // This comment is added by fahad2
        // This comment is added by fahad3
        setContentView(R.layout.activity_sign_up);
        initialization();
        set_Up_spinner();
        next_Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFunc();
            }
        });

        already_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    private void initialization() {
        spinner_menu = findViewById(R.id.filled_exposed_dropdown);
        next_Step = findViewById(R.id.next_signin_step);
        first_name = findViewById(R.id.first_Name);
        last_name = findViewById(R.id.last_Name);
        email = findViewById(R.id.email_sign_In);
        password = findViewById(R.id.password_SignIn);
        already_Account = findViewById(R.id.sign_Up_login_now);
        back_button = findViewById(R.id.backButton_go_to_login);
    }

    private void set_Up_spinner() {
        String[] COUNTRIES = new String[]{"Driver", "Dispatcher"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUp.this,
                R.layout.list_item,
                COUNTRIES);
        int text = 1;
        spinner_menu.setText("Select Account");
        spinner_menu.setAdapter(adapter);

    }

    private void signUpFunc() {
        String role_String = spinner_menu.getText().toString();
        String first_name_String = first_name.getText().toString();
        String last_name_String = last_name.getText().toString();
        String email_String = email.getText().toString().trim();
        String pssword_String = password.getText().toString();
        String emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean fieldsOK = validate(new EditText[]{first_name, last_name, email});
        if (fieldsOK) {
            if (!email_String.matches(emailValidation)) {
                email.setError("Invalid email format");
            } else if (role_String.isEmpty() || role_String.equals("Select Account")) {
                Toast.makeText(this, "Select Your Account type", Toast.LENGTH_SHORT).show();
            } else if (pssword_String.isEmpty() || password.length() < 8) {
                Toast.makeText(this, "Password cannot be less than 8 characters!", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent(SignUp.this, Sign_Up_2.class);
                intent.putExtra("first_name", first_name_String);
                intent.putExtra("last_name", last_name_String);
                intent.putExtra("email", email_String);
                intent.putExtra("role", role_String);
                intent.putExtra("pssword", pssword_String);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        } else {

        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
