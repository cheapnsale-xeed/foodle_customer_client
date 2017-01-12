package com.xeed.cheapnsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.xeed.cheapnsale.adapter.MainTabPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private MainTabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.show_all));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.my_order));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new MainTabPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //todo : 임시 생성. 탭 붙으면 삭제
    @OnClick(R.id.mainToolbarSearchButton)
    public void menuTempClicked() {
        Intent intent = new Intent(MainActivity.this, ListSelectedTempActivity.class);
        startActivity(intent);
    }
}
