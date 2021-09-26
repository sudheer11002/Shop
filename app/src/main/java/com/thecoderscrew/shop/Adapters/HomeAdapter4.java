package com.thecoderscrew.shop.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thecoderscrew.shop.Activities.Description_2;
import com.thecoderscrew.shop.Model.Home_Model2;
import com.thecoderscrew.shop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeAdapter4 extends RecyclerView.Adapter<HomeAdapter4.MyViewHolder> {

    private String productname, material, capacity, color, pattern, price, discount, deliveryprice, user;
    private DatabaseReference databaseReference;
    private HashMap<String, String> transfer = new HashMap<>();
    private List list = new ArrayList();
    private List getkey = new ArrayList();

    public void add(Home_Model2 homeModel2, String key) {
        list.add(homeModel2);
        getkey.add(key);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        AppCompatImageView heart;

        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.text_1);
            price = view.findViewById(R.id.text_2);
            heart = view.findViewById(R.id.heart);
            user = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            databaseReference = FirebaseDatabase.getInstance().getReference();

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new MyViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Home_Model2 homeModel2 = (Home_Model2) list.get(position);
        productname = homeModel2.getProductname();
        material = homeModel2.getMaterial();
        capacity = homeModel2.getCapacity();
        color = homeModel2.getColor();
        pattern = homeModel2.getPattern();
        price = homeModel2.getPrice();
        discount = homeModel2.getDiscount();
        deliveryprice = homeModel2.getDeliveryprice();

        holder.title.setText(productname);
        holder.price.setText("₹" + price);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_Model2 homeModel2 = (Home_Model2) list.get(position);
                productname = homeModel2.getProductname();
                material = homeModel2.getMaterial();
                price = homeModel2.getPrice();
                color = homeModel2.getColor();
                pattern = homeModel2.getPattern();
                discount = homeModel2.getDiscount();
                deliveryprice = homeModel2.getDeliveryprice();

                Intent intent = new Intent(v.getContext(), Description_2.class);
                intent.putExtra("productname", productname);
                intent.putExtra("material", material);
                intent.putExtra("capacity", capacity);
                intent.putExtra("color", color);
                intent.putExtra("pattern", pattern);
                intent.putExtra("price", price);
                intent.putExtra("discount", discount);
                intent.putExtra("deliveryprice", deliveryprice);
                intent.putExtra("key", (String) getkey.get(position));
                v.getContext().startActivity(intent);
            }
        });

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.heart.setImageResource(R.drawable.dill);
                transfer.put("title", "Bottles");
                transfer.put("clicked","true");
                databaseReference.child("Wishlist").child(user).child((String) getkey.get(position)).setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

