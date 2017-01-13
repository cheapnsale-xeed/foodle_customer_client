package com.xeed.cheapnsale;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xeed.cheapnsale.adapter.StoreMenuTabPagerAdapter;


public class StoreDetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private final static int COLLAPS_LIMIT = -700;

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private StoreMenuTabPagerAdapter storeMenuTabPagerAdapter;

    private AppBarLayout appBarLayout;
    private ImageButton backButton;

    private TextView title;
    private TextView callLink;
    private TextView mapLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

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

        tabLayout.addTab(tabLayout.newTab().setText("메뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("가게정보"));
        tabLayout.addTab(tabLayout.newTab().setText("리뷰(21)"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        storeMenuTabPagerAdapter = new StoreMenuTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(storeMenuTabPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);

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

                    toolbar.setBackgroundColor(Color.rgb(255,255,255));
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
