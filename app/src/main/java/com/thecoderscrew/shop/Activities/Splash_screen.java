package com.thecoderscrew.shop.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecoderscrew.shop.R;

import java.util.Objects;

public class Splash_screen extends AppCompatActivity {
    private boolean detailsExists = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        FirebaseDatabase.getInstance().getReference().child("User").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    detailsExists = false;
                } else {
                    detailsExists = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    if (FirebaseAuth.getInstance().getCurrentUser() == null || !detailsExists) {
                        startActivity(new Intent(Splash_screen.this, Login.class));
                        finish();
                    } else {
                        startActivity(new Intent(Splash_screen.this, Home.class));
                        finish();
                    }

                } catch (Exception ignored) {
                }
            }
        }.start();

    }
}
