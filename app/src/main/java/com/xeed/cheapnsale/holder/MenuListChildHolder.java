package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuListChildHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.image_minus_button_menu)
    public ImageView imageMinusButtonMenu;

    @BindView(R.id.image_plus_button_menu)
    public ImageView imagePlusButtonMenu;

    @BindView(R.id.text_item_count_menu)
    public TextView textItemCountMenu;

    @BindView(R.id.text_total_price_menu)
    public TextView textTotalPriceMenu;

    @BindView(R.id.button_add_cart_menu)
    public Button buttonAddCartMenu;

    @BindView(R.id.button_order_now_menu)
    public Button buttonOrderNowMenu;

    public MenuListChildHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
