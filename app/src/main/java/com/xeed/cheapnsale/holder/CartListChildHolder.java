package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListChildHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_store_name_cart)
    public TextView textStoreNameCart;

    @BindView(R.id.linear_add_menu_cart)
    public LinearLayout linearAddMenuCart;

    public CartListChildHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
