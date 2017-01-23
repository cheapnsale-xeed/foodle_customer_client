package com.xeed.cheapnsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xeed.cheapnsale.adapter.CartListAdapter;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends AppCompatActivity {

    CartListAdapter cartListAdapter;

    @BindView(R.id.cart_list_recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.main_toolbar_back_button)
    public ImageButton backButton;

    @BindView(R.id.cart_footer_order_button)
    public TextView orderButton;

    @BindView(R.id.cart_footer_order_info_count)
    public TextView orderSummaryInfoCount;

    @BindView(R.id.cart_footer_order_info_price)
    public TextView orderSummaryInfoPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        Application app = (Application) getApplicationContext();
        cartListAdapter = new CartListAdapter(this, app.getCart().getCartItems());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(cartListAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateCartFooterData();
    }

    @OnClick(R.id.cart_footer_order_button)
    public void onClickFooterOrderButton(View view) {
        Intent intent = new Intent(CartActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    public void updateCartFooterData(){
        Application app = (Application) getApplicationContext();

        final DecimalFormat formatter = new DecimalFormat("#,###,###");
        orderSummaryInfoCount.setText("(" + app.getCart().getTotalCount() + ")");
        orderSummaryInfoPrice.setText(formatter.format(app.getCart().getTotalPrice()) + getResources().getString(R.string.price_type));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartListAdapter.notifyDataSetChanged();
    }
}
