package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister, tvRegisterOne, tvForgetPassword;
    private static final String TAG = "Cannot invoke method length() on null object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvRegister = findViewById(R.id.tvRegister);
        tvRegisterOne = findViewById(R.id.tvRegisterOne);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);

        btnLogin = findViewById(R.id.btnLogin);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError("Enter a valid Email");
                    etEmail.requestFocus();
                    return;
                }

                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@@@@@@@@                      Email : " + etEmail.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@@@@@@@@                       Password : " + etPassword.getText());
            }
        });




    }
}