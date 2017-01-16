package com.xeed.cheapnsale.holder;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.StoreDetailActivity;

public class ExpandableMenuChildHolder extends RecyclerView.ViewHolder{

    public ImageButton itemMinus;
    public ImageButton itemPlus;
    public TextView itemCountText;
    public TextView itemTotalPriceText;
    public Button itemAddCart;

    public StoreDetailActivity storeDetailActivity;
    public RelativeLayout storeDetailLayout;
    public CoordinatorLayout storeCoordinatorLayout;

    public ExpandableMenuChildHolder(View view) {
        super(view);

        itemMinus = (ImageButton) view.findViewById(R.id.item_minus);
        itemPlus = (ImageButton)view.findViewById(R.id.item_plus);
        itemCountText = (TextView) view.findViewById(R.id.item_count_text);
        itemTotalPriceText = (TextView) view.findViewById(R.id.selected_item_total_price);
        itemAddCart = (Button) view.findViewById(R.id.item_add_cart);

        storeDetailActivity = (StoreDetailActivity) view.getContext();
        storeDetailLayout = (RelativeLayout) ((StoreDetailActivity) view.getContext()).findViewById(R.id.store_detail_layout);
        storeCoordinatorLayout = (CoordinatorLayout) ((StoreDetailActivity) view.getContext()).findViewById(R.id.coordinator);
    }
}
