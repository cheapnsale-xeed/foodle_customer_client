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

public class CartListHolder extends RecyclerView.ViewHolder{
    public ImageButton deleteButton;
    public ImageView itemImage;
    public TextView itemName;
    public ImageButton itemMinus;
    public ImageButton itemPlus;
    public TextView itemCountText;
    public TextView itemTotalPriceText;

    @Inject
    public Picasso picasso;

    public CartListHolder(View view) {
        super(view);

        ((Application) view.getContext().getApplicationContext()).getApplicationComponent().inject(this);

        deleteButton = (ImageButton) view.findViewById(R.id.cart_item_delete);
        itemImage = (ImageView) view.findViewById(R.id.cart_item_img);
        itemName = (TextView) view.findViewById(R.id.cart_item_name);
        itemMinus = (ImageButton) view.findViewById(R.id.cart_item_minus);
        itemPlus = (ImageButton)view.findViewById(R.id.cart_item_plus);
        itemCountText = (TextView) view.findViewById(R.id.cart_item_count_text);
        itemTotalPriceText = (TextView) view.findViewById(R.id.cart_item_price);
    }
}
