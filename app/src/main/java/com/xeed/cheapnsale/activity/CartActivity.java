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

    private CartListAdapter cartListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        Application app = (Application) getApplicationContext();
        cartListAdapter = new CartListAdapter(this, app.getCart().getMenus());

        recyclerCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerCart.setAdapter(cartListAdapter);

        updateCartFooterData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.image_back_button_cart)
    public void onClickBackButton(View view) {
        onBackPressed();
    }

    @OnClick(R.id.text_order_button_footer)
    public void onClickFooterOrderButton(View view) {
        Intent intent = new Intent(CartActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    public void updateCartFooterData(){
        Application app = (Application) getApplicationContext();

        textItemCountFooter.setText("(" + app.getCart().getTotalCount() + ")");
        textTotalPriceFooter.setText(NumbersUtil.format(app.getCart().getTotalPrice()) + getResources().getString(R.string.price_type));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartListAdapter.notifyDataSetChanged();
    }
}
