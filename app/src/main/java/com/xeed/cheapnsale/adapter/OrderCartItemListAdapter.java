package com.xeed.cheapnsale.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.OrderCartItemListHolder;
import com.xeed.cheapnsale.vo.CartItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderCartItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CartItem> cartItemList = new ArrayList<>();

    public OrderCartItemListAdapter(ArrayList<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public OrderCartItemListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_child_view, parent, false);

        return new OrderCartItemListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrderCartItemListHolder orderCartItemListHolder = (OrderCartItemListHolder) holder;

        final DecimalFormat formatter = new DecimalFormat("#,###,###");

        orderCartItemListHolder.orderCartItemName.setText(cartItemList.get(position).getMenuName());
        orderCartItemListHolder.orderCartItemCount.setText(""+formatter.format(cartItemList.get(position).getCount()));
        orderCartItemListHolder.orderCartItemPrice.setText(""+formatter.format(cartItemList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

}
