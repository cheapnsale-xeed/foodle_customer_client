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
import com.xeed.cheapnsale.service.model.Order;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MyOrderCurrentAdapter extends RecyclerView.Adapter<MyOrderCurrentAdapter.MyOrderCurrentHolder> {

    private Context context;
    private ArrayList<Order> myOrder;

    public MyOrderCurrentAdapter(Context context, ArrayList<Order> myOrder) {
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
        Order order = myOrder.get(position);

        //TODO : 확인 필요
        holder.picasso.load(order.getMenus().get(0).getMenuImg())
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
        return myOrder.size();
    }

    public void updateData(ArrayList<Order> myOrder) {
        this.myOrder = myOrder;
        this.notifyDataSetChanged();
    }

    public class MyOrderCurrentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_item_src_menu)
        ImageView imgView;
        @BindView(R.id.menu_item_status)
        TextView itemStatus;
        @BindView(R.id.my_order_item_name)
        TextView itemName;
        @BindView(R.id.my_order_store_name)
        TextView storeName;

        @Inject
        Picasso picasso;

        public MyOrderCurrentHolder(View itemView) {
            super(itemView);
            ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);
            ButterKnife.bind(this, itemView);
        }
    }
}
