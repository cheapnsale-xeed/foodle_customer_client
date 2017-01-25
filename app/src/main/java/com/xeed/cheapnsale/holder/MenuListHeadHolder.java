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

public class MenuListHeadHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.text_item_price_menu)
    public TextView textItemPriceMenu;

    @BindView(R.id.text_item_name_menu)
    public TextView textItemNameMenu;

    @BindView(R.id.image_item_src_menu)
    public ImageView imageItemSrcMenu;

    @Inject
    public Picasso picasso;

    public MenuListHeadHolder(View view) {
        super(view);

        ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);
        ButterKnife.bind(this, view);
    }
}
