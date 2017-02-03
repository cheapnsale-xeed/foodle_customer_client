package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.MapStoreListAdapter;
import com.xeed.cheapnsale.map.NMapPOIflagType;
import com.xeed.cheapnsale.map.NMapViewerResourceProvider;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends NMapActivity {

    private static final String LOG_TAG = "MapActivity";
    private final String CLIENT_ID = "06NkaJ4SLa6IICRbLzeO";// 애플리케이션 클라이언트 아이디 값

    @Inject
    public CheapnsaleService cheapnsaleService;

    @BindView(R.id.map_view)
    public NMapView mapView;// 지도 화면 View

    @BindView(R.id.image_list_button_map)
    public ImageView imageListButtonMap;

    @BindView(R.id.recycler_store_map)
    public RecyclerViewPager storeRecyclerViewPager;

    @BindView(R.id.text_title_map)
    public TextView textTitleMap;

    private NMapMyLocationOverlay mMyLocationOverlay;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;
    private NMapController mMapController;
    private NMapOverlayManager mOverlayManager;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private MapStoreListAdapter mapStoreListAdapter;

    private ArrayList<Store> stores = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private NGeoPoint myLocation;

    private NMapPOIdataOverlay poiDataOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ((Application) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        mapView.setScalingFactor(4f,true);
        mapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapView.setFocusable(true);
        mapView.setFocusableInTouchMode(true);
        mapView.requestFocus();
        mapView.getMapController().setZoomLevel(11);

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mapView.getMapController();

        // create resource provider
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        // create overlay manager
        mOverlayManager = new NMapOverlayManager(this, mapView, mMapViewerResourceProvider);

        // location manager
        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

        // compass manager
        mMapCompassManager = new NMapCompassManager(this);

        // create my location overlay
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);

        startMyLocation();

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        storeRecyclerViewPager.setLayoutManager(linearLayoutManager);
        storeRecyclerViewPager.setHasFixedSize(true);
        storeRecyclerViewPager.setLongClickable(true);

        storeRecyclerViewPager.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    poiDataOverlay.selectPOIitem(((RecyclerViewPager) recyclerView).getCurrentPosition(), false);
                }
            }
        });

        if (((Application) getApplication()).getMyLocation() != null) {
            myLocation = new NGeoPoint(((Application) getApplication()).getMyLocation().getLongitude(), ((Application) getApplication()).getMyLocation().getLatitude());
            if (mMapController != null) {
                mMapController.animateTo(myLocation);
            }
        }

        // 내 위치 보여줄 수 있음.
        // mMapLocationManager.enableMyLocation(true) 는 사용하지 않는다.
        NMapPOIdata myLocPoiData = new NMapPOIdata(1, mMapViewerResourceProvider, true);
        myLocPoiData.beginPOIdata(1);
        myLocPoiData.addPOIitem(myLocation, null, NMapPOIflagType.MY_LOC, null);
        myLocPoiData.endPOIdata();
        poiDataOverlay = mOverlayManager.createPOIdataOverlay(myLocPoiData, null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.image_list_button_map)
    public void onClickBackButton(View view) {
        onBackPressed();
    }

    private final OnDataProviderListener onDataProviderListener = new OnDataProviderListener() {
        @Override
        public void onReverseGeocoderResponse(NMapPlacemark placeMark, NMapError errInfo) {
            if (errInfo != null) {
                Log.e(LOG_TAG, "Failed to findPlacemarkAtLocation: error=" + errInfo.toString());
                MapActivity.super.setMapDataProviderListener(null);
                return;
            }
            Log.d("Map : ", "onDataProviderListener : " + placeMark.dongName);
            textTitleMap.setText(placeMark.dongName);
            MapActivity.super.setMapDataProviderListener(null);

            mMapLocationManager.removeOnLocationChangeListener(onMyLocationChangeListener);
        }
    };

    private final NMapPOIdataOverlay.OnStateChangeListener onStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {
        @Override
        public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
            if (nMapPOIitem != null && nMapPOIitem.getTag() != null) {
                Store tag = (Store) nMapPOIitem.getTag();
                nMapPOIdataOverlay.selectPOIitem(nMapPOIitem,true);
                storeRecyclerViewPager.scrollToPosition(stores.indexOf(tag));
            }
        }

        @Override
        public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
            // do nothing.
        }
    };

    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

            if (mMapController != null) {
//                mMapController.animateTo(myLocation);
                Log.d("Map : ", "onLocationChanged");
                MapActivity.super.setMapDataProviderListener(onDataProviderListener);
                findPlacemarkAtLocation(myLocation.longitude, myLocation.latitude);
            }

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    stores = cheapnsaleService.getStoreList();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {

                    // set POI data
                    NMapPOIdata poiData =  new NMapPOIdata(stores.size(), mMapViewerResourceProvider, true);
                    poiData.beginPOIdata(stores.size());
                    for (int i = 0; i < stores.size(); ++i) {
                        poiData.addPOIitem(new NGeoPoint(stores.get(i).getGpsCoordinatesLong(), stores.get(i).getGpsCoordinatesLat()),
                                null, NMapPOIflagType.SPOT, stores.get(i));
                    }

                    poiData.endPOIdata();

                    // create POI data overlay
                    poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
                    poiDataOverlay.setOnStateChangeListener(onStateChangeListener);
                    poiDataOverlay.selectPOIitem(0, true);

                    for (int i = 0; i < stores.size(); ++i) {
                        if (mMapLocationManager.getMyLocation() != null) {
                            stores.get(i).setDistanceToStore((int) NGeoPoint.getDistance(mMapLocationManager.getMyLocation(), new NGeoPoint(stores.get(i).getGpsCoordinatesLong(), stores.get(i).getGpsCoordinatesLat())));
                        }
                    }

                    mapStoreListAdapter.updateData(stores);
                }
            }.execute();
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
                if (!mapView.isAutoRotateEnabled()) {
                    mMyLocationOverlay.setCompassHeadingVisible(true);
                    mMapCompassManager.enableCompass();
                    mapView.setAutoRotateEnabled(true, false);
                } else {
                    stopMyLocation();
                }
                mapView.postInvalidate();
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

            if (mapView.isAutoRotateEnabled()) {
                mMyLocationOverlay.setCompassHeadingVisible(false);
                mMapCompassManager.disableCompass();
                mapView.setAutoRotateEnabled(false, false);
            }
        }
    }
}
