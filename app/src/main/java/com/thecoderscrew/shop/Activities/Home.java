package com.thecoderscrew.shop.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecoderscrew.shop.Fragments.Home_fragment;
import com.thecoderscrew.shop.Fragments.MyProfile;
import com.thecoderscrew.shop.R;

import java.util.Objects;

public class Home extends AppCompatActivity {
    private FragmentTransaction fragmentTransaction;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private AppCompatTextView Nav_Header_Text, Nav_Header_Mobile_No;
    private View view;
    private String first_name, last_name, mobile_no;
    private LinearLayout linearLayout;
    private AppCompatEditText Search;
    BottomNavigationView bottomNavigationView;
    FrameLayout Fragment_Container;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setUp();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Fragment_Container, new Home_fragment());
        fragmentTransaction.commit();
        Fragment_Container = findViewById(R.id.Fragment_Container);
        navigationView = findViewById(R.id.Nav_Drawer);
        view = navigationView.getHeaderView(0);
        Nav_Header_Text = view.findViewById(R.id.Nav_Header_Text);
        linearLayout = findViewById(R.id.Tool_Barl);
        Nav_Header_Mobile_No = view.findViewById(R.id.Nav_Header_Mobile_No);
        Search = findViewById(R.id.Search);
        bottomNavigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.Tool_Bar);
        getdata();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        toolbar.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.animation, R.anim.second);
                        fragmentTransaction.replace(R.id.Fragment_Container, new Home_fragment());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_person:
                        toolbar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.animation, R.anim.second);
                        fragmentTransaction.replace(R.id.Fragment_Container, new MyProfile());
                        fragmentTransaction.commit();
                        return true;
                }
                return false;
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, WishList.class));
            }
        });

        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, MyCart.class));
            }
        });

        Search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&(keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String SearchedText = Objects.requireNonNull(Search.getText()).toString();

                    if(!SearchedText.equals(""))
                    {
                        startActivity(new Intent(Home.this, SearchResultList.class).putExtra("searchedtext", SearchedText));
                        Search.setText("");
                    }

                    return true;
                }

                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {

                    case R.id.nav_Home:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Fragment_Container, new Home_fragment());
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_MyOrders:
                        startActivity(new Intent(Home.this, MyOrders.class));
                        break;
                    case R.id.nav_WishList:
                        startActivity(new Intent(Home.this, WishList.class));
                        break;
                    case R.id.nav_MyProfile:
                        toolbar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        bottomNavigationView.setSelectedItemId(R.id.navigation_person);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.Fragment_Container, new MyProfile());
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_ContactUs:
                        startActivity(new Intent(Home.this, Contact_us.class));
                        break;
                    case R.id.nav_SignOut:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Home.this, Login.class));
                        break;
                }
                return false;
            }
        });


    }

    private void setUp() {                                        //Setting up the Toolbar
        toolbar = findViewById(R.id.Tool_Bar);
        drawerLayout = findViewById(R.id.Drawer_Layout);
        navigationView = findViewById(R.id.Nav_Drawer);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getdata() {                                     //Getting the value of name from Database
        FirebaseDatabase.getInstance().getReference().child("User").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                first_name = Objects.requireNonNull(dataSnapshot.child("firstname").getValue()).toString();
                last_name = Objects.requireNonNull(dataSnapshot.child("lastname").getValue()).toString();
                mobile_no = Objects.requireNonNull(dataSnapshot.child("mobileno").getValue()).toString();
                Nav_Header_Text.setText(first_name + " " + last_name);
                Nav_Header_Mobile_No.setText("(" + mobile_no + ")");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                view = navigationView.getHeaderView(0);
                Nav_Header_Text.setText("User");
                Nav_Header_Mobile_No.setText("xxxxx-xxxxx");
            }
        });
    }

    // View Holder Class for Search


}
