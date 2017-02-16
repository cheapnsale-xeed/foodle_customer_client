package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.StoreMenuTabPagerAdapter;
import com.xeed.cheapnsale.service.model.Cart;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.util.CommonUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StoreDetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private final static int COLLAPS_LIMIT = -666;

    @Inject
    public Picasso picasso;

    @BindView(R.id.app_bar_store_detail)
    public AppBarLayout appBarStoreDetail;

    @BindView(R.id.pager_order_detail)
    public ViewPager pagerOrderDetail;

    @BindView(R.id.tab_store_detail)
    public TabLayout tabStoreDetail;

    @BindView(R.id.toolbar_store_detail)
    public Toolbar toolbarStoreDetail;

    @BindView(R.id.image_back_button_store_detail)
    public ImageView imageBackButtonStoreDetail;

    @BindView(R.id.text_title_order_detail)
    public TextView textTitleOrderDetail;

    @BindView(R.id.text_call_button_store_detail)
    public TextView textCallButtonStoreDetail;

    @BindView(R.id.text_map_button_store_detail)
    public TextView textMapButtonStoreDetail;

    @BindView(R.id.text_name_store_detail)
    public TextView textNameStoreDetail;

    @BindView(R.id.text_payment_type_store_detail)
    public TextView textPaymentTypeStoreDetail;

    @BindView(R.id.image_top_src_store_detail)
    public ImageView imageTopSrcStoreDetail;

    @BindView(R.id.floating_call_button_store_detail)
    public FloatingActionButton floatingCallButtonStoreDetail;

    @BindView(R.id.text_arrival_time_store_detail)
    public TextView textArrivalTimeStoreDetail;

    @BindView(R.id.view_vertical_bar_store_detail)
    public View viewVerticalBarStoreDetail;

    @BindView(R.id.text_distance_store_detail)
    public TextView textDistanceStoreDetail;

    Store store;
    public Target imageCallback;

    private StoreMenuTabPagerAdapter storeMenuTabPagerAdapter;
    private MaterialDialog orderCancelConfirmlDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((Application) getApplication()).getApplicationComponent().inject(this);
        setContentView(R.layout.activity_store_detail);
        ButterKnife.bind(this);

        store = (Store) getIntent().getSerializableExtra("store");

        //actionbar에 들어갈 내용을 우리의 view인 toolbar에 적용..
        setSupportActionBar(toolbarStoreDetail);

        tabStoreDetail.addTab(tabStoreDetail.newTab().setText("메뉴"));
        tabStoreDetail.addTab(tabStoreDetail.newTab().setText("가게정보"));
        tabStoreDetail.addTab(tabStoreDetail.newTab().setText("리뷰(21)"));
        tabStoreDetail.setTabGravity(TabLayout.GRAVITY_FILL);

        int arrivalTime = store.getDistanceToStore() / 60;

        if (store.getDistanceToStore() > 1000) {
            double distance_km = (double)((int)((store.getDistanceToStore() + 50) / 100))/10;
            textDistanceStoreDetail.setText(distance_km + "km");
        } else {
            textDistanceStoreDetail.setText(store.getDistanceToStore() + "m");
        }

        if (store.getDistanceToStore() > 1500) {
            textArrivalTimeStoreDetail.setVisibility(View.GONE);
            viewVerticalBarStoreDetail.setVisibility(View.GONE);
        } else {
            textArrivalTimeStoreDetail.setVisibility(View.VISIBLE);
            viewVerticalBarStoreDetail.setVisibility(View.VISIBLE);
            textArrivalTimeStoreDetail.setText(arrivalTime + "분");
        }

        storeMenuTabPagerAdapter = new StoreMenuTabPagerAdapter(store, getSupportFragmentManager(), tabStoreDetail.getTabCount());
        pagerOrderDetail.setAdapter(storeMenuTabPagerAdapter);
        pagerOrderDetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabStoreDetail));
        tabStoreDetail.addOnTabSelectedListener(this);

        //store 정보 세팅 시작
        textNameStoreDetail.setText(store.getName());
        textTitleOrderDetail.setText(store.getName());
        textPaymentTypeStoreDetail.setText(store.getPaymentType());

        imageCallback = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageTopSrcStoreDetail.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        picasso.load(store.getMainImageUrl()).into(imageCallback);

        setTabTextStyle(0, Typeface.BOLD);

        appBarStoreDetail.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset > COLLAPS_LIMIT) {
                    textTitleOrderDetail.setVisibility(View.GONE);
                    textCallButtonStoreDetail.setVisibility(View.GONE);
                    textMapButtonStoreDetail.setVisibility(View.GONE);

                    toolbarStoreDetail.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    textTitleOrderDetail.setVisibility(View.VISIBLE);
                    textCallButtonStoreDetail.setVisibility(View.VISIBLE);
                    textMapButtonStoreDetail.setVisibility(View.VISIBLE);

                    toolbarStoreDetail.setBackgroundColor(Color.rgb(255, 255, 255));
                }
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //유저에 의해 tab button 눌린 순간 viewpager 화면 조정..
        pagerOrderDetail.setCurrentItem(tab.getPosition());
        setTabTextStyle(tab.getPosition(), Typeface.BOLD);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabTextStyle(tab.getPosition(), Typeface.NORMAL);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {

        Cart cart = ((Application) getApplication()).getCart();

        if(cart.getTotalCount() > 0) {

            orderCancelConfirmlDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_store_detail_back_pressed, false).build();
            orderCancelConfirmlDialog.show();

            TextView noButton = (TextView) orderCancelConfirmlDialog.getView().findViewById(R.id.button_cancel_confirm_dialog_no);
            TextView yesButton = (TextView) orderCancelConfirmlDialog.getView().findViewById(R.id.button_cancel_confirm_dialog_yes);

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderCancelConfirmlDialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderCancelConfirmlDialog.dismiss();
                    finish();
                }
            });

        } else {
            finish();
        }
    }

    @OnClick(R.id.image_back_button_store_detail)
    public void onClickBackButton(View view) {
        onBackPressed();
    }

    @OnClick(R.id.floating_call_button_store_detail)
    public void onClickFloatingCallButton(View view) {
        CommonUtil.makePhoneCall(StoreDetailActivity.this, store.getPhoneNumber());
    }

    @OnClick(R.id.text_call_button_store_detail)
    public void onClickToolbarTextCallButton(View view) {
        CommonUtil.makePhoneCall(StoreDetailActivity.this, store.getPhoneNumber());
    }

    @OnClick(R.id.text_map_button_store_detail)
    public void onClickStoreDetailMapButton(View view) {
        Intent nextIntent = new Intent(StoreDetailActivity.this, StoreDetailMapActivity.class);
        nextIntent.putExtra("store", store);
        startActivity(nextIntent);
    }

    private void setTabTextStyle(int position, int typeface) {

        LinearLayout linearLayout = (LinearLayout) ((ViewGroup) tabStoreDetail.getChildAt(0)).getChildAt(position);
        TextView tabTextView = (TextView) linearLayout.getChildAt(1);
        tabTextView.setTypeface(tabTextView.getTypeface(), typeface);

    }
}
