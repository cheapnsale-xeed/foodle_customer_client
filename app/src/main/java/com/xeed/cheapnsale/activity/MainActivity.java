package com.xeed.cheapnsale.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.MainTabPagerAdapter;
import com.xeed.cheapnsale.backgroundservice.PushFirebaseInstanceIdService;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Order;
import com.xeed.cheapnsale.user.AWSMobileClient;
import com.xeed.cheapnsale.user.IdentityManager;
import com.xeed.cheapnsale.util.DateUtil;

import org.joda.time.DateTimeUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Inject
    public CheapnsaleService cheapnsaleService;

    @Inject
    public AWSMobileClient awsMobileClient;

    @BindView(R.id.pager_main)
    public ViewPager pagerMain;

    @BindView(R.id.tab_main)
    public TabLayout tabMain;

    @BindView(R.id.image_map_button_map)
    public ImageView imageMapButtonMap;

    @BindView(R.id.image_search_button_main)
    public ImageView imageSearchButtonMain;

    ArrayList<Order> myOrder = new ArrayList<>();

    private MainTabPagerAdapter adapter;
    private IdentityManager identityManager;


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
        tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        if (getIntent().getExtras() != null && getIntent().getExtras().get("isPayment") != null) {
            boolean isPayment = (boolean) getIntent().getExtras().get("isPayment");
            if (isPayment) {
                pagerMain.setCurrentItem(1);
            }
        }

        if (getIntent().getExtras() != null && getIntent().getExtras().get(getResources().getString(R.string.notification)) != null) {
            pagerMain.setCurrentItem(1);
        }

        identityManager = awsMobileClient.getIdentityManager();

        Intent intent = new Intent(this, PushFirebaseInstanceIdService.class);
        startService(intent);

    }

    @OnClick(R.id.image_search_button_main)
    public void onClickImageSearchButton(View view) {
//        Intent intent = new Intent(MainActivity.this, SMSAuthActivity.class);
//        startActivity(intent);
        identityManager.signOut();

        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                myOrder = cheapnsaleService.getMyCurrentOrder(((Application) getApplication()).getUserEmail());
                for (int i=myOrder.size() - 1; i >= 0 ; i--) {
                    if (DateUtil.stringToDate(myOrder.get(i).getPickupTime()).getTime() < DateTimeUtils.currentTimeMillis()) {
                        myOrder.remove(i);
                    }
                }
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("종료");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                Intent launchIntent = new Intent(Intent.ACTION_MAIN);
                launchIntent.addCategory(Intent.CATEGORY_HOME);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(launchIntent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}