package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.CartActivity;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.CartListHolder;
import com.xeed.cheapnsale.vo.Cart;
import com.xeed.cheapnsale.vo.CartItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListHolder> {

    private Context context;
    private ArrayList<CartItem> cartItems;
    private Cart cart;

    public CartListAdapter(Context context, ArrayList<CartItem> list) {
        this.context = context;
        this.cartItems = list;

        Application app = ((Application) context.getApplicationContext());
        cart = app.getCart();
    }

    @Override
    public CartListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cart_card, null);

        return new CartListHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartListHolder holder, final int position) {
        final DecimalFormat formatter = new DecimalFormat("#,###,###");

        holder.itemName.setText(cartItems.get(position).getMenuName());
        holder.itemCountText.setText(Integer.toString(cartItems.get(position).getCount()));
        holder.itemTotalPriceText.setText(formatter.format(cartItems.get(position).getPrice())
                +context.getResources().getString(R.string.price_type));
//        holder.itemImage

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.deleteCartItem(cart.getCartItems().get(position).getMenuName());
                ((CartActivity) context).updateCartFooterData();
                notifyDataSetChanged();
            }
        });

        holder.itemPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cart.plusCartItem(cartItems.get(position).getMenuName());
                ((CartActivity) context).updateCartFooterData();
                notifyDataSetChanged();

            }
        });

        holder.itemMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cart.minusCartItem(cartItems.get(position).getMenuName());
                ((CartActivity) context).updateCartFooterData();
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}





