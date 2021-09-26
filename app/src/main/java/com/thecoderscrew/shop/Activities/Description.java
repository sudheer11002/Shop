package com.thecoderscrew.shop.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.thecoderscrew.shop.Adapters.ImageAdapter;
import com.thecoderscrew.shop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;

public class Description extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        ViewPager viewPager = findViewById(R.id.view_pager);
        Toolbar toolbar = findViewById(R.id.des_toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        ((AppCompatTextView) findViewById(R.id.des_title)).setText(getIntent().getStringExtra("productname"));                                   //Displaying the strings on different TextViews
        ((AppCompatTextView) findViewById(R.id.des_price)).setText("₹ " + getIntent().getStringExtra("price"));
        ((AppCompatTextView) findViewById(R.id.des_discount)).setText(getIntent().getStringExtra("discount") + "% off");
        ((AppCompatTextView) findViewById(R.id.des_material)).setText(getIntent().getStringExtra("material"));
        ((AppCompatTextView) findViewById(R.id.des_NeckType)).setText(getIntent().getStringExtra("necktype"));
        ((AppCompatTextView) findViewById(R.id.des_SleeveType)).setText(getIntent().getStringExtra("sleevetype"));
        ((AppCompatTextView) findViewById(R.id.des_pattern)).setText(getIntent().getStringExtra("pattern"));

        findViewById(R.id.des_addtocart).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (((AppCompatSpinner) findViewById(R.id.des_spinner1)).getSelectedItem().toString().equals("Choose an Option")) {
                    Toast.makeText(Description.this, "Please provide a proper size", Toast.LENGTH_SHORT).show();
                } else if (((AppCompatSpinner) findViewById(R.id.des_spinner2)).getSelectedItem().toString().equals("Choose an Option")) {

                    Toast.makeText(Description.this, "Please choose a valid color", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> transfer = new HashMap<>();
                    Toast.makeText(Description.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    transfer.put("title", getIntent().getStringExtra("productname"));
                    transfer.put("price", getIntent().getStringExtra("price"));
                    transfer.put("discount", getIntent().getStringExtra("discount"));
                    transfer.put("color", ((AppCompatSpinner) findViewById(R.id.des_spinner2)).getSelectedItem().toString());
                    transfer.put("size", ((AppCompatSpinner) findViewById(R.id.des_spinner1)).getSelectedItem().toString());
                    transfer.put("material", getIntent().getStringExtra("material"));
                    transfer.put("first_image", getIntent().getStringExtra("first_image"));
                    transfer.put("val", "true");

                    FirebaseDatabase.getInstance().getReference().child("Cart").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(getIntent().getStringExtra("key")).push().setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                        }
                    });
                    Vibrator vt = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vt.vibrate(100);
                    startActivity(new Intent(Description.this, MyCart.class));
                }

            }
        });

        findViewById(R.id.buynow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Description.this, Review.class));

            }
        });


        ArrayList<String> list = new ArrayList<String>();
        list.add(getIntent().getStringExtra("first_image"));
        list.add(getIntent().getStringExtra("second_image"));
        ImageAdapter imageAdapter = new ImageAdapter(this, list);
        viewPager.setAdapter(imageAdapter);
        CircleIndicator circleIndicator = findViewById(R.id.circle);
        circleIndicator.setViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
