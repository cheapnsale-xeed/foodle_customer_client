package com.xeed.cheapnsale.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.OrderCartItemListHolder;
import com.xeed.cheapnsale.util.NumbersUtil;
import com.xeed.cheapnsale.service.model.Menu;

import java.util.ArrayList;

public class OrderCartItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Menu> cartItemList = new ArrayList<>();

    public OrderCartItemListAdapter(ArrayList<Menu> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public OrderCartItemListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_child, parent, false);

        return new OrderCartItemListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrderCartItemListHolder orderCartItemListHolder = (OrderCartItemListHolder) holder;

        orderCartItemListHolder.textItemNameOrder.setText(cartItemList.get(position).getMenuName());
        orderCartItemListHolder.textItemCountOrder.setText(""+ NumbersUtil.format(cartItemList.get(position).getMenuItemCount()));
        orderCartItemListHolder.textItemPriceOrder.setText(""+ NumbersUtil.format(cartItemList.get(position).getMenuPrice() * cartItemList.get(position).getMenuItemCount()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

}
