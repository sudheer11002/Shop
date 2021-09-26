package com.thecoderscrew.shop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import com.thecoderscrew.shop.R;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    ArrayList<String> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public ImageAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_slider, container, false);
        AppCompatImageView img = view.findViewById(R.id.image_to_change);
        Picasso.get().load(list.get(position)).into(img);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object==view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        container.refreshDrawableState();
    }
}

