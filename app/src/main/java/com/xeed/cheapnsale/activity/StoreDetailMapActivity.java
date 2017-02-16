package com.xeed.cheapnsale.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.map.NMapPOIflagType;
import com.xeed.cheapnsale.map.NMapViewerResourceProvider;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.util.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreDetailMapActivity extends NMapActivity {

    private final String CLIENT_ID = "06NkaJ4SLa6IICRbLzeO";// 애플리케이션 클라이언트 아이디 값

    @BindView(R.id.detail_map_view)
    public NMapView mapView;// 지도 화면 View

    @BindView(R.id.text_store_name_detail_map)
    public TextView storeName;

    @BindView(R.id.text_store_address_detail_map)
    public TextView storeAddress;

    @BindView(R.id.text_store_business_time_detail_map)
    public TextView storeBusinessTime;

    @BindView(R.id.image_store_call_detail_map)
    public ImageView callButton;

    @BindView(R.id.image_back_button_store_detail_map)
    public ImageView backButton;

    private Store store;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private NMapPOIdataOverlay poiDataOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail_map);

        ButterKnife.bind(this);

        store = (Store) getIntent().getExtras().get("store");

        StringBuffer businessTime = new StringBuffer();
        businessTime.append(store.getOpenTime()).append(" - ").append(store.getCloseTime())
                .append(" (")
                .append(getString(R.string.txt_order_end_time))
                .append(" ")
                .append(store.getEndTime()).append(")");

        storeName.setText(store.getName());
        storeAddress.setText(store.getAddress());
        storeBusinessTime.setText(businessTime);

        mapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정

        mapView.setScalingFactor(2f,true);
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapView.setFocusable(true);
        mapView.setFocusableInTouchMode(true);
        mapView.requestFocus();
        mapView.getMapController().setZoomLevel(12);

        // create resource provider
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        // create overlay manager
        mOverlayManager = new NMapOverlayManager(this, mapView, mMapViewerResourceProvider);

        // set POI data
        NMapPOIdata poiData =  new NMapPOIdata(1, mMapViewerResourceProvider, true);
        poiData.addPOIitem(new NGeoPoint(store.getGpsCoordinatesLong(), store.getGpsCoordinatesLat()),
                null, NMapPOIflagType.SPOT, store);

        // create POI data overlay
        poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.selectPOIitem(0, true);
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

    @OnClick(R.id.image_store_call_detail_map)
    public void touchCallButton () {
        CommonUtil.makePhoneCall(StoreDetailMapActivity.this, store.getPhoneNumber());
    }

    @OnClick(R.id.image_back_button_store_detail_map)
    public void touchBackButton () {
        onBackPressed();
    }
}
