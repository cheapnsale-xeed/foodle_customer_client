package com.xeed.cheapnsale.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.map.NMapPOIflagType;
import com.xeed.cheapnsale.map.NMapViewerResourceProvider;

public class MapActivity extends NMapActivity {

    private static final String LOG_TAG = "MapActivity";

    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "06NkaJ4SLa6IICRbLzeO";// 애플리케이션 클라이언트 아이디 값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMapView = (NMapView) findViewById(R.id.map_view);

        //mMapView = new NMapView(this);

        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();


        // create resource provider
        NMapViewerResourceProvider mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        // create overlay manager
        NMapOverlayManager mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(127.0630205, 37.5091300, "Pizza 777-111", markerId, 0);
        poiData.addPOIitem(127.061, 37.51, "Pizza 123-456", markerId, 0);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

        // show all POI data
        poiDataOverlay.showAllPOIdata(0);

        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(new NMapPOIdataOverlay.OnStateChangeListener() {
            @Override
            public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
                if (nMapPOIitem != null) {
                    Log.i(LOG_TAG, "onFocusChanged: " + nMapPOIitem.toString());
                } else {
                    Log.i(LOG_TAG, "onFocusChanged: ");
                }
            }

            @Override
            public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
                // [[TEMP]] handle a click event of the callout
                Toast.makeText(MapActivity.this, "onCalloutClick: " + nMapPOIitem.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
