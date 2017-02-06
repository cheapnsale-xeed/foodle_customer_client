package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.CartActivity;
import com.xeed.cheapnsale.activity.OrderActivity;
import com.xeed.cheapnsale.activity.StoreDetailActivity;
import com.xeed.cheapnsale.holder.MenuListChildHolder;
import com.xeed.cheapnsale.holder.MenuListHeadHolder;
import com.xeed.cheapnsale.service.model.Cart;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.util.NumbersUtil;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int CHILD = 1;

    private ArrayList<Menu> menus;
    private Context context;
    private Toast toast;
    private View cartFooter;
    private LinearLayoutManager linearLayoutManager;
    private Store store;

    public MenuListAdapter(Context context, LinearLayoutManager linearLayoutManager, ArrayList<Menu> menus, Store store) {
        this.context = context;
        this.menus = menus;
        this.store = store;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_head, parent, false);

        switch (viewType) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_menu_head, parent, false);
                return new MenuListHeadHolder(view);
            case CHILD:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_menu_child, parent, false);
                return new MenuListChildHolder(view);
            default:
                return new MenuListHeadHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == HEADER) {
            final MenuListHeadHolder menuListHeadHolder = (MenuListHeadHolder) holder;

            menuListHeadHolder.picasso.load(menus.get(position).getMenuImg())
                    .transform(new CropCircleTransformation()).into(menuListHeadHolder.imageItemSrcMenu);
            menuListHeadHolder.textItemNameMenu.setText(menus.get(position).getMenuName());
            menuListHeadHolder.textItemPriceMenu.setText(NumbersUtil.format(menus.get(position).getMenuPrice())
                    + context.getResources().getString(R.string.price_type));
            menuListHeadHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (menuListHeadHolder.textItemNameMenu != null) {
                        int childPos = getChildPos();
                        int parentPos = position;

                        if (childPos != -1) {
                            menus.remove(childPos);
                        }

                        if (parentPos != childPos - 1) {
                            if (parentPos < childPos || childPos == -1) {
                                childPos = parentPos + 1;
                            } else {
                                childPos = parentPos;
                                --parentPos;
                            }


                            Menu childItem = new Menu(CHILD, menus.get(parentPos).getMenuId(), menus.get(parentPos).getMenuName(),
                                    menus.get(parentPos).getMenuPrice(), menus.get(parentPos).getMenuImg());
                            childItem.setMenuItemCount(1);
                            childItem.setMenuItemTotalPrice(childItem.getMenuPrice());
                            menus.add(childPos, childItem);
                        }

                        linearLayoutManager.scrollToPosition(childPos);
                        if(linearLayoutManager.findLastCompletelyVisibleItemPosition() >= childPos - 1) {
                            ((AppBarLayout) ((StoreDetailActivity) context).findViewById(R.id.app_bar_store_detail)).setExpanded(false);
                        }
                        notifyDataSetChanged();
                    }
                }
            });
        } else if (getItemViewType(position) == CHILD) {
            final MenuListChildHolder childHolder = (MenuListChildHolder) holder;

            childHolder.textTotalPriceMenu.setText(NumbersUtil.format(menus.get(position).getMenuItemTotalPrice())
                    + context.getResources().getString(R.string.price_type));
            childHolder.textItemCountMenu.setText("" + menus.get(position).getMenuItemCount());
            childHolder.imagePlusButtonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemCount = Integer.parseInt(childHolder.textItemCountMenu.getText().toString());

                    childHolder.textItemCountMenu.setText(String.valueOf(++itemCount));

                    if (itemCount > 1) {
                        childHolder.imageMinusButtonMenu.setImageResource(R.drawable.ico_minus);
                    }

                    int totalItemPrice = itemCount * menus.get(position).getMenuPrice();
                    childHolder.textTotalPriceMenu.setText(NumbersUtil.format(totalItemPrice)
                            + context.getResources().getString(R.string.price_type));
                }
            });
            childHolder.imageMinusButtonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemCount = Integer.parseInt(childHolder.textItemCountMenu.getText().toString());
                    if (itemCount < 2) return;
                    else childHolder.textItemCountMenu.setText(String.valueOf(--itemCount));

                    if (itemCount < 2)
                        childHolder.imageMinusButtonMenu.setImageResource(R.drawable.ico_minus_dim);

                    int totalItemPrice = itemCount * menus.get(position).getMenuPrice();
                    childHolder.textTotalPriceMenu.setText(NumbersUtil.format(totalItemPrice)
                            + context.getResources().getString(R.string.price_type));
                }
            });
            childHolder.buttonAddCartMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 다름 유저스토리에서 활용 가능
                    Application app = ((Application) context.getApplicationContext());

                    Menu menu = new Menu();
                    menu.setMenuId(menus.get(getChildPos()).getMenuId());
                    menu.setMenuName(menus.get(getChildPos()).getMenuName());
                    menu.setMenuPrice(menus.get(getChildPos()).getMenuPrice());
                    menu.setMenuImg(menus.get(getChildPos()).getMenuImg());
                    menu.setMenuItemCount(Integer.parseInt(childHolder.textItemCountMenu.getText().toString()));

                    app.getCart().setStoreId(store.getId());
                    app.getCart().setStoreName(store.getName());
                    app.getCart().addCartItem(menu);

                    initCartFooterLayout();
                    showCartCheckSec();

                    setChildLayoutChange(childHolder, app.getCart().getTotalCount());
                    menus.remove(getChildPos());

                    notifyDataSetChanged();
                }
            });

            childHolder.buttonOrderNowMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Application app = ((Application) context.getApplicationContext());

                    Menu menu = new Menu();
                    menu.setMenuId(menus.get(getChildPos()).getMenuId());
                    menu.setMenuName(menus.get(getChildPos()).getMenuName());
                    menu.setMenuPrice(menus.get(getChildPos()).getMenuPrice());
                    menu.setMenuImg(menus.get(getChildPos()).getMenuImg());
                    menu.setMenuItemCount(Integer.parseInt(childHolder.textItemCountMenu.getText().toString()));

                    app.getCart().setStoreId(store.getId());
                    app.getCart().setStoreName(store.getName());
                    app.getCart().addCartItem(menu);

                    menus.remove(getChildPos());

                    Intent intent = new Intent(context, OrderActivity.class);
                    context.startActivity(intent);
                }
            });

            setChildLayoutChange(childHolder, ((Application) context.getApplicationContext()).getCart().getTotalCount());
        }
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    @Override
    public int getItemViewType(int position) {
        return menus.get(position).getMenuType();
    }

    private void showCartCheckSec() {

        LinearLayout cartCheck = (LinearLayout) View.inflate(context, R.layout.cart_check, null);
        toast = new Toast(context);
        toast.setView(cartCheck);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }

    public void initCartFooterLayout() {
        if (context instanceof StoreDetailActivity) {
            Application app = (Application) context.getApplicationContext();
            Cart cart = app.getCart();

            if (cartFooter == null) {
                cartFooter = LayoutInflater.from(context)
                        .inflate(R.layout.cart_footer, (ViewGroup) ((StoreDetailActivity) context).findViewById(R.id.relative_store_detail), false);
            } else {
                ((ViewGroup) ((StoreDetailActivity) context).findViewById(R.id.relative_store_detail)).removeView(cartFooter);
            }

            if (cart.getTotalCount() == 0) return;

            ((ViewGroup) ((StoreDetailActivity) context).findViewById(R.id.relative_store_detail)).addView(cartFooter);

            RelativeLayout.LayoutParams coordinatorLayoutParams = (RelativeLayout.LayoutParams) (((StoreDetailActivity) context).findViewById(R.id.coordinator_store_detail)).getLayoutParams();
            coordinatorLayoutParams.addRule(RelativeLayout.ABOVE, cartFooter.getId());

            TextView orderSummaryInfoCount = (TextView) cartFooter.findViewById(R.id.text_item_count_footer);
            orderSummaryInfoCount.setText("(" + app.getCart().getTotalCount() + ")");
            TextView orderSummaryInfoPrice = (TextView) cartFooter.findViewById(R.id.text_total_price_footer);
            orderSummaryInfoPrice.setText(NumbersUtil.format(app.getCart().getTotalPrice()) + context.getResources().getString(R.string.price_type));

            TextView orderButton = (TextView) cartFooter.findViewById(R.id.text_order_button_footer);
            orderButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderActivity.class);
                    context.startActivity(intent);
                }
            });

            View callCartButton = cartFooter.findViewById(R.id.linear_cart_button_footer);
            callCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CartActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    private int getChildPos() {
        for (int i = 0; i < getItemCount(); i++) {
            if (menus.get(i).getMenuType() == CHILD) {
                return i;
            }
        }

        return -1;
    }

    private void setChildLayoutChange(MenuListChildHolder childHolder, int totalCount) {
        if (totalCount == 0) {
            childHolder.buttonOrderNowMenu.setVisibility(View.VISIBLE);
        } else {
            childHolder.buttonOrderNowMenu.setVisibility(View.GONE);
        }
    }

    public void updateData(ArrayList<Menu> menus) {
        this.menus = menus;
        notifyDataSetChanged();
    }
}
