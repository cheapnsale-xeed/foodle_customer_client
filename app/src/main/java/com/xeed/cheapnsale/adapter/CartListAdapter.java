package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.activity.CartActivity;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.CartListHolder;
import com.xeed.cheapnsale.util.cheapnsaleUtils;
import com.xeed.cheapnsale.vo.Cart;
import com.xeed.cheapnsale.vo.CartItem;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, null);

        return new CartListHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartListHolder holder, final int position) {

        holder.textItemNameCart.setText(cartItems.get(position).getMenuName());
        holder.textItemCountCart.setText(Integer.toString(cartItems.get(position).getCount()));
        holder.textItemPriceCart.setText(cheapnsaleUtils.format(cartItems.get(position).getPrice() * cartItems.get(position).getCount())
                +context.getResources().getString(R.string.price_type));
        holder.picasso.load(cartItems.get(position).getMenuImage())
                .transform(new CropCircleTransformation()).into(holder.imageItemSrcCart);

        if (cart.getCartItems().get(position).getCount() > 1) {
            holder.imageMinusButtonCart.setImageResource(R.drawable.ico_minus);
        }

        holder.imageDeleteButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.deleteCartItem(cart.getCartItems().get(position).getMenuId());
                ((CartActivity) context).updateCartFooterData();
                notifyDataSetChanged();
            }
        });

        holder.imagePlusButtonCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cart.plusCartItem(cartItems.get(position).getMenuId());
                if (cart.getCartItems().get(position).getCount() > 1) {
                    holder.imageMinusButtonCart.setImageResource(R.drawable.ico_minus);
                }
                ((CartActivity) context).updateCartFooterData();
                notifyDataSetChanged();

            }
        });

        holder.imageMinusButtonCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cart.minusCartItem(cartItems.get(position).getMenuId());
                if (cart.getCartItems().get(position).getCount() < 2) {
                    holder.imageMinusButtonCart.setImageResource(R.drawable.ico_minus_dim);
                }
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





