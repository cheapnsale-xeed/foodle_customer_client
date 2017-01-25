package com.xeed.cheapnsale.adapter;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MapStoreListAdapterTest {

    MapStoreListAdapter mapStoreListAdapter;

    @Before
    public void setUp() throws Exception {
        mapStoreListAdapter = new MapStoreListAdapter(RuntimeEnvironment.application, makeMockList());
    }


    private ArrayList<Store> makeMockList(){
        ArrayList<Store> stores = new ArrayList<>();

        Store store = new Store();
        store.setId("mock 1");
        store.setCategory("mock category");
        store.setName("mock store");
        store.setRegNum("02-1234-1234");
        store.setPaymentType("바로결제");
        store.setAvgPrepTime("20");
        store.setMainImageUrl("http://www.mock.com/mock.img");

        stores.add(store);
        return stores;
    }
}