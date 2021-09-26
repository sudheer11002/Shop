package com.thecoderscrew.shop.Adapters;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thecoderscrew.shop.Activities.Description;
import com.thecoderscrew.shop.Activities.Description_2;
import com.thecoderscrew.shop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    private List<String> key =  new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private String s, mtitle;
    private DatabaseReference databaseReference;

    public void add(String key, String mtitle) {
        this.key.add(key);
        this.title.add(mtitle);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav, parent, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        mtitle = title.get(position);
        FirebaseDatabase.getInstance().getReference().child("Item").child(mtitle).child(key.get(position)).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(Objects.requireNonNull(dataSnapshot.child("first_image").getValue()).toString()).into(holder.Wishlist_img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                    databaseReference.child("Item").child("TeeShirts").child(key.get(position)).child("clickedstatus").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("Wishlist").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(key.get(position)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(holder.itemView.getContext().getApplicationContext(), "Removed Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = key.get(position);
                mtitle = title.get(position);
                FirebaseDatabase.getInstance().getReference().child("Item").child(mtitle).child(s).addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (mtitle.equals("Hoodies")||mtitle.equals("TeeShirts"))
                        {

                            Intent intent = new Intent(holder.itemView.getContext(), Description.class);
                            intent.putExtra("productname", Objects.requireNonNull(dataSnapshot.child("productname").getValue()).toString());
                            intent.putExtra("material", Objects.requireNonNull(dataSnapshot.child("material").getValue()).toString());
                            intent.putExtra("necktype", Objects.requireNonNull(dataSnapshot.child("necktype").getValue()).toString());
                            intent.putExtra("sleevetype", Objects.requireNonNull(dataSnapshot.child("sleevetype").getValue()).toString());
                            intent.putExtra("pattern", Objects.requireNonNull(dataSnapshot.child("pattern").getValue()).toString());
                            intent.putExtra("price", Objects.requireNonNull(dataSnapshot.child("price").getValue()).toString());
                            intent.putExtra("discount", Objects.requireNonNull(dataSnapshot.child("discount").getValue()).toString());
                            intent.putExtra("deliveryprice", Objects.requireNonNull(dataSnapshot.child("deliveryprice").getValue()).toString());
                            intent.putExtra("first_image", Objects.requireNonNull(dataSnapshot.child("first_image").getValue()).toString());
                            intent.putExtra("second_image", Objects.requireNonNull(dataSnapshot.child("second_image").getValue()).toString());
                            intent.putExtra("key", s);
                            holder.itemView.getContext().startActivity(intent);
                        }
                        else
                            {
                              Intent intent = new Intent(holder.itemView.getContext(), Description_2.class);
                                intent.putExtra("productname", Objects.requireNonNull(dataSnapshot.child("productname").getValue()).toString());
                                intent.putExtra("material", Objects.requireNonNull(dataSnapshot.child("material").getValue()).toString());
                                intent.putExtra("pattern", Objects.requireNonNull(dataSnapshot.child("pattern").getValue()).toString());
                                intent.putExtra("capacity", Objects.requireNonNull(dataSnapshot.child("capacity").getValue()).toString());
                                intent.putExtra("color", Objects.requireNonNull(dataSnapshot.child("color").getValue()).toString());
                                intent.putExtra("price", Objects.requireNonNull(dataSnapshot.child("price").getValue()).toString());
                                intent.putExtra("discount", Objects.requireNonNull(dataSnapshot.child("discount").getValue()).toString());
                                intent.putExtra("deliveryprice", Objects.requireNonNull(dataSnapshot.child("deliveryprice").getValue()).toString());
                                intent.putExtra("key", s);
                                holder.itemView.getContext().startActivity(intent);

                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return key.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        AppCompatImageButton remove;
        AppCompatImageView Wishlist_img;

        MyViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.addtocart);
            remove=view.findViewById(R.id.wishlist_remove);
            Wishlist_img=view.findViewById(R.id.item_img);
        }
    }


}
