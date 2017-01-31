package com.xeed.cheapnsale.fragments;

import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.RequestCreator;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StoreListFragmentTest {

    StoreListFragment storeListFragment;

    @Mock
    RequestCreator requestCreator;

    @Before
    public void setUp() throws Exception {
        storeListFragment = new StoreListFragment();
        SupportFragmentTestUtil.startFragment(storeListFragment);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenFragmentOnResume_thenShowStoreCountIsCorrectly() throws Exception {
        when(storeListFragment.cheapnsaleService.getStoreList()).thenReturn(makeMockStores());

        storeListFragment.onResume();
        RecyclerView recyclerView = (RecyclerView) storeListFragment.getView().findViewById(R.id.recycler_store_list);
        assertThat(recyclerView.getAdapter().getItemCount()).isEqualTo(1);
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
        store.setGpsCoordinatesLat(37.517646d);
        store.setGpsCoordinatesLong(127.101843d);
        store.setDistanceToStore(456);
        stores.add(store);

        return stores;
    }
}