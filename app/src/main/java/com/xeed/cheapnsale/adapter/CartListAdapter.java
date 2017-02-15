package com.xeed.cheapnsale.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.CartActivity;
import com.xeed.cheapnsale.activity.StoreDetailActivity;
import com.xeed.cheapnsale.holder.CartListChildHolder;
import com.xeed.cheapnsale.holder.CartListHeadHolder;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Cart;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.util.NumbersUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject
    public CheapnsaleService cheapnsaleService;

    private static final int HEADER = 0;
    private static final int CHILD = 1;
    private static final String isReorder = "IS_REORDER";

    private boolean isReorderFlag = false;

    public ArrayList<Menu> cartItems;
    private Context context;
    private Cart cart;
    private Store store;
    Application app;

    public CartListAdapter(Context context) {
        app = ((Application) context.getApplicationContext());

        app.getApplicationComponent().inject(this);
        this.context = context;
        this.cart = app.getCart();
        this.cartItems = this.cart.getMenus();

        if(((Activity) context).getIntent() != null && ((Activity) context).getIntent().getExtras() != null) {
            isReorderFlag = ((Activity) context).getIntent().getExtras().getBoolean(isReorder);
        }

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
                    if (isReorderFlag) {

                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... params) {
                                String storeId = app.getCart().getStoreId();
                                store = cheapnsaleService.getStore(storeId);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                controlLastItem(true);

                                Intent backActionIntent = new Intent(context, StoreDetailActivity.class);
                                backActionIntent.putExtra("store", store);
                                context.startActivity(backActionIntent);
                            }
                        }.execute();
                    } else {
                        ((Activity) context).finish();
                    }
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

    // CartActivity 의 마지막 Item은 해당 매장에서의 Item 을 추가하는 링크임
    // (알아요 안다고요. 코드 지저분 해요)
    // 이에 따라 CartActivity에 진입할 때 마지막 Item을 추가하고, Activity를 떠나는 시점에 Item을 삭제 해야 함
    // 진입하는 시점: 주문하기 버튼 또는 재주문 버튼을 통해 진입
    //    -> Resume 시점에 마지막 아이템의 상태를 확인하여 추가여부 결정
    // 떠나는 시점: 뒤로가기 또는 매장명 선택 시
    //    -> controlLastItem(true)를 호출하여 처리할 것
    public void controlLastItem(boolean isLeaveActivity) {

        if (cartItems.size() > 0) {
            if(cartItems.get(cartItems.size()-1).getMenuType() != CHILD) {
                Menu addMenuLayout = new Menu();
                addMenuLayout.setMenuType(CHILD);
                cartItems.add(addMenuLayout);
            } else if (isLeaveActivity) {
                cartItems.remove(cartItems.size() - 1);
            }
        }

    }

    public void setReorderFlag(boolean reorderFlag) {
        isReorderFlag = reorderFlag;
    }

}