package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.MainTabPagerAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Order;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Inject
    public CheapnsaleService cheapnsaleService;

    @BindView(R.id.pager_main)
    public ViewPager pagerMain;

    @BindView(R.id.tab_main)
    public TabLayout tabMain;

    @BindView(R.id.image_map_button_map)
    public ImageView imageMapButtonMap;

    ArrayList<Order> myOrder = new ArrayList<>();

    private MainTabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getApplication()).getApplicationComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tabMain.addTab(tabMain.newTab().setText(R.string.show_all));
        tabMain.addTab(tabMain.newTab().setText(getResources().getString(R.string.my_order_default)));
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

        if(getIntent().getExtras() != null && getIntent().getExtras().get("isPayment") != null){
            boolean isPayment = (boolean) getIntent().getExtras().get("isPayment");
            if(isPayment){
                pagerMain.setCurrentItem(1);
            }
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                myOrder = cheapnsaleService.getMyCurrentOrder(((Application) getApplication()).getUserEmail());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                tabMain.getTabAt(1).setText(String.format(getResources().getString(R.string.my_order), myOrder.size()));
            }
        }.execute();
    }

    @OnClick(R.id.image_map_button_map)
    public void onClickMapLinkButton(View view) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
