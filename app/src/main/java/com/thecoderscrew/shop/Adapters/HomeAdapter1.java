package com.thecoderscrew.shop.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.thecoderscrew.shop.Model.Home_Model1;
import com.thecoderscrew.shop.R;
import com.thecoderscrew.shop.viewHolders.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter1 extends RecyclerView.Adapter<MyViewHolder> {
    private List<Home_Model1> list = new ArrayList();
    private List getkey = new ArrayList();

    public void add(Home_Model1 homeModel1, String key) {
        list.add(homeModel1);
        getkey.add(key);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        Context context = parent.getContext();
        return new MyViewHolder(view , context);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(list.get(position),getkey.get(position).toString());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}

