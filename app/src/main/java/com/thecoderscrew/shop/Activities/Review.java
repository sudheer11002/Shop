package com.thecoderscrew.shop.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.thecoderscrew.shop.R;

import java.util.Objects;

public class Review extends AppCompatActivity {
    private AppCompatSpinner spinner;
    private AppCompatButton add_address, next;
    private TextInputEditText editText;
    private LottieAnimationView lottieAnimationView;
    private String quantity;
    AppCompatImageView imageView;
    private Toolbar toolbar;
    AppCompatTextView productTitle, productSize, productColor, productPrice, totalPrice, discount, productQuantity, deliveryPrice,displayPrice;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        spinner = findViewById(R.id.review_spinner);
        imageView = findViewById(R.id.review_image);
        toolbar = findViewById(R.id.Review_toolbar);
        productTitle = findViewById(R.id.review_text1);
        productSize = findViewById(R.id.review_text55);
        productColor = findViewById(R.id.review_text3);
        discount = findViewById(R.id.review_text8);
        productPrice = findViewById(R.id.review_text6);
        productQuantity = findViewById(R.id.review_2_txt7);
        deliveryPrice = findViewById(R.id.review_2_txt8);
        displayPrice = findViewById(R.id.review_2_txt6);
        totalPrice = findViewById(R.id.review_2_txt10);
        quantity = spinner.getSelectedItem().toString();

        productTitle.setText(getIntent().getStringExtra("title"));
        productSize.setText(getIntent().getStringExtra("size"));
        productColor.setText(getIntent().getStringExtra("color"));
        discount.setText(getIntent().getStringExtra("discount") + "% off");
        productPrice.setText("₹" + getIntent().getStringExtra("price"));
        productQuantity.setText(getIntent().getStringExtra("quantity"));
        productPrice.setText(getIntent().getStringExtra("price"));
        deliveryPrice.setText("₹"+"45");

        final String key1 = getIntent().getStringExtra("key1");
        final String key2 = getIntent().getStringExtra("key2");

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);


        findViewById(R.id.review_continue).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(), AddressAndPaymentMode.class);
                pass.putExtra("title", getIntent().getStringExtra("title"));
                pass.putExtra("size", getIntent().getStringExtra("size"));
                pass.putExtra("color", getIntent().getStringExtra("color"));
                pass.putExtra("discount", getIntent().getStringExtra("discount"));
                pass.putExtra("price", getIntent().getStringExtra("price"));
                pass.putExtra("quantity", getIntent().getStringExtra("quantity"));
                pass.putExtra("first_image", getIntent().getStringExtra("first_image"));
                pass.putExtra("key", key1);
                pass.putExtra("key1", key2);
                startActivity(pass);
                finish();

            }
        });

        findViewById(R.id.review_remove).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Cart").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()))
                        .child(key1).child(key2).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Product removed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                startActivity(new Intent(Review.this, MyCart.class));
                finish();
            }
        });


        Picasso.get().load(getIntent().getStringExtra("first_image")).into(imageView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
