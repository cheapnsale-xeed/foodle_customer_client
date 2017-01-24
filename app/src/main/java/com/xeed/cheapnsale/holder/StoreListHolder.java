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

    @BindView(R.id.store_name_view)
    public TextView nameView;

    @BindView(R.id.store_image_view)
    public ImageView mainImgView;

    @BindView(R.id.payment_type_view)
    public TextView paymentTextView;

    @BindView(R.id.avg_prep_time_view)
    public TextView avgPrepTimeTextView;

    @BindView(R.id.text_arrival_time_in_store)
    public TextView textArrivalTimeInStore;

    @BindView(R.id.view_divide_bar_in_store)
    public View viewDivideBarInStore;

    @BindView(R.id.text_distance_to_store_in_store)
    public TextView textDistanceToStoreInStore;


    public StoreListHolder(View itemView) {
        super(itemView);

        ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, itemView);
    }
}
