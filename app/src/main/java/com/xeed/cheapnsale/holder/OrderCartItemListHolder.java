package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderCartItemListHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_item_name_order)
    public TextView textItemNameOrder;

    @BindView(R.id.text_item_count_order)
    public TextView textItemCountOrder;

    @BindView(R.id.text_item_price_order)
    public TextView textItemPriceOrder;

    public OrderCartItemListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
