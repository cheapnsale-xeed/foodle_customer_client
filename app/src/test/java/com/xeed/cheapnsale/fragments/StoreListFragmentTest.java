package com.xeed.cheapnsale.fragments;

import android.location.Location;
import android.location.LocationManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.RequestCreator;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.TestApplication;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StoreListFragmentTest {

    StoreListFragment storeListFragment;

    private Application application;

    @Mock
    RequestCreator requestCreator;

    @Before
    public void setUp() throws Exception {
        application = (TestApplication) RuntimeEnvironment.application;

        Location testLocation = new Location(LocationManager.GPS_PROVIDER);
        testLocation.setLatitude(37.517646d);
        testLocation.setLongitude(127.101843d);

        application.setMyLocation(testLocation);

        storeListFragment = new StoreListFragment();
        SupportFragmentTestUtil.startFragment(storeListFragment);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenFragmentOnResume_thenShowStoreCountIsCorrectly() throws Exception {

        HashMap<String, Double> myLocationMap = new HashMap<String, Double>();
        myLocationMap.put("GPS_COORDINATES_LAT", Double.valueOf(37.517646d));
        myLocationMap.put("GPS_COORDINATES_LONG", Double.valueOf(127.101843d));

        when(storeListFragment.cheapnsaleService.getStoreListByLocation(myLocationMap)).thenReturn(makeMockStores());

        storeListFragment.onResume();
        RecyclerView recyclerView = (RecyclerView) storeListFragment.getView().findViewById(R.id.recycler_store_list);
        assertThat(recyclerView.getAdapter().getItemCount()).isEqualTo(2);
    }

    @Test
    public void whenFragmentOnResume_thenShowStoreListOrderbyDistance() throws Exception {

        HashMap<String, Double> myLocationMap = new HashMap<String, Double>();
        myLocationMap.put("GPS_COORDINATES_LAT", Double.valueOf(37.517646d));
        myLocationMap.put("GPS_COORDINATES_LONG", Double.valueOf(127.101843d));

        when(storeListFragment.cheapnsaleService.getStoreListByLocation(myLocationMap)).thenReturn(makeMockStores());

        storeListFragment.onResume();

        assertThat(storeListFragment.stores.get(0).getDistanceToStore()).isEqualTo(2084);

    }

    private ArrayList<Store> makeMockStores() {
        ArrayList<Store> stores = new ArrayList<>();

        Store store = new Store();
        store.setId("mock 1");
        store.setCategory("mock category");
        store.setRegNum("111-111-111");
        store.setName("mock store");
        store.setPaymentType("바로결제");
        store.setAvgPrepTime("20");
        store.setMainImageUrl("http://www.mock.com/mock.img");
        store.setPhoneNumber("010-1234-5678");
        store.setGpsCoordinatesLat(37.417646d);
        store.setGpsCoordinatesLong(127.001843d);
        store.setDistanceToStore(456);
        stores.add(store);

        store = new Store();
        store.setId("mock 2");
        store.setCategory("mock category");
        store.setRegNum("111-111-111");
        store.setName("mock store2");
        store.setPaymentType("바로결제");
        store.setAvgPrepTime("30");
        store.setMainImageUrl("http://www.mock.com/mock.img");
        store.setPhoneNumber("010-1234-5679");
        store.setGpsCoordinatesLat(37.527646d);
        store.setGpsCoordinatesLong(127.121843d);
        store.setDistanceToStore(356);
        stores.add(store);

        return stores;
    }
}