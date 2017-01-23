package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderCartItemListHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.order_cart_item_name)
    public TextView orderCartItemName;

    @BindView(R.id.order_cart_item_count)
    public TextView orderCartItemCount;

    @BindView(R.id.order_cart_item_price)
    public TextView orderCartItemPrice;

    public OrderCartItemListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
