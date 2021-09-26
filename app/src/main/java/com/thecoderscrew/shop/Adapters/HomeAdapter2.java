package com.thecoderscrew.shop.Adapters;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thecoderscrew.shop.Activities.Description;
import com.thecoderscrew.shop.Model.Home_Model1;
import com.thecoderscrew.shop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class HomeAdapter2 extends RecyclerView.Adapter<HomeAdapter2.MyViewHolder> {

    private String productname, material, necktype, sleevetype, pattern, price, discount, deliveryprice;
    private DatabaseReference databaseReference;
    private HashMap<String, String> transfer = new HashMap<>();
    private List list = new ArrayList();
    private List getkey = new ArrayList();

    public void add(Home_Model1 homeModel1, String key) {
        list.add(homeModel1);
        getkey.add(key);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        AppCompatImageView heart;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.text_1);
            price = view.findViewById(R.id.text_2);
            heart = view.findViewById(R.id.heart);
            databaseReference = FirebaseDatabase.getInstance().getReference();

        }

        ;

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Home_Model1 homeModel1 = (Home_Model1) list.get(position);
        productname = homeModel1.getProductname();
        material = homeModel1.getMaterial();
        necktype = homeModel1.getNecktype();
        sleevetype = homeModel1.getSleevetype();
        pattern = homeModel1.getPattern();
        price = homeModel1.getPrice();
        discount = homeModel1.getDiscount();
        deliveryprice = homeModel1.getDeliveryprice();

        holder.title.setText(productname);
        holder.price.setText("₹" + price);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_Model1 homeModel1 = (Home_Model1) list.get(position);
                productname = homeModel1.getProductname();
                material = homeModel1.getMaterial();
                necktype = homeModel1.getNecktype();
                sleevetype = homeModel1.getSleevetype();
                pattern = homeModel1.getPattern();
                price = homeModel1.getPrice();
                discount = homeModel1.getDiscount();
                deliveryprice = homeModel1.getDeliveryprice();

                Intent intent = new Intent(v.getContext(), Description.class);
                intent.putExtra("productname", productname);
                intent.putExtra("material", material);
                intent.putExtra("necktype", necktype);
                intent.putExtra("sleevetype", sleevetype);
                intent.putExtra("pattern", pattern);
                intent.putExtra("price", price);
                intent.putExtra("discount", discount);
                intent.putExtra("deliveryprice", deliveryprice);
                intent.putExtra("key", (String) getkey.get(position));
                v.getContext().startActivity(intent);
            }
        });

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                int cliked = 0;
                if (cliked == 0) {
                    holder.heart.setImageResource(R.drawable.dill);
                    transfer.put("title", "Hoodies");
                    transfer.put("clicked", "true");
                    databaseReference.child("Wishlist").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child((String) getkey.get(position)).setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                    cliked++;
                } else if (cliked == 1) {
                    holder.heart.setImageResource(R.drawable.dill);
                    transfer.put("title", "Hoodies");
                    transfer.put("clicked", "true");
                    databaseReference.child("Wishlist").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()).child((String) getkey.get(position)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                    cliked--;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

