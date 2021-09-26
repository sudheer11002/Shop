
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.thecoderscrew.shop.R;
import java.util.HashMap;
import java.util.Objects;

public class Description_2 extends AppCompatActivity {
private Toolbar toolbar;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_2);
        toolbar=findViewById(R.id.des2_toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);


        ((AppCompatTextView)findViewById(R.id.des_2_title)).setText(getIntent().getStringExtra("productname"));                                  //Displaying the strings on different TextViews
        ((AppCompatTextView)findViewById(R.id.des_2_price)).setText("₹ " + getIntent().getStringExtra("price"));
        ((AppCompatTextView)findViewById(R.id.des_2_discount)).setText(getIntent().getStringExtra("discount") + "% off");
        ((AppCompatTextView)findViewById(R.id.des_2_material)).setText(getIntent().getStringExtra("material"));
        ((AppCompatTextView)findViewById(R.id.des_2_capacity)).setText(getIntent().getStringExtra("capacity"));
        ((AppCompatTextView)findViewById(R.id.des_2_color)).setText(getIntent().getStringExtra("color"));
        ((AppCompatTextView)findViewById(R.id.des_2_pattern)).setText(getIntent().getStringExtra("pattern"));


        findViewById(R.id.des_2_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                   //On Add To Cart Button click it will do the validation first
                new Intent(Description_2.this, MyCart.class).putExtra("key", getIntent().getStringExtra("key"));
              ((AppCompatSpinner)findViewById(R.id.des_2_spinner)).getSelectedItem().toString();

                if (((AppCompatSpinner)findViewById(R.id.des_2_spinner)).equals("Choose an Option")) {
                    Toast.makeText(Description_2.this, "Please choose a valid color", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> transfer = new HashMap<>();
                    Toast.makeText(Description_2.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    transfer.put("title", getIntent().getStringExtra("productname"));
                    transfer.put("price",  getIntent().getStringExtra("price"));
                    transfer.put("material",getIntent().getStringExtra("material"));
                    transfer.put("discount", getIntent().getStringExtra("discount"));
                    transfer.put("color", ((AppCompatSpinner)findViewById(R.id.des_2_spinner)).getSelectedItem().toString());
                    transfer.put("size", getIntent().getStringExtra("capacity"));
                    transfer.put("val","false");

                    FirebaseDatabase.getInstance().getReference().child("Cart").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(getIntent().getStringExtra("key")).push().setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                    Vibrator vt = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vt.vibrate(100);
                    startActivity( new Intent(Description_2.this, MyCart.class));
                    finish();
                }

            }
        });

        findViewById(R.id.des_2_buynow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Description_2.this, Review.class));
                finish();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
