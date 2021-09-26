package com.thecoderscrew.shop.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.thecoderscrew.shop.R;

import java.util.Objects;

public class Contact_us extends AppCompatActivity {
    private AppCompatEditText email_subject, email_message;
    private AppCompatButton event_send;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        Toolbar toolbar = findViewById(R.id.contact_us_toolbar);
        email_subject = findViewById(R.id.email_subject);
        email_message = findViewById(R.id.email_message);
        event_send = findViewById(R.id.contact_us_send);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        event_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = Objects.requireNonNull(email_subject.getText()).toString().trim();
                String message = Objects.requireNonNull(email_message.getText()).toString().trim();
                String recipient = "pickitquick00@gmail.com";

                if (subject.isEmpty()) {
                    Toast.makeText(Contact_us.this, "Please provide a subject", Toast.LENGTH_SHORT).show();
                } else if (message.isEmpty()) {
                    Toast.makeText(Contact_us.this, "Please write something to us.", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(recipient, subject, message);
                }

            }
        });

    }

    @SuppressLint("IntentReset")
    private void sendEmail(String recipient, String subject, String message) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipient);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(emailIntent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
