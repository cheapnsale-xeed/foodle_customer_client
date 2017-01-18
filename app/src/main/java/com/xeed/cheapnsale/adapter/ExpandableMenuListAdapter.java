package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.OrderActivity;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.StoreDetailActivity;
import com.xeed.cheapnsale.holder.ExpandableMenuChildHolder;
import com.xeed.cheapnsale.holder.ExpandableMenuListHolder;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.vo.CartItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpandableMenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int CHILD = 1;

    private ArrayList<Menu> menus;
    private Context context;
    private Toast toast;

    public ExpandableMenuListAdapter(Context context, ArrayList<Menu> menus) {
        this.context = context;
        this.menus = menus;
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

            expandableMenuListHolder.picasso.load(menus.get(position).getMenuImg()).into(expandableMenuListHolder.itemImage);
            expandableMenuListHolder.itemName.setText(menus.get(position).getMenuName());
            expandableMenuListHolder.itemPrice.setText(formatter.format(menus.get(position).getMenuPrice())
                    +context.getResources().getString(R.string.price_type));
            expandableMenuListHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (expandableMenuListHolder.itemName != null) {
                        int childPos = getChildPos();

                        if (childPos != -1) {
                            menus.remove(childPos);
                        }

                        if (position != childPos-1) {
                            if (position < childPos || childPos == -1) {
                                childPos = position + 1;
                            } else {
                                childPos = position;
                            }


                            Menu childItem = new Menu(CHILD, menus.get(position).getMenuName(),
                                    menus.get(position).getMenuPrice(), menus.get(position).getMenuImg());
                            childItem.setMenuItemCount(1);
                            childItem.setMenuItemTotalPrice(1 * childItem.getMenuPrice());
                            menus.add(childPos, childItem);
                        }

                        notifyDataSetChanged();
                    }
                }
            });
        }
        else if (getItemViewType(position) == CHILD){
            final ExpandableMenuChildHolder childHolder = (ExpandableMenuChildHolder) holder;

            childHolder.itemTotalPriceText.setText(formatter.format(menus.get(position).getMenuItemTotalPrice())
                    +context.getResources().getString(R.string.price_type));
            childHolder.itemCountText.setText(""+ menus.get(position).getMenuItemCount());
            childHolder.itemPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemCount = Integer.parseInt(childHolder.itemCountText.getText().toString());
                    childHolder.itemCountText.setText(String.valueOf(++itemCount));

                    int totalItemPrice = itemCount * menus.get(position).getMenuPrice();
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

                    int totalItemPrice = itemCount * menus.get(position).getMenuPrice();
                    childHolder.itemTotalPriceText.setText(formatter.format(totalItemPrice)
                            +context.getResources().getString(R.string.price_type));
                }
            });
            childHolder.itemAddCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 다름 유저스토리에서 활용 가능
                    Application app = ((Application) context.getApplicationContext());

                    for (int i = 0; i < Integer.parseInt(childHolder.itemCountText.getText().toString()); i++) {
                        CartItem cartItem = new CartItem();
                        cartItem.setMenuId("111");
                        cartItem.setMenuName(menus.get(getChildPos()).getMenuName());
                        cartItem.setPrice(menus.get(getChildPos()).getMenuPrice());

                        app.getCart().setStoreId("store_1");
                        app.getCart().addCartItem(cartItem);
                    }

                    initCartFooterLayout();
                    showCartCheckSec();

                    setChildLayoutChange(childHolder, app.getCart().getTotalCount());
                    menus.remove(getChildPos());

                    notifyDataSetChanged();
                }
            });

            childHolder.itemOrderNowButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Application app = ((Application) context.getApplicationContext());
                    for (int i = 0; i < Integer.parseInt(childHolder.itemCountText.getText().toString()); i++) {
                        CartItem cartItem = new CartItem();
                        cartItem.setMenuId("111");
                        cartItem.setMenuName(menus.get(getChildPos()).getMenuName());
                        cartItem.setPrice(menus.get(getChildPos()).getMenuPrice());

                        app.getCart().setStoreId("store_1");
                        app.getCart().addCartItem(cartItem);
                    }
                    Intent intent = new Intent(context, OrderActivity.class);
                    context.startActivity(intent);
                }
            });

            setChildLayoutChange(childHolder, ((Application) context.getApplicationContext()).getCart().getTotalCount());
        }
    }

    private void showCartCheckSec() {

        LinearLayout cartCheck=(LinearLayout)View.inflate(context,R.layout.cart_check,null);
        toast=new Toast(context);
        toast.setView(cartCheck);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }

    private void initCartFooterLayout() {

        View cartFooter = LayoutInflater.from(context)
                .inflate(R.layout.cart_footer, (ViewGroup) ((StoreDetailActivity) context).findViewById(R.id.store_detail_layout), false);
        ((ViewGroup) ((StoreDetailActivity) context).findViewById(R.id.store_detail_layout)).addView(cartFooter);

        Application app = ((Application) context.getApplicationContext());
        final DecimalFormat formatter = new DecimalFormat("#,###,###");


        RelativeLayout.LayoutParams coordinatorLayoutParams = (RelativeLayout.LayoutParams) (((StoreDetailActivity) context).findViewById(R.id.coordinator)).getLayoutParams();
        coordinatorLayoutParams.addRule(RelativeLayout.ABOVE, cartFooter.getId());

        TextView orderSummaryInfoCount = (TextView) cartFooter.findViewById(R.id.cart_footer_order_info_count);
        orderSummaryInfoCount.setText("(" + app.getCart().getTotalCount() + ")");
        TextView orderSummaryInfoPrice = (TextView) cartFooter.findViewById(R.id.cart_footer_order_info_price);
        orderSummaryInfoPrice.setText(formatter.format(app.getCart().getTotalPrice()) + context.getResources().getString(R.string.price_type));
        TextView orderButton = (TextView) cartFooter.findViewById(R.id.cart_footer_order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    @Override
    public int getItemViewType(int position) {
        return menus.get(position).getMenuType();
    }

    private int getChildPos(){
        for (int i = 0; i < getItemCount(); i ++) {
            if (menus.get(i).getMenuType() == CHILD) {
                return i;
            }
        }

        return -1;
    }

    private void setChildLayoutChange(ExpandableMenuChildHolder childHolder, int totalCount) {
        if (totalCount == 0) {
            childHolder.itemOrderNowButton.setVisibility(View.VISIBLE);
        } else {
            childHolder.itemOrderNowButton.setVisibility(View.GONE);
        }
    }

    public void updateData(ArrayList<Menu> menus) {
        this.menus = menus;
        notifyDataSetChanged();        
    }
}
