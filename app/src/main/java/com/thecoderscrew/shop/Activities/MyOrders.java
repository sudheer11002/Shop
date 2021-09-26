package com.thecoderscrew.shop.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecoderscrew.shop.Adapters.MyOrderAdapter;
import com.thecoderscrew.shop.Model.MyOrders_Model;
import com.thecoderscrew.shop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyOrders extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private List<MyOrders_Model> model = new ArrayList();
    private Toolbar toolbar;
    private AppCompatButton No_orders_button;
    private LinearLayout linearLayout;

    MyOrderAdapter myOrderAdapter;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);
        toolbar = findViewById(R.id.MyOrders_toolbar);
        linearLayout=findViewById(R.id.No_Orders_Screen);
        No_orders_button=findViewById(R.id.empty_shop_now);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        recyclerView = findViewById(R.id.MyOrders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                 linearLayout.setVisibility(View.GONE);
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        list.add(dataSnapshot1.getKey());

                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            MyOrders_Model myOrders_model = dataSnapshot2.getValue(MyOrders_Model.class);

                            model.add(myOrders_model);
                        }
                    }

                    notifyAdapter();

                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        No_orders_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });
    }



    private void notifyAdapter() {
        if ( myOrderAdapter == null) {
            myOrderAdapter = new MyOrderAdapter(MyOrders.this, model, list);
            recyclerView.setAdapter(myOrderAdapter);
        } else {
            myOrderAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
