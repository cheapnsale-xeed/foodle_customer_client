package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.MainTabPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pager_main)
    public ViewPager pagerMain;

    @BindView(R.id.tab_main)
    public TabLayout tabMain;

    @BindView(R.id.image_map_button_map)
    public ImageView imageMapButtonMap;

    private MainTabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tabMain.addTab(tabMain.newTab().setText(R.string.show_all));
        tabMain.addTab(tabMain.newTab().setText(R.string.my_order));
        tabMain.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new MainTabPagerAdapter
                (getSupportFragmentManager(), tabMain.getTabCount());
        pagerMain.setAdapter(adapter);
        pagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));
        tabMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick(R.id.image_map_button_map)
    public void onClickMapLinkButton(View view) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
