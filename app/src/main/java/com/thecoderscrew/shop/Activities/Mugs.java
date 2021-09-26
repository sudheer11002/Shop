package com.thecoderscrew.shop.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecoderscrew.shop.Adapters.HomeAdapter3;
import com.thecoderscrew.shop.Model.Home_Model2;
import com.thecoderscrew.shop.R;

import java.util.Objects;

public class Mugs extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String key;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mugs);
        toolbar=findViewById(R.id.mugs_toolbar);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        FirebaseDatabase.getInstance().getReference().child("Item").child("Mugs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HomeAdapter3 homeAdapter3 = new HomeAdapter3();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    key = dataSnapshot1.getKey();
                    Home_Model2 homeModel2 = dataSnapshot1.getValue(Home_Model2.class);
                    homeAdapter3.add(homeModel2, key);

                }

                recyclerView=findViewById(R.id.mugs_recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                recyclerView.setAdapter( homeAdapter3);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
