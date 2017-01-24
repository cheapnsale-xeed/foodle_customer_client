package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableMenuListHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.menu_item_price)
    public TextView itemPrice;

    @BindView(R.id.menu_item_name)
    public TextView itemName;

    @BindView(R.id.menu_item_image)
    public ImageView itemImage;

    @Inject
    public Picasso picasso;

    public ExpandableMenuListHolder(View view) {
        super(view);

        ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, view);
    }
}
