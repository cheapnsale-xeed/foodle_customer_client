package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreListHolder> {

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
        holder.nameView.setText(stores.get(position).getName());
        Picasso.with(context).load(stores.get(position).getMainImg()).into(holder.mainImgView);
        holder.paymentTextView.setText(stores.get(position).getPaymentType());
        holder.avgPrepTimeTextView.setText(stores.get(position).getAvgPrepTime()+context.getResources().getString(R.string.minute));
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
        public ImageView mainImgView;
        public TextView paymentTextView;
        public TextView avgPrepTimeTextView;

        public StoreListHolder(View itemView) {
            super(itemView);

            nameView = (TextView) itemView.findViewById(R.id.store_name_view);
            mainImgView = (ImageView) itemView.findViewById(R.id.store_image_view);
            paymentTextView = (TextView) itemView.findViewById(R.id.payment_type_view);
            avgPrepTimeTextView = (TextView) itemView.findViewById(R.id.avg_prep_time_view);
        }
    }
}





