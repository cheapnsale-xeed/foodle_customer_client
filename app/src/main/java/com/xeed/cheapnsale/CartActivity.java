package com.xeed.cheapnsale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xeed.cheapnsale.adapter.CartListAdapter;
import com.xeed.cheapnsale.adapter.StoreListAdapter;
import com.xeed.cheapnsale.service.model.Store;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    CartListAdapter cartListAdapter;
    RecyclerView recyclerView;
    View cartFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initCartFooterLayout();

        Application app = (Application) getApplicationContext();
        cartListAdapter = new CartListAdapter(this, app.getCart().getCartItems());

        recyclerView = (RecyclerView) findViewById(R.id.cart_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(cartListAdapter);

        ImageButton backButton = (ImageButton) findViewById(R.id.main_toolbar_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    private void initCartFooterLayout() {
        cartFooter = LayoutInflater.from(this).inflate(R.layout.cart_footer, (ViewGroup) findViewById(R.id.activity_cart), false);
        ((ViewGroup) findViewById(R.id.activity_cart)).addView(cartFooter);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById(R.id.cart_layout).getLayoutParams();
        layoutParams.addRule(RelativeLayout.ABOVE, cartFooter.getId());

        updateCartFooterData();

        TextView orderButton = (TextView) cartFooter.findViewById(R.id.cart_footer_order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateCartFooterData(){

        Application app = (Application) getApplicationContext();
        final DecimalFormat formatter = new DecimalFormat("#,###,###");
        TextView orderSummaryInfoCount = (TextView) cartFooter.findViewById(R.id.cart_footer_order_info_count);
        orderSummaryInfoCount.setText("(" + app.getCart().getTotalCount() + ")");
        TextView orderSummaryInfoPrice = (TextView) cartFooter.findViewById(R.id.cart_footer_order_info_price);
        orderSummaryInfoPrice.setText(formatter.format(app.getCart().getTotalPrice()) + getResources().getString(R.string.price_type));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartListAdapter.notifyDataSetChanged();
    }
}
