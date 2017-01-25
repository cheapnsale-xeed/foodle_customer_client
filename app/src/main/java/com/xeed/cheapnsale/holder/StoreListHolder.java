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

public class StoreListHolder extends RecyclerView.ViewHolder {

    @Inject
    public Picasso picasso;

    @BindView(R.id.text_name_store)
    public TextView textNameStore;

    @BindView(R.id.image_src_store)
    public ImageView imageSrcStore;

    @BindView(R.id.text_payment_type_store)
    public TextView textPaymentTypeStore;

    @BindView(R.id.text_prep_time_store)
    public TextView textPrepTimeStore;

    @BindView(R.id.text_arrival_time_store)
    public TextView textArrivalTimeStore;

    @BindView(R.id.view_vertical_bar_store)
    public View viewVerticalBarStore;

    @BindView(R.id.text_distance_store)
    public TextView textDistanceStore;


    public StoreListHolder(View itemView) {
        super(itemView);

        ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, itemView);
    }
}
