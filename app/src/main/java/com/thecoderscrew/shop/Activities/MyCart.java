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
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecoderscrew.shop.Adapters.MyCartAdapter;
import com.thecoderscrew.shop.Model.Cart_Model;
import com.thecoderscrew.shop.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyCart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout combine, togo;
    private List<String> list = new ArrayList<>();
    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private List<Cart_Model> model = new ArrayList();
    private Toolbar toolbar;
    private  boolean a=false,b=false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cart);

        recyclerView = findViewById(R.id.MyCart_recycler_view);
        combine = findViewById(R.id.combine);
        togo = findViewById(R.id.togo);
        toolbar=findViewById(R.id.MyCart_toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);



        findViewById(R.id.empty_shop_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCart.this, Home.class));
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Cart").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()))
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                if (dataSnapshot.exists()) {
                    togo.setVisibility(View.VISIBLE);
                    combine.setVisibility(View.GONE);
                    for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        list.add(dataSnapshot1.getKey());
                    }
                    addcart();

                }else {
                    togo.setVisibility(View.GONE);
                    combine.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void addcart() {
        list1.clear();
        list2.clear();
        model.clear();
        for (int i = 0; i <= list.size() - 1; i++) {
            final int ii = i;
            FirebaseDatabase.getInstance().getReference().child("Cart").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(list.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                            list1.add(dataSnapshot2.getKey());
                            try {
                                list2.add(list.get(ii));
                            } catch (Exception ignored) {
                            }
                            Cart_Model cart_model1 = dataSnapshot2.getValue(Cart_Model.class);
                            model.add(cart_model1);
                            okdone();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MyCart.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void okdone() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter( new MyCartAdapter(model, list2, list1));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
