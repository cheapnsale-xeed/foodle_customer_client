package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.cart_item_delete)
    public ImageButton deleteButton;

    @BindView(R.id.cart_item_img)
    public ImageView itemImage;

    @BindView(R.id.cart_item_name)
    public TextView itemName;

    @BindView(R.id.cart_item_minus)
    public ImageButton itemMinus;

    @BindView(R.id.cart_item_plus)
    public ImageButton itemPlus;

    @BindView(R.id.cart_item_count_text)
    public TextView itemCountText;

    @BindView(R.id.cart_item_price)
    public TextView itemTotalPriceText;

    @Inject
    public Picasso picasso;

    public CartListHolder(View view) {
        super(view);

        ((Application) view.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, view);
    }
}
