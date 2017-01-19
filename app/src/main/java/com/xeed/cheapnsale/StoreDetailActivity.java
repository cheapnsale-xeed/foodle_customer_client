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
import android.util.Log;
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


public class StoreDetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private final static int COLLAPS_LIMIT = -666;

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private StoreMenuTabPagerAdapter storeMenuTabPagerAdapter;

    private AppBarLayout appBarLayout;
    private ImageButton backButton;

    private TextView title;
    private TextView callLink;
    private TextView mapLink;
    private TextView storeTitle;
    private TextView storePaymentType;
    private ImageView storeMainImage;
    private FloatingActionButton phoneButton;
    private TextView phoneLinkText;

    Store store;

    @Inject
    public Picasso picasso;
    public Target imageCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((Application) getApplication()).getApplicationComponent().inject(this);

        setContentView(R.layout.activity_store_detail);

        store = (Store) getIntent().getSerializableExtra("store");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //actionbar에 들어갈 내용을 우리의 view인 toolbar에 적용..
        setSupportActionBar(toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.store_detail_app_bar);
        backButton = (ImageButton) findViewById(R.id.toolbar_back_button);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        title = (TextView) findViewById(R.id.toolbar_order_text);
        callLink = (TextView) findViewById(R.id.toolbar_call_link);
        mapLink = (TextView) findViewById(R.id.toolbar_map_link);
        storeTitle = (TextView) findViewById(R.id.store_title);
        storePaymentType = (TextView) findViewById(R.id.store_payment_type);
        storeMainImage = (ImageView) findViewById(R.id.store_main_img);
        phoneButton = (FloatingActionButton) findViewById(R.id.store_detail_button_call);
        phoneLinkText = (TextView) findViewById(R.id.toolbar_call_link);

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

        phoneLinkText.setOnClickListener(new View.OnClickListener() {
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
