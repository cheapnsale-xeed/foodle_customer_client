package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

public class ExpandableMenuChildHolder extends RecyclerView.ViewHolder{

    public ImageButton itemMinus;
    public ImageButton itemPlus;
    public TextView itemCountText;
    public TextView itemTotalPriceText;
    public Button itemAddCartButton;
    public Button itemOrderNowButton;

    public ExpandableMenuChildHolder(View view) {
        super(view);

        itemMinus = (ImageButton) view.findViewById(R.id.item_minus);
        itemPlus = (ImageButton)view.findViewById(R.id.item_plus);
        itemCountText = (TextView) view.findViewById(R.id.item_count_text);
        itemTotalPriceText = (TextView) view.findViewById(R.id.selected_item_total_price);
        itemAddCartButton = (Button) view.findViewById(R.id.menu_item_add_cart);
        itemOrderNowButton = (Button) view.findViewById(R.id.menu_item_order_now);
    }
}
