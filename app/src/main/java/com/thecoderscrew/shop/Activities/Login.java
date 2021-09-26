package com.thecoderscrew.shop.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;
import com.thecoderscrew.shop.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import android.view.View;

import java.util.Objects;

public class Login extends AppCompatActivity {
    TextInputEditText login_edittext;
    CountryCodePicker countryCodePicker;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        countryCodePicker=findViewById(R.id.cp);
        login_edittext = findViewById(R.id.login_edittext);
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                String mybox = Objects.requireNonNull(login_edittext.getText()).toString();
                if (mybox.isEmpty()) {
                    login_edittext.setError("Please enter your mobile no.");

                } else if (mybox.length() < 10) {
                    login_edittext.setError("Please provide correct mobile no.");

                } else {

                    startActivity(new Intent(Login.this, OTP.class).putExtra("mobile_number", countryCodePicker.getDefaultCountryCodeWithPlus()+ mybox));

                    login_edittext.setText("");

                }
            }
        });

        runtimepermission();

    }

    private void runtimepermission() {

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECEIVE_SMS}, 99);
    }

}
