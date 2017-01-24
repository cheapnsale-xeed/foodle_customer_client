package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableMenuChildHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.item_minus)
    public ImageButton itemMinus;

    @BindView(R.id.item_plus)
    public ImageButton itemPlus;

    @BindView(R.id.item_count_text)
    public TextView itemCountText;

    @BindView(R.id.selected_item_total_price)
    public TextView itemTotalPriceText;

    @BindView(R.id.menu_item_add_cart)
    public Button itemAddCartButton;

    @BindView(R.id.menu_item_order_now)
    public Button itemOrderNowButton;

    public ExpandableMenuChildHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
