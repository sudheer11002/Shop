package com.thecoderscrew.shop.viewHolders;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.thecoderscrew.shop.Activities.Home;
import com.thecoderscrew.shop.Model.Home_Model1;
import com.thecoderscrew.shop.R;

public  class ItemsViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView textView;
    public ItemsViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_1);

    }

    public void setData(Home homeContext, Home_Model1 model) {
        textView.setText(model.getProductname());
    }
}
