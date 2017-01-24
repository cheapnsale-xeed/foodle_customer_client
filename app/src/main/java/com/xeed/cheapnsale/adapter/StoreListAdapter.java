package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.StoreDetailActivity;
import com.xeed.cheapnsale.holder.StoreListHolder;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListHolder> {

    private Context context;
    public ArrayList<Store> stores;

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
    public void onBindViewHolder(StoreListHolder holder, final int position) {
        holder.nameView.setText(stores.get(position).getName());
        holder.picasso.load(stores.get(position).getMainImageUrl()).into(holder.mainImgView);
        holder.paymentTextView.setText(stores.get(position).getPaymentType());
        holder.avgPrepTimeTextView.setText(stores.get(position).getAvgPrepTime()+context.getResources().getString(R.string.minute));

        final StoreListHolder storeListHolder = holder;
        storeListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(storeListHolder.itemView.getContext(), StoreDetailActivity.class);
                intent.putExtra("store", stores.get(position));
                storeListHolder.itemView.getContext().startActivity(intent);
            }
        });

        int distance = stores.get(position).getDistanceToStore();
        int arrivalTime = distance / 60;

        if (distance > 1000) {
            holder.textDistanceToStoreInStore.setText(((double)stores.get(position).getDistanceToStore()/1000) + "km");
        } else {
            holder.textDistanceToStoreInStore.setText(stores.get(position).getDistanceToStore() + "m");
        }
        if (distance > 1500) {
            holder.textArrivalTimeInStore.setVisibility(View.GONE);
            holder.viewDivideBarInStore.setVisibility(View.GONE);
        } else {
            holder.textArrivalTimeInStore.setVisibility(View.VISIBLE);
            holder.viewDivideBarInStore.setVisibility(View.VISIBLE);
            holder.textArrivalTimeInStore.setText(arrivalTime + "분");

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





