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

public class CartListHeadHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.image_delete_button_cart)
    public ImageView imageDeleteButtonCart;

    @BindView(R.id.image_item_src_cart)
    public ImageView imageItemSrcCart;

    @BindView(R.id.text_item_name_cart)
    public TextView textItemNameCart;

    @BindView(R.id.image_minus_button_cart)
    public ImageView imageMinusButtonCart;

    @BindView(R.id.image_plus_button_cart)
    public ImageView imagePlusButtonCart;

    @BindView(R.id.text_item_count_cart)
    public TextView textItemCountCart;

    @BindView(R.id.text_item_price_cart)
    public TextView textItemPriceCart;

    @Inject
    public Picasso picasso;

    public CartListHeadHolder(View view) {
        super(view);

        ((Application) view.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, view);
    }
}
