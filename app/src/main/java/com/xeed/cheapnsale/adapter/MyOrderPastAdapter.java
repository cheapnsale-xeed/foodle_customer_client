package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.CartActivity;
import com.xeed.cheapnsale.service.model.Order;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderPastAdapter extends RecyclerView.Adapter<MyOrderPastAdapter.MyOrderPastHolder> {

    private Context context;
    private ArrayList<Order> myOrder;

    public MyOrderPastAdapter(Context context, ArrayList<Order> myOrder) {
        this.context = context;
        this.myOrder = myOrder;
    }

    @Override
    public MyOrderPastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finish_my_order, parent, false);

        return new MyOrderPastAdapter.MyOrderPastHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyOrderPastHolder holder, final int position) {
        final Order order = myOrder.get(position);
        String menuContent;

        holder.picasso.load(order.getMenus().get(0).getMenuImg()).into(holder.imageItemSrcMyOrder);

        if (order.getMenus().size() > 1) {
            menuContent = order.getMenus().get(0).getMenuName() + " 외 " + String.valueOf(order.getMenus().size() - 1) + "개";
        } else {
            menuContent = order.getMenus().get(0).getMenuName();
        }
        holder.textItemNameMyOrder.setText(menuContent);
        holder.textStoreNameMyOrder.setText(order.getStoreName());
        holder.textPickupTimeMyOrder.setText(order.getPickupTime().substring(0,10));
        holder.buttonReorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Application app = ((Application) context.getApplicationContext());
                Order order = myOrder.get(position);

                app.getCart().setStoreId(order.getStoreId());
                app.getCart().setStoreName(order.getStoreName());

                for ( int i = 0 ; i < order.getMenus().size() ; i++ ) {
                    app.getCart().addCartItem(order.getMenus().get(i));
                }

                Intent reorderIntent = new Intent(context, CartActivity.class);
                context.startActivity(reorderIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }

    public void updateData(ArrayList<Order> myOrder) {
        this.myOrder = myOrder;
        this.notifyDataSetChanged();
    }

    public class MyOrderPastHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_item_src_my_order)
        public ImageView imageItemSrcMyOrder;
        @BindView(R.id.text_item_name_my_order)
        public TextView textItemNameMyOrder;
        @BindView(R.id.text_store_name_my_order)
        public TextView textStoreNameMyOrder;
        @BindView(R.id.text_pickup_time_my_order)
        public TextView textPickupTimeMyOrder;
        @BindView(R.id.button_reorder)
        public Button buttonReorder;

        @Inject
        Picasso picasso;

        public MyOrderPastHolder(View itemView) {
            super(itemView);
            ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);
            ButterKnife.bind(this, itemView);
        }
    }
}
