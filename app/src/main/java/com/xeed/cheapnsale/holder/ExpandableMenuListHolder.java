package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;

import javax.inject.Inject;

public class ExpandableMenuListHolder extends RecyclerView.ViewHolder{
    public TextView itemPrice;
    public TextView itemName;
    public ImageView itemImage;

    @Inject
    public Picasso picasso;

    public ExpandableMenuListHolder(View view) {
        super(view);

        ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);

        itemName = (TextView) view.findViewById(R.id.menu_item_name);
        itemImage = (ImageView)view.findViewById(R.id.menu_item_image);
        itemPrice = (TextView) view.findViewById(R.id.menu_item_price);
    }
}
