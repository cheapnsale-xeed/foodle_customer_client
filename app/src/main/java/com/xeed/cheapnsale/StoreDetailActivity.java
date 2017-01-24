package com.xeed.cheapnsale;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xeed.cheapnsale.adapter.StoreMenuTabPagerAdapter;
import com.xeed.cheapnsale.service.model.Store;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoreDetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private final static int COLLAPS_LIMIT = -666;

    private StoreMenuTabPagerAdapter storeMenuTabPagerAdapter;

    @BindView(R.id.store_detail_app_bar)
    public AppBarLayout appBarLayout;

    @BindView(R.id.view_pager)
    public ViewPager viewPager;

    @BindView(R.id.tabs)
    public TabLayout tabLayout;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.toolbar_back_button)
    public ImageButton backButton;

    @BindView(R.id.toolbar_order_text)
    public TextView title;

    @BindView(R.id.toolbar_call_link)
    public TextView callLink;

    @BindView(R.id.toolbar_map_link)
    public TextView mapLink;

    @BindView(R.id.store_title)
    public TextView storeTitle;

    @BindView(R.id.store_payment_type)
    public TextView storePaymentType;

    @BindView(R.id.store_main_img)
    public ImageView storeMainImage;

    @BindView(R.id.store_detail_button_call)
    public FloatingActionButton phoneButton;

    Store store;

    @Inject
    public Picasso picasso;
    public Target imageCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((Application) getApplication()).getApplicationComponent().inject(this);
        setContentView(R.layout.activity_store_detail);
        ButterKnife.bind(this);

        store = (Store) getIntent().getSerializableExtra("store");

        //actionbar에 들어갈 내용을 우리의 view인 toolbar에 적용..
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText("메뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("가게정보"));
        tabLayout.addTab(tabLayout.newTab().setText("리뷰(21)"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        storeMenuTabPagerAdapter = new StoreMenuTabPagerAdapter(store, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(storeMenuTabPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);

        //store 정보 세팅 시작
        storeTitle.setText(store.getName());
        storePaymentType.setText(store.getPaymentType());

        imageCallback = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                storeMainImage.setImageBitmap(bitmap);
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

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset > COLLAPS_LIMIT) {
                    title.setVisibility(View.GONE);
                    callLink.setVisibility(View.GONE);
                    mapLink.setVisibility(View.GONE);

                    toolbar.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    title.setVisibility(View.VISIBLE);
                    callLink.setVisibility(View.VISIBLE);
                    mapLink.setVisibility(View.VISIBLE);

                    toolbar.setBackgroundColor(Color.rgb(255, 255, 255));
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        callLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
    }

    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + store.getPhoneNumber()));
        try {
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void setTabTextStyle(int position, int typeface) {

        LinearLayout linearLayout = (LinearLayout) ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(position);
        TextView tabTextView = (TextView) linearLayout.getChildAt(1);
        tabTextView.setTypeface(tabTextView.getTypeface(), typeface);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //유저에 의해 tab button 눌린 순간 viewpager 화면 조정..
        viewPager.setCurrentItem(tab.getPosition());
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
        super.onBackPressed();
        finish();
    }
}
