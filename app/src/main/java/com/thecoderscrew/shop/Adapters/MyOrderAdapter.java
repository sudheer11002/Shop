package com.thecoderscrew.shop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thecoderscrew.shop.Activities.OrderDetails;
import com.thecoderscrew.shop.Model.MyOrders_Model;
import com.thecoderscrew.shop.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {
    private String title, size, color, discount, price, quantity, first_image;
    private Context context;
    private List<MyOrders_Model> list;
    private List<String> key;

    public MyOrderAdapter(Context context, List<MyOrders_Model> myorders_model, List<String> keys) {
        key = keys;
        list = myorders_model;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.productTitle.setText(list.get(position).getProductDetails().getTitle());
        holder.productSize.setText(list.get(position).getProductDetails().getSize());
        holder.productColor.setText(list.get(position).getProductDetails().getColor());
        holder.productQuantity.setText(list.get(position).getProductDetails().getQuantity());
        holder.productDiscount.setText(list.get(position).getProductDetails().getDiscount());
        holder.productPrice.setText(list.get(position).getProductDetails().getPrice());
        MyOrders_Model myOrders_model = (MyOrders_Model) list.get(position);
        String product_Image = myOrders_model.getProductDetails().getFirst_image();

        Picasso.get().load(product_Image).into(holder.productImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, OrderDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle, productSize, productColor, productDiscount, productQuantity, productPrice;
        ImageView productImage;
        CardView cardView;

        MyViewHolder(@NonNull View view) {
            super(view);
            productTitle = view.findViewById(R.id.My_Orders_Product_Title);
            productSize = view.findViewById(R.id.My_Orders_Product_Size);
            productColor = view.findViewById(R.id.My_Orders_Product_Color);
            productDiscount = view.findViewById(R.id.My_Orders_Product_Discount);
            productQuantity = view.findViewById(R.id.My_Orders_Product_Quantity_Value);
            productPrice = view.findViewById(R.id.My_Orders_Product_Price);
            cardView = view.findViewById(R.id.My_Orders_CardView);
            productImage = view.findViewById(R.id.My_Orders_Product_Image);
        }
    }
}
