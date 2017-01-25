package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.StoreDetailActivity;
import com.xeed.cheapnsale.holder.StoreListHolder;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListHolder> {

    private Context context;
    private ArrayList<Store> stores;

    public StoreListAdapter(Context context, ArrayList<Store> list) {
        this.stores = list;
        this.context = context;
    }

    @Override
    public StoreListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list, parent, false);

        return new StoreListHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreListHolder holder, int position) {
        holder.picasso.load(stores.get(position).getMainImageUrl()).into(holder.imageSrcStore);
        holder.textNameStore.setText(stores.get(position).getName());
        holder.textPaymentTypeStore.setText(stores.get(position).getPaymentType());
        holder.textPrepTimeStore.setText(stores.get(position).getAvgPrepTime()+context.getResources().getString(R.string.minute));

        final int storePosition = position;
        final StoreListHolder storeListHolder = holder;
        storeListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(storeListHolder.itemView.getContext(), StoreDetailActivity.class);
                intent.putExtra("store", stores.get(storePosition));
                storeListHolder.itemView.getContext().startActivity(intent);
            }
        });

        int distance = stores.get(position).getDistanceToStore();
        int arrivalTime = distance / 60;

        if (distance > 1000) {
            holder.textDistanceStore.setText(((double)stores.get(position).getDistanceToStore()/1000) + "km");
        } else {
            holder.textDistanceStore.setText(stores.get(position).getDistanceToStore() + "m");
        }
        if (distance > 1500) {
            holder.textArrivalTimeStore.setVisibility(View.GONE);
            holder.viewVerticalBarStore.setVisibility(View.GONE);
        } else {
            holder.textArrivalTimeStore.setVisibility(View.VISIBLE);
            holder.viewVerticalBarStore.setVisibility(View.VISIBLE);
            holder.textArrivalTimeStore.setText(arrivalTime + "분");

        }
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public void updateData(ArrayList<Store> stores) {
        this.stores.clear();
        this.stores = stores;
        this.notifyDataSetChanged();
    }
}





