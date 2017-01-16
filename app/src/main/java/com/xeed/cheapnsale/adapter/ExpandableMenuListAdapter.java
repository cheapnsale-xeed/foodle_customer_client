package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.ExpandableMenuChildHolder;
import com.xeed.cheapnsale.holder.ExpandableMenuListHolder;
import com.xeed.cheapnsale.vo.CartItem;
import com.xeed.cheapnsale.vo.MenuItems;

import java.text.DecimalFormat;
import java.util.List;

public class ExpandableMenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int CHILD = 1;

    private List<MenuItems> menuItemList;
    private Context context;

    public ExpandableMenuListAdapter(Context context, List<MenuItems> menuItemList) {
        this.context = context;
        this.menuItemList = menuItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_menu_list_child, parent, false);

        switch (viewType) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_item_menu_list_child, parent, false);
                return new ExpandableMenuListHolder(view);
            case CHILD:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_selected_menu_item_child, parent, false);
                return new ExpandableMenuChildHolder(view);
            default:
                return new ExpandableMenuListHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final DecimalFormat formatter = new DecimalFormat("#,###,###");

        if (getItemViewType(position) == HEADER){
            final ExpandableMenuListHolder expandableMenuListHolder = (ExpandableMenuListHolder) holder;

            expandableMenuListHolder.itemName.setText(menuItemList.get(position).getItemName());
            expandableMenuListHolder.itemPrice.setText(formatter.format(menuItemList.get(position).getItemPrice())
                    +context.getResources().getString(R.string.price_type));
            expandableMenuListHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (expandableMenuListHolder.itemName != null) {
                        int childPos = getChildPos();

                        if (childPos != -1) {
                            menuItemList.remove(childPos);
                        }

                        if (position != childPos-1) {
                            if (position < childPos || childPos == -1) {
                                childPos = position + 1;
                            } else {
                                childPos = position;
                            }

                            MenuItems newItem = new MenuItems(CHILD, menuItemList.get(position).getItemName(),
                                    menuItemList.get(position).getItemPrice(), menuItemList.get(position).getImageSrc());
                            newItem.setItemCount(1);
                            newItem.setItemTotalPrice(1 * newItem.getItemPrice());
                            menuItemList.add(childPos, newItem);
                        }

                        notifyDataSetChanged();
                    }
                }
            });
        }
        else if (getItemViewType(position) == CHILD){
            final ExpandableMenuChildHolder childHolder = (ExpandableMenuChildHolder) holder;

            childHolder.itemTotalPriceText.setText(formatter.format(menuItemList.get(position).getItemTotalPrice())
                    +context.getResources().getString(R.string.price_type));
            childHolder.itemCountText.setText(""+menuItemList.get(position).getItemCount());
            childHolder.itemPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemCount = Integer.parseInt(childHolder.itemCountText.getText().toString());
                    childHolder.itemCountText.setText(String.valueOf(++itemCount));

                    int totalItemPrice = itemCount * menuItemList.get(position).getItemPrice();
                    childHolder.itemTotalPriceText.setText(formatter.format(totalItemPrice)
                            +context.getResources().getString(R.string.price_type));
                }
            });
            childHolder.itemMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemCount = Integer.parseInt(childHolder.itemCountText.getText().toString());
                    if (itemCount < 2) return;
                    else childHolder.itemCountText.setText(String.valueOf(--itemCount));

                    int totalItemPrice = itemCount * menuItemList.get(position).getItemPrice();
                    childHolder.itemTotalPriceText.setText(formatter.format(totalItemPrice)
                            +context.getResources().getString(R.string.price_type));
                }
            });
            childHolder.itemAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Application app = ((Application) childHolder.storeDetailActivity.getApplicationContext());
                    if (app.getCart().getTotalCount() == 0) {
                        CartItem cartItem = new CartItem();
                        cartItem.setMenuId("111");
                        cartItem.setMenuName("만두");
                        cartItem.setPrice(5000);

                        app.getCart().setStoreId("store_1");
                        app.getCart().addCartItem(cartItem);

                        initCartFooterLayout(childHolder);
                    }

                    showCartCheckSec(childHolder);
                }
            });
        }
    }

    private void showCartCheckSec(final ExpandableMenuChildHolder childHolder) {
        final View cartCheck = LayoutInflater.from(childHolder.storeDetailActivity)
                .inflate(R.layout.cart_check, childHolder.storeDetailLayout, false);
        childHolder.storeDetailLayout.addView(cartCheck);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                childHolder.storeDetailLayout.removeView(cartCheck);
            }
        }, 1000);
    }

    private void initCartFooterLayout(ExpandableMenuChildHolder childHolder) {

        View cartFooter = LayoutInflater.from(childHolder.storeDetailActivity)
                .inflate(R.layout.cart_footer, childHolder.storeDetailLayout, false);
        childHolder.storeDetailLayout.addView(cartFooter);

        RelativeLayout.LayoutParams coordinatorLayoutParams = (RelativeLayout.LayoutParams) childHolder.storeCoordinatorLayout.getLayoutParams();
        coordinatorLayoutParams.addRule(RelativeLayout.ABOVE, cartFooter.getId());
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return menuItemList.get(position).getType();
    }

    public int getChildPos(){
        for (int i = 0; i < getItemCount(); i ++) {
            if (menuItemList.get(i).getType() == CHILD) {
                return i;
            }
        }

        return -1;
    }
}
