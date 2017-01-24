package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.model.MyOrder;
import com.xeed.cheapnsale.service.model.Order;

import javax.inject.Inject;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MyOrderCurrentAdapter extends RecyclerView.Adapter<MyOrderCurrentAdapter.MyOrderCurrentHolder> {

    private Context context;
    private MyOrder myOrder;

    public MyOrderCurrentAdapter(Context context, MyOrder myOrder) {
        this.context = context;
        this.myOrder = myOrder;
    }

    @Override
    public MyOrderCurrentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_order_ready_list, parent, false);

        return new MyOrderCurrentAdapter.MyOrderCurrentHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderCurrentHolder holder, int position) {
        Order order = myOrder.getOrders().get(position);
        holder.picasso.load(order.getMenus().get(position).getMenuImg())
                .transform(new CropCircleTransformation())
                .into(holder.imgView);
        if (order.getStatus().equals("DONE")) {
            holder.itemStatus.setText(R.string.ready_to_pickup);
        }
        else if (order.getStatus().equals("READY")) {
            holder.itemStatus.setText(R.string.ready_to_receipt);
        }
        holder.itemName.setText(order.getMenus().get(0).getMenuName());
        holder.storeName.setText(order.getStoreName());
    }

    @Override
    public int getItemCount() {
        return myOrder.getOrders().size();
    }

    public void updateData(MyOrder myOrder) {
        this.myOrder.setOrders(myOrder.getOrders());
        this.notifyDataSetChanged();
    }

    public class MyOrderCurrentHolder extends RecyclerView.ViewHolder {

        ImageView imgView;
        TextView itemStatus;
        TextView itemName;
        TextView storeName;

        @Inject
        Picasso picasso;

        public MyOrderCurrentHolder(View itemView) {
            super(itemView);
            ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);

            imgView = (ImageView) itemView.findViewById(R.id.menu_item_image);
            itemStatus = (TextView) itemView.findViewById(R.id.menu_item_status);
            itemName = (TextView) itemView.findViewById(R.id.my_order_item_name);
            storeName = (TextView) itemView.findViewById(R.id.my_order_store_name);
        }
    }
}
