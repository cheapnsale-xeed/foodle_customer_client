package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapStoreListHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.map_store_img)
    public ImageView itemImage;

    @BindView(R.id.map_store_name)
    public TextView itemName;

    @BindView(R.id.map_store_avg_prep_time)
    public TextView itemAvgPrepTime;

    @BindView(R.id.text_distance_to_store_in_map)
    public TextView textDistanceToStore;

    @BindView(R.id.text_arrival_time_in_map)
    public TextView textArrivalTime;

    @BindView(R.id.view_divide_bar_in_map)
    public View viewDivideBarInMapActivity;

    @Inject
    public Picasso picasso;

    public MapStoreListHolder(View view) {
        super(view);

        ((Application) view.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, view);
    }
}
