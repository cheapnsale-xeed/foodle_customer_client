package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.CartListAdapter;
import com.xeed.cheapnsale.adapter.MapStoreListAdapter;
import com.xeed.cheapnsale.map.NMapPOIflagType;
import com.xeed.cheapnsale.map.NMapViewerResourceProvider;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends NMapActivity {

    private static final String LOG_TAG = "MapActivity";
    private final String CLIENT_ID = "06NkaJ4SLa6IICRbLzeO";// 애플리케이션 클라이언트 아이디 값

    @Inject
    public CheapnsaleService cheapnsaleService;

    @BindView(R.id.map_view)
    public NMapView mMapView;// 지도 화면 View

    @BindView(R.id.map_toolbar_list_button)
    public ImageView backListButton;

    @BindView(R.id.map_store_recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.map_tool_bar_title)
    public TextView toolbarTitle;

    private NMapMyLocationOverlay mMyLocationOverlay;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;
    private NMapController mMapController;
    private NMapOverlayManager mOverlayManager;
    ArrayList<Store> stores;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private MapStoreListAdapter mapStoreListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        ((Application) getApplication()).getApplicationComponent().inject(this);

        backListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mMapView.setScalingFactor(4f,true);
        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.getMapController();

        // create resource provider
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        // create overlay manager
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        // location manager
        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

        // compass manager
        mMapCompassManager = new NMapCompassManager(this);

        // create my location overlay
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);

        startMyLocation();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                stores = cheapnsaleService.getStoreList();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // set POI data
                NMapPOIdata poiData = new NMapPOIdata(stores.size(), mMapViewerResourceProvider, true);
                poiData.beginPOIdata(stores.size());
                for (int i = 0; i < stores.size(); ++i) {
                    poiData.addPOIitem(new NGeoPoint(stores.get(i).getGpsCoordinatesLong(), stores.get(i).getGpsCoordinatesLat()),
                            stores.get(i).getName(), NMapPOIflagType.SPOT, null);
                }

                poiData.endPOIdata();

                // create POI data overlay
                NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

                poiDataOverlay.selectPOIitem(0, false);

                mapStoreListAdapter = new MapStoreListAdapter(getApplicationContext(), stores);
                recyclerView.setAdapter(mapStoreListAdapter);
                mapStoreListAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private final OnDataProviderListener onDataProviderListener = new OnDataProviderListener() {
        @Override
        public void onReverseGeocoderResponse(NMapPlacemark placeMark, NMapError errInfo) {
            if (errInfo != null) {
                Log.e(LOG_TAG, "Failed to findPlacemarkAtLocation: error=" + errInfo.toString());
                MapActivity.super.setMapDataProviderListener(null);
                return;
            }
            toolbarTitle.setText(placeMark.dongName);
            MapActivity.super.setMapDataProviderListener(null);
        }
    };

    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

            if (mMapController != null) {
                mMapController.animateTo(myLocation);
                MapActivity.super.setMapDataProviderListener(onDataProviderListener);
                findPlacemarkAtLocation(myLocation.longitude, myLocation.latitude);
            }

            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
            Toast.makeText(MapActivity.this, "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {
            Toast.makeText(MapActivity.this, "Your current location is unavailable area.", Toast.LENGTH_LONG).show();
            stopMyLocation();
        }

    };

    private void startMyLocation() {

        if (mMyLocationOverlay != null) {
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                mOverlayManager.addOverlay(mMyLocationOverlay);
            }

            if (mMapLocationManager.isMyLocationEnabled()) {
                if (!mMapView.isAutoRotateEnabled()) {
                    mMyLocationOverlay.setCompassHeadingVisible(true);
                    mMapCompassManager.enableCompass();
                    mMapView.setAutoRotateEnabled(true, false);
                } else {
                    stopMyLocation();
                }
                mMapView.postInvalidate();
            } else {
                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Toast.makeText(MapActivity.this, "Please enable a My Location source in system settings",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);
                }
            }
        }
    }

    private void stopMyLocation() {
        if (mMyLocationOverlay != null) {
            mMapLocationManager.disableMyLocation();

            if (mMapView.isAutoRotateEnabled()) {
                mMyLocationOverlay.setCompassHeadingVisible(false);
                mMapCompassManager.disableCompass();
                mMapView.setAutoRotateEnabled(false, false);
            }
        }
    }

}
