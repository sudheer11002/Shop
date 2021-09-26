package com.thecoderscrew.shop.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.thecoderscrew.shop.R;

import java.util.HashMap;
import java.util.Objects;

public class AfterPayment extends AppCompatActivity {
    private HashMap<String, String> transfer = new HashMap<>();

    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),MyCart.class));
        super.onBackPressed();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_payment);
        Toolbar toolbar = findViewById(R.id.afterpayment_toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        findViewById(R.id.view_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),MyOrders.class));
               finishAffinity();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            startActivity(new Intent(getApplicationContext(),MyCart.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
