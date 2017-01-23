package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;

import javax.inject.Inject;

public class MapStoreListHolder extends RecyclerView.ViewHolder{
    public ImageView itemImage;
    public TextView itemName;
    public TextView itemAvgPrepTime;

    @Inject
    public Picasso picasso;

    public MapStoreListHolder(View view) {
        super(view);

        ((Application) view.getContext().getApplicationContext()).getApplicationComponent().inject(this);

        itemImage = (ImageView) view.findViewById(R.id.map_store_img);
        itemName = (TextView) view.findViewById(R.id.map_store_name);
        itemAvgPrepTime = (TextView) view.findViewById(R.id.map_store_avg_prep_time);

    }
}
