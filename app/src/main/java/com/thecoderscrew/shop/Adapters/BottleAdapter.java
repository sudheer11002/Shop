package com.thecoderscrew.shop.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.thecoderscrew.shop.Activities.Description;
import com.thecoderscrew.shop.R;

public class BottleAdapter extends RecyclerView.Adapter<BottleAdapter.MyViewHolder> {


    private int[] image;
    private String[] name;

    public BottleAdapter(int[] image, String[] name) {
        this.image = image;
        this.name = name;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), image[position]));
        holder.title.setText(name[position]);
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.text_1);
            imageView = view.findViewById(R.id.card_imgview);

        }

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Description.class);
            v.getContext().startActivity(intent);

        }
    };
}
