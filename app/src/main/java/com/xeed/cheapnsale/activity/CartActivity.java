package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.CartListAdapter;
import com.xeed.cheapnsale.util.NumbersUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.recycler_cart)
    public RecyclerView recyclerCart;

    @BindView(R.id.image_back_button_cart)
    public ImageView imageBackButtonCart;

    @BindView(R.id.text_order_button_footer)
    public TextView textOrderButtonFooter;

    @BindView(R.id.text_item_count_footer)
    public TextView textItemCountFooter;

    @BindView(R.id.text_total_price_footer)
    public TextView textTotalPriceFooter;

    @BindView(R.id.text_title_cart)
    public TextView textTitleCart;

    private CartListAdapter cartListAdapter;
    private Application app;

    private static final String isReorder = "IS_REORDER";

    private boolean isReorderFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (Application) getApplicationContext();

        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);
        cartListAdapter = new CartListAdapter(this);

        recyclerCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerCart.setAdapter(cartListAdapter);

        if(getIntent() != null && getIntent().getExtras() != null) {
            isReorderFlag = getIntent().getExtras().getBoolean(isReorder);
        }

        if(isReorderFlag) {
            textTitleCart.setText(R.string.word_reorder);
        }

        updateCartFooterData();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        cartListAdapter.controlLastItem(false);

        cartListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 카트에서 메뉴 추가 레이아웃 위치를 액티비티 소멸시 제거.
        cartListAdapter.controlLastItem(true);

    }

    @OnClick(R.id.image_back_button_cart)
    public void onClickBackButton(View view) {
        onBackPressed();
    }

    @OnClick(R.id.text_order_button_footer)
    public void onClickFooterOrderButton(View view) {
        // 카트에서 메뉴 추가 레이아웃 위치를 액티비티 소멸시 제거.
        cartListAdapter.controlLastItem(true);

        Intent intent = new Intent(CartActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    public void updateCartFooterData(){

        textItemCountFooter.setText("(" + app.getCart().getTotalCount() + ")");
        textTotalPriceFooter.setText(NumbersUtil.format(app.getCart().getTotalPrice()) + getResources().getString(R.string.price_type));
    }

    public boolean getIsReorderFlag() {
        return isReorderFlag;
    }

    public void setReorderFlag(boolean reorderFlag) {
        isReorderFlag = reorderFlag;
    }

}
