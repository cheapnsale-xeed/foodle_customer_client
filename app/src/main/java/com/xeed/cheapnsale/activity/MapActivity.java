package com.xeed.cheapnsale.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
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

        mapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정

        mapView.setScalingFactor(2f,true);
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

        mapStoreListAdapter = new MapStoreListAdapter(getApplicationContext(), stores);
        storeRecyclerViewPager.setAdapter(mapStoreListAdapter);

        if (((Application) getApplication()).getMyLocation() != null) {
            myLocation = new NGeoPoint(((Application) getApplication()).getMyLocation().getLongitude(), ((Application) getApplication()).getMyLocation().getLatitude());

            if (mMapController != null) {
                mMapController.animateTo(myLocation);
            }

            // 내 위치 보여줄 수 있음.
            NMapPOIdata myLocPoiData = new NMapPOIdata(1, mMapViewerResourceProvider, true);
            myLocPoiData.beginPOIdata(1);
            myLocPoiData.addPOIitem(myLocation, null, NMapPOIflagType.MY_LOC, null);
            myLocPoiData.endPOIdata();
            poiDataOverlay = mOverlayManager.createPOIdataOverlay(myLocPoiData, null);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(myLocation != null){
            super.setMapDataProviderListener(onDataProviderListener);
            findPlacemarkAtLocation(myLocation.longitude, myLocation.latitude);

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
                        stores.get(i).setDistanceToStore((int) NGeoPoint.getDistance(myLocation, new NGeoPoint(stores.get(i).getGpsCoordinatesLong(), stores.get(i).getGpsCoordinatesLat())));
                    }

                    mapStoreListAdapter.updateData(stores);
                }
            }.execute();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        mapView.setScalingFactor(1f);
        super.onDestroy();
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

}
