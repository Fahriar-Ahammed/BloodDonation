package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etFullName, etPhoneNumber, etAddress, etEmailForRegister, etPasswordOneForRegister, etPasswordTwoForRegister;
    AutoCompleteTextView spinnerGender, spinnerBloodGroup, spinnerDivision;
    Button btnRegister;
    String name, phoneNumber,address, email, passwordOne, passwordTwo;
    String gender, bloodGroup, division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Spinner
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerDivision = findViewById(R.id.spinnerDivision);
        spinnerConfig();

        // EditText
        etFullName = findViewById(R.id.etFullName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        etEmailForRegister = findViewById(R.id.etEmailForRegister);
        etPasswordOneForRegister = findViewById(R.id.etPasswordOneForRegister);
        etPasswordTwoForRegister = findViewById(R.id.etPasswordTwoForRegister);

        // Button
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyError();
            }
        });



    }

    private void emptyError() {

        name = etFullName.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        address = etAddress.getText().toString();
        email = etEmailForRegister.getText().toString();
        passwordOne = etPasswordOneForRegister.getText().toString();
        passwordTwo = etPasswordTwoForRegister.getText().toString();


        if (name.isEmpty()){
            etFullName.requestFocus();
            etFullName.setError("Please enter your name.");
        }else if(phoneNumber.isEmpty()){
            etPhoneNumber.requestFocus();
            etPhoneNumber.setError("Please enter phone number.");
        }else if (address.isEmpty()){
            etAddress.requestFocus();
            etAddress.setError("Enter your address.");
        }else if (email.isEmpty()){
            etEmailForRegister.requestFocus();
            etEmailForRegister.setError("Enter your Email.");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmailForRegister.requestFocus();
            etEmailForRegister.setError("Enter a valid Email address.");
        }else if (passwordOne.isEmpty()){
            etPasswordOneForRegister.requestFocus();
            etPasswordOneForRegister.setError("Enter a password");
        }else if (passwordTwo.isEmpty()){
            etPasswordTwoForRegister.requestFocus();
            etPasswordTwoForRegister.setError("Enter a password");
        }else if (!passwordTwo.equals(passwordOne)){
            Toast.makeText(getApplicationContext(), "Password didn't match", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
        }


    }

    private void spinnerConfig() {
        // Gender Selector Spinner
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender,
                android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        // Blood Group Spinner
        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender,
                android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(bloodGroupAdapter);

        // Division Selection Spinner
        ArrayAdapter<CharSequence> divisionAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender,
                android.R.layout.simple_spinner_item);
        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDivision.setAdapter(divisionAdapter);
    }
}