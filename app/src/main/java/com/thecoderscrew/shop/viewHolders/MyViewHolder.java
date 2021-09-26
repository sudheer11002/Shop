package com.thecoderscrew.shop.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
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
import com.squareup.picasso.Picasso;
import com.thecoderscrew.shop.Activities.Description;
import com.thecoderscrew.shop.Model.Home_Model1;
import com.thecoderscrew.shop.R;

import java.util.HashMap;
import java.util.Objects;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private String productname;
    private String material;
    private String necktype;
    private String sleevetype;
    private String pattern;
    private String price;
    private String discount;
    private String deliveryprice;
    private String clickedstatus;
    private DatabaseReference databaseReference;
    private HashMap<String, String> transfer = new HashMap<>();


    View view;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MyViewHolder(View view , Context context) {
        super(view);
        this.view = view;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.context=context;
    }


    public void setData(final Home_Model1 homeModel1, final String key) {
        productname = homeModel1.getProductname();
        material = homeModel1.getMaterial();
        necktype = homeModel1.getNecktype();
        sleevetype = homeModel1.getSleevetype();
        pattern = homeModel1.getPattern();
        price = homeModel1.getPrice();
        discount = homeModel1.getDiscount();
        deliveryprice = homeModel1.getDeliveryprice();
        final String first_image = homeModel1.getFirst_Image();
        final String second_image = homeModel1.getSecond_Image();
        clickedstatus = homeModel1.getClickedstatus();

        TextView titleTV = view.findViewById(R.id.text_1);
        TextView priceTV = view.findViewById(R.id.text_2);
        final AppCompatImageView heart = view.findViewById(R.id.heart);

        AppCompatImageView first_imageAIV = view.findViewById(R.id.card_imgview);

        if (clickedstatus.equalsIgnoreCase("1")) {
            heart.setImageResource(R.drawable.dill);
            clickedstatus="0";
        } else if ( clickedstatus.equalsIgnoreCase("0")) {
            heart.setImageResource(R.drawable.dil);
            clickedstatus = "1";
        }

        titleTV.setText(productname);
        priceTV.setText("₹" + price);
        Picasso.get().load(first_image).into(first_imageAIV);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productname = homeModel1.getProductname();
                material = homeModel1.getMaterial();
                necktype = homeModel1.getNecktype();
                sleevetype = homeModel1.getSleevetype();
                pattern = homeModel1.getPattern();
                price = homeModel1.getPrice();
                discount = homeModel1.getDiscount();
                deliveryprice = homeModel1.getDeliveryprice();
                clickedstatus = homeModel1.getClickedstatus();

                Intent intent = new Intent(v.getContext(), Description.class);
                intent.putExtra("productname", productname);
                intent.putExtra("material", material);
                intent.putExtra("necktype", necktype);
                intent.putExtra("sleevetype", sleevetype);
                intent.putExtra("pattern", pattern);
                intent.putExtra("price", price);
                intent.putExtra("discount", discount);
                intent.putExtra("deliveryprice", deliveryprice);
                intent.putExtra("key", (String) key);
                intent.putExtra("first_image", first_image);
                intent.putExtra("second_image", second_image);
                v.getContext().startActivity(intent);
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if (clickedstatus.equalsIgnoreCase("1")) {
                    heart.setImageResource(R.drawable.dill);
                    transfer.put("title", "TeeShirts");

                    databaseReference.child("Wishlist").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(key).setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });


                    databaseReference.child("Item").child("TeeShirts").child(key).child("clickedstatus").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });

                }
                if (clickedstatus .equalsIgnoreCase("0")) {
                    heart.setImageResource(R.drawable.dill);
                    transfer.put("title", "TeeShirts");

                    databaseReference.child("Wishlist").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child((key)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });

                    databaseReference.child("Item").child("TeeShirts").child(key).child("clickedstatus").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });

                }

            }
        });


    }

}