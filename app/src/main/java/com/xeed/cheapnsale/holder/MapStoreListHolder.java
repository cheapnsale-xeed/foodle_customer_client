package com.xeed.cheapnsale.holder;

import android.support.v7.widget.CardView;
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

    @BindView(R.id.image_store_src_map)
    public ImageView imageStoreSrcMap;

    @BindView(R.id.text_store_name_map)
    public TextView textStoreNameMap;

    @BindView(R.id.text_avg_prep_time_map)
    public TextView textAvgPrepTimeMap;

    @BindView(R.id.text_distance_map)
    public TextView textDistanceMap;

    @BindView(R.id.text_arrival_time_map)
    public TextView textArrivalTimeMap;

    @BindView(R.id.view_vertical_bar_map)
    public View viewVerticalBarMap;

    @BindView(R.id.card_view_map_store)
    public CardView cardViewMapStore;

    @Inject
    public Picasso picasso;

    public MapStoreListHolder(View view) {
        super(view);

        ((Application) view.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, view);
    }
}
