package com.xeed.cheapnsale;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xeed.cheapnsale.adapter.StoreMenuTabPagerAdapter;

public class StoreDetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private StoreMenuTabPagerAdapter storeMenuTabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //actionbar에 들어갈 내용을 우리의 view인 toolbar에 적용..
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("메뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("가게정보"));
        tabLayout.addTab(tabLayout.newTab().setText("리뷰(21)"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        storeMenuTabPagerAdapter = new StoreMenuTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(storeMenuTabPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);

        setTabTextStyle(0, Typeface.BOLD);
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

}
