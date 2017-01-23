package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.CartActivity;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.CartListHolder;
import com.xeed.cheapnsale.holder.MapStoreListHolder;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.vo.Cart;
import com.xeed.cheapnsale.vo.CartItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MapStoreListAdapter extends RecyclerView.Adapter<MapStoreListHolder> {

    private Context context;
    ArrayList<Store> stores;

    public MapStoreListAdapter(Context context, ArrayList<Store> stores) {
        this.context = context;
        this.stores = stores;

        Application app = ((Application) context.getApplicationContext());
    }

    @Override
    public MapStoreListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_map_store_list_card, null);

        return new MapStoreListHolder(view);
    }

    @Override
    public void onBindViewHolder(final MapStoreListHolder holder, final int position) {

        holder.itemName.setText(stores.get(position).getName());
        holder.picasso.with(context).load(stores.get(position).getMainImageUrl())
                .transform(new CropCircleTransformation()).into(holder.itemImage);
        holder.itemAvgPrepTime.setText(stores.get(position).getAvgPrepTime() + context.getResources().getString(R.string.minute));

    }

    @Override
    public int getItemCount() {
        return stores.size();
    }
}





