package com.thecoderscrew.shop.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.thecoderscrew.shop.Activities.MyCart;
import com.thecoderscrew.shop.Activities.Review;
import com.thecoderscrew.shop.Model.Cart_Model;
import com.thecoderscrew.shop.R;
import java.util.List;
import java.util.Objects;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {
    private String title, size, color, discount, price, quantity , first_image;
    private Context context;
    private List<Cart_Model> list;
    private List<String> key;
    private List<String> key2;

    public  MyCartAdapter(List<Cart_Model> cart_model1, List<String> keys, List<String> key22) {
        key=keys;
        key2=key22;
        list= cart_model1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart, parent, false);
        context = parent.getContext();
        return new MyCartAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Cart_Model cart_model1 = (Cart_Model) list.get(position);
        title = cart_model1.getTitle();
        size = cart_model1.getSize();
        color = cart_model1.getColor();
        discount = cart_model1.getDiscount();
        price = cart_model1.getPrice();
        first_image=cart_model1.getFirst_image();


        holder.title.setText(title);
        holder.size.setText(size);
        holder.color.setText(color);
        holder.discount.setText(discount + "% off");;
        holder.price.setText("₹ " + price);
        quantity = holder.spinner.getSelectedItem().toString();
        Picasso.get().load(first_image).into(holder.imageView);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
               FirebaseDatabase.getInstance().getReference().child("Cart").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()))
                .child(key.get(position)).child(key2.get(position)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Product removed successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        holder.clickitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Review.class);
                intent.putExtra("title", title);
                intent.putExtra("size", size);
                intent.putExtra("color", color);
                intent.putExtra("discount", discount);
                intent.putExtra("price", price);
                intent.putExtra("quantity", quantity);
                intent.putExtra("key1",key.get(position));
                intent.putExtra("key2",key2.get(position));
                intent.putExtra("first_image",first_image);
                context.startActivity(intent);

                ((MyCart)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, size, color, discount, price;
        AppCompatImageView imageView;
        LinearLayout clickitem;
        AppCompatButton remove;
        AppCompatSpinner spinner;

        public MyViewHolder(@NonNull View view) {
            super(view);
            clickitem = view.findViewById(R.id.itemclick);
            spinner = view.findViewById(R.id.cart_spinner);
            title = view.findViewById(R.id.cart_txt1);
            price = view.findViewById(R.id.cart_txt2);
            discount = view.findViewById(R.id.cart_txt3);
            color = view.findViewById(R.id.cart_txt5);
            size = view.findViewById(R.id.cart_txt6);
            remove = view.findViewById(R.id.cart_btn);
            imageView = view.findViewById(R.id.cart_img);
        }
    }
}
