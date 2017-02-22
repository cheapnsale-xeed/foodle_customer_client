package com.xeed.cheapnsale.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.MainPagerAdapter;
import com.xeed.cheapnsale.adapter.MainTabPagerAdapter;
import com.xeed.cheapnsale.backgroundservice.PushFirebaseInstanceIdService;
import com.xeed.cheapnsale.fragments.MainFragment;
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

    private MainPagerAdapter mainPagerAdapter;
    public ViewPager mainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getApplication()).getApplicationComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, PushFirebaseInstanceIdService.class);
        startService(intent);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainViewPager = (ViewPager) findViewById(R.id.pager_main);
        mainViewPager.setAdapter(mainPagerAdapter);

        mainViewPager.setCurrentItem(1);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void selectCurrentFragment(int index) {
        mainViewPager.setCurrentItem(index);
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