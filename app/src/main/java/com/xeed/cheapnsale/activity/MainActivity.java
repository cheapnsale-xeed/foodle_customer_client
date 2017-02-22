package com.xeed.cheapnsale.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.StoreListAdapter;
import com.xeed.cheapnsale.backgroundservice.PushFirebaseInstanceIdService;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.user.AWSMobileClient;
import com.xeed.cheapnsale.user.IdentityManager;
import com.xeed.cheapnsale.util.CalcDistanceUtil;
import com.xeed.cheapnsale.wrapper.OnSwipeTouchListener;
import com.xeed.cheapnsale.wrapper.SlidingWrapperActivity;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends SlidingWrapperActivity {

    @Inject
    public CheapnsaleService cheapnsaleService;

    @Inject
    public AWSMobileClient awsMobileClient;

    private IdentityManager identityManager;
    private StoreListAdapter storeListAdapter;
    private RecyclerView recyclerView;

    public LocationManager mLocationManager;

    ArrayList<Store> stores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getApplication()).getApplicationComponent().inject(this);

        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);

        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.sliding_menu);
        setSlidingActionBarEnabled(false);

        ButterKnife.bind(this);

        Intent intent = new Intent(this, PushFirebaseInstanceIdService.class);
        startService(intent);

        // configure the SlidingMenu
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidth(0);
        slidingMenu.setFadeEnabled(false);
        slidingMenu.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeLeft() {
                getSlidingMenu().toggle();
            }
        });

        TextView nickMenuNavi = (TextView) findViewById(R.id.text_nick_menu_navi);
        Typeface face = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/TmonMonsori.otf.otf");
        nickMenuNavi.setTypeface(face);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_store_list);

        storeListAdapter = new StoreListAdapter(getApplicationContext(), new ArrayList<Store>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(storeListAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        identityManager = awsMobileClient.getIdentityManager();

        findViewById(R.id.image_x_button_menu_navi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSlidingMenu().toggle();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        showProgressDialog();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                stores = cheapnsaleService.getStoreList();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialogDismiss();
                Log.d("Store : ", "Create");
                sortStoreAndUpdateList().execute();
            }

        }.execute();

        ((Application) getApplication()).getCart().clearCartItems();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogDismiss();
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

    @OnClick(R.id.image_menu_button_main)
    public void onClickMenuNaviButton(View view) {
        getSlidingMenu().toggle();
    }

    @OnClick(R.id.image_search_button_main)
    public void onClickImageSearchButton(View view) {
        identityManager.signOut();

        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.image_map_button_map)
    public void onClickMapLinkButton(View view) {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
    }

    private AsyncTask<Void, Void, Void> sortStoreAndUpdateList () {

        return new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (((Application) getApplication()).getMyLocation() != null) {
                    setStoreDistance(((Application) getApplication()).getMyLocation());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Collections.sort(stores, Store.StoreIdAsc);
                Collections.sort(stores, Store.RecommendDesc);
                storeListAdapter.updateData(stores);
            }
        };

    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("Store, longitude", ""+location.getLongitude()); // 경도
            Log.d("Store, Latitude", ""+location.getLatitude()); // 위도

            if (stores != null) {
                setStoreDistance(location);
                storeListAdapter.notifyDataSetChanged();
            }

            if (getApplication() != null) {
                ((Application) getApplication()).setMyLocation(location);
                mLocationManager.removeUpdates(mLocationListener);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public void setStoreDistance(Location location) {
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).setDistanceToStore((int)
                    CalcDistanceUtil.calDistance(location.getLatitude(), location.getLongitude(),
                            stores.get(i).getGpsCoordinatesLat(), stores.get(i).getGpsCoordinatesLong())
            );
        }
    }
}