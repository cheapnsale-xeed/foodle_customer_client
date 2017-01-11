package com.xeed.cheapnsale.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreListHolder> {

    private ArrayList<Store> stores;

    public StoreListAdapter(ArrayList<Store> list) {
        this.stores = list;
    }

    @Override
    public StoreListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list, parent, false);

        return new StoreListHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreListHolder holder, int position) {
        holder.nameView.setText(stores.get(position).getName());
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

    public class StoreListHolder extends RecyclerView.ViewHolder {
        public TextView nameView;

        public StoreListHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.store_name_view);
        }
    }
}





