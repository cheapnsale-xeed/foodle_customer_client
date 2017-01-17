package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

public class ExpandableMenuListHolder extends RecyclerView.ViewHolder{
    public TextView itemPrice;
    public TextView itemName;
    public ImageView itemImage;

    public ExpandableMenuListHolder(View view) {
        super(view);

        itemName = (TextView) view.findViewById(R.id.menu_item_name);
        itemImage = (ImageView)view.findViewById(R.id.menu_item_image);
        itemPrice = (TextView) view.findViewById(R.id.menu_item_price);
    }
}
