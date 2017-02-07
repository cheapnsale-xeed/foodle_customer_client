package com.xeed.cheapnsale.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.CartActivity;
import com.xeed.cheapnsale.holder.CartListChildHolder;
import com.xeed.cheapnsale.holder.CartListHeadHolder;
import com.xeed.cheapnsale.service.model.Cart;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.util.NumbersUtil;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int CHILD = 1;

    public ArrayList<Menu> cartItems;
    private Context context;
    private Cart cart;

    public CartListAdapter(Context context) {
        Application app = ((Application) context.getApplicationContext());

        this.context = context;
        this.cart = app.getCart();
        this.cartItems = this.cart.getMenus();

        Menu addMenuLayout = new Menu();
        addMenuLayout.setMenuType(CHILD);
        cartItems.add(addMenuLayout);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, null);

        switch (viewType) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_cart, parent, false);
                return new CartListHeadHolder(view);
            case CHILD:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_add_menu_cart, parent, false);
                return new CartListChildHolder(view);
            default:
                return new CartListHeadHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        if (getItemViewType(position) == HEADER) {

            final CartListHeadHolder cartListHeadHolder = (CartListHeadHolder) viewHolder;

            cartListHeadHolder.textItemNameCart.setText(cartItems.get(position).getMenuName());
            cartListHeadHolder.textItemCountCart.setText(Integer.toString(cartItems.get(position).getMenuItemCount()));
            cartListHeadHolder.textItemPriceCart.setText(NumbersUtil.format(cartItems.get(position).getMenuPrice() * cartItems.get(position).getMenuItemCount())
                    + context.getResources().getString(R.string.price_type));
            cartListHeadHolder.picasso.load(cartItems.get(position).getMenuImg())
                    .transform(new CropCircleTransformation()).into(cartListHeadHolder.imageItemSrcCart);

            if (cart.getMenus().get(position).getMenuItemCount() > 1) {
                cartListHeadHolder.imageMinusButtonCart.setImageResource(R.drawable.ico_minus);
            }

            cartListHeadHolder.imageDeleteButtonCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart.deleteCartItem(cart.getMenus().get(position).getMenuId());
                    ((CartActivity) context).updateCartFooterData();
                    notifyDataSetChanged();
                }
            });

            cartListHeadHolder.imagePlusButtonCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart.plusCartItem(cartItems.get(position).getMenuId());
                    if (cart.getMenus().get(position).getMenuItemCount() > 1) {
                        cartListHeadHolder.imageMinusButtonCart.setImageResource(R.drawable.ico_minus);
                    }
                    ((CartActivity) context).updateCartFooterData();
                    notifyDataSetChanged();

                }
            });

            cartListHeadHolder.imageMinusButtonCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart.minusCartItem(cartItems.get(position).getMenuId());
                    if (cart.getMenus().get(position).getMenuItemCount() < 2) {
                        cartListHeadHolder.imageMinusButtonCart.setImageResource(R.drawable.ico_minus_dim);
                    }
                    ((CartActivity) context).updateCartFooterData();
                    notifyDataSetChanged();

                }
            });
        } else if (getItemViewType(position) == CHILD) {
            final CartListChildHolder cartListChildHolder = (CartListChildHolder) viewHolder;

            cartListChildHolder.textStoreNameCart.setText(cart.getStoreName());
            cartListChildHolder.linearAddMenuCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity) context).finish();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return cartItems.get(position).getMenuType();

    }
}





