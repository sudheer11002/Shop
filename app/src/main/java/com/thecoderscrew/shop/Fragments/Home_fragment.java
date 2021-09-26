package com.thecoderscrew.shop.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecoderscrew.shop.Activities.Bottles;
import com.thecoderscrew.shop.Activities.Hoodies;
import com.thecoderscrew.shop.Activities.Mugs;
import com.thecoderscrew.shop.Activities.TeeShirt;
import com.thecoderscrew.shop.Adapters.HomeAdapter1;
import com.thecoderscrew.shop.Adapters.HomeAdapter2;
import com.thecoderscrew.shop.Adapters.HomeAdapter3;
import com.thecoderscrew.shop.Adapters.HomeAdapter4;
import com.thecoderscrew.shop.Model.Home_Model1;
import com.thecoderscrew.shop.Model.Home_Model2;
import com.thecoderscrew.shop.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment {

    private RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private CardView tees, hoodies, mugs, bottles;
    private Intent intent;
    private String key;
    private DatabaseReference databaseReference;
    private AppCompatTextView see_all_one, see_all_two, see_all_three, see_all_four;
    private View mview;


    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mview = view.findViewById(R.id.home_loading);
        recyclerView1 = view.findViewById(R.id.recycler_view1);
        recyclerView2 = view.findViewById(R.id.recycler_view2);
        recyclerView3 = view.findViewById(R.id.recycler_view3);
        recyclerView4 = view.findViewById(R.id.recycler_view4);
        tees = getView().findViewById(R.id.Home_CardView1);
        hoodies = getView().findViewById(R.id.Home_CardView2);
        mugs = getView().findViewById(R.id.Home_CardView3);
        bottles = getView().findViewById(R.id.Home_CardView4);
        see_all_one = view.findViewById(R.id.see_all_one);
        see_all_two = view.findViewById(R.id.see_all_two);
        see_all_three = view.findViewById(R.id.see_all_three);
        see_all_four = view.findViewById(R.id.see_all_four);

        tees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TeeShirt.class));
            }
        });

        hoodies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Mugs.class));
            }
        });

        mugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Hoodies.class));
            }
        });
        bottles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), Hoodies.class));
            }
        });


        see_all_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TeeShirt.class));
            }
        });

        see_all_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Hoodies.class));
            }
        });

        see_all_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Mugs.class));
            }
        });

        see_all_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Bottles.class));
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
       mview.setVisibility(View.VISIBLE);


        databaseReference.child("Item").child("TeeShirts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HomeAdapter1 homeAdapter1 = new HomeAdapter1();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    key = dataSnapshot1.getKey();
                    Home_Model1 homeModel1 = dataSnapshot1.getValue(Home_Model1.class);
                    homeAdapter1.add(homeModel1, key);

                }

                recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
                recyclerView1.setHasFixedSize(true);
                recyclerView1.setAdapter(homeAdapter1);
                mview.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Item").child("Hoodies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HomeAdapter2 homeAdapter2 = new HomeAdapter2();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    key = dataSnapshot1.getKey();
                    Home_Model1 homeModel1 = dataSnapshot1.getValue(Home_Model1.class);
                    homeAdapter2.add(homeModel1, key);

                }

                recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
                recyclerView2.setHasFixedSize(true);
                recyclerView2.setAdapter(homeAdapter2);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Item").child("Mugs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HomeAdapter3 homeAdapter3 = new HomeAdapter3();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    key = dataSnapshot1.getKey();
                    Home_Model2 homeModel2 = dataSnapshot1.getValue(Home_Model2.class);
                    homeAdapter3.add(homeModel2, key);

                }

                recyclerView3.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
                recyclerView3.setHasFixedSize(true);
                recyclerView3.setAdapter(homeAdapter3);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child("Item").child("Bottles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HomeAdapter4 homeAdapter4 = new HomeAdapter4();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    key = dataSnapshot1.getKey();
                    Home_Model2 homeModel2 = dataSnapshot1.getValue(Home_Model2.class);
                    homeAdapter4.add(homeModel2, key);

                }

                recyclerView4.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
                recyclerView4.setHasFixedSize(true);
                recyclerView4.setAdapter(homeAdapter4);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
