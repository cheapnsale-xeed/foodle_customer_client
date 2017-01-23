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

    @BindView(R.id.store_name_view)
    public TextView nameView;

    @BindView(R.id.store_image_view)
    public ImageView mainImgView;

    @BindView(R.id.payment_type_view)
    public TextView paymentTextView;

    @BindView(R.id.avg_prep_time_view)
    public TextView avgPrepTimeTextView;

    @Inject
    public Picasso picasso;

    public StoreListHolder(View itemView) {
        super(itemView);

        ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, itemView);
    }
}
