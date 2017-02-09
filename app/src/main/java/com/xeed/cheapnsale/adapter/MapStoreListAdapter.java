package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.StoreDetailActivity;
import com.xeed.cheapnsale.holder.MapStoreListHolder;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MapStoreListAdapter extends RecyclerView.Adapter<MapStoreListHolder> {

    private Context context;
    private ArrayList<Store> stores;

    public MapStoreListAdapter(Context context, ArrayList<Store> stores) {
        this.context = context;
        this.stores = stores;
    }

    @Override
    public MapStoreListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list_map, null);

        return new MapStoreListHolder(view);
    }

    @Override
    public void onBindViewHolder(final MapStoreListHolder holder, final int position) {

        holder.textStoreNameMap.setText(stores.get(position).getName());
        holder.picasso.load(stores.get(position).getMainImageUrl()).transform(new CropCircleTransformation()).into(holder.imageStoreSrcMap);
        holder.textAvgPrepTimeMap.setText(stores.get(position).getAvgPrepTime() + context.getResources().getString(R.string.minute));

        int distance = stores.get(position).getDistanceToStore();
        int arrivalTime = distance / 60;

        if (distance > 1000) {
            holder.textDistanceMap.setText(((double)stores.get(position).getDistanceToStore()/1000) + "km");
        } else {
            holder.textDistanceMap.setText(stores.get(position).getDistanceToStore() + "m");
        }
        if (distance > 1500) {
            holder.textArrivalTimeMap.setVisibility(View.GONE);
            holder.viewVerticalBarMap.setVisibility(View.GONE);
        } else {
            holder.textArrivalTimeMap.setVisibility(View.VISIBLE);
            holder.viewVerticalBarMap.setVisibility(View.VISIBLE);
            holder.textArrivalTimeMap.setText("도보 " + arrivalTime + "분");

        }

        holder.cardViewMapStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), StoreDetailActivity.class);
                intent.putExtra("store", stores.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public void updateData(ArrayList<Store> stores) {
        this.stores.clear();
        this.stores.addAll(stores);
        this.notifyDataSetChanged();
    }
}





