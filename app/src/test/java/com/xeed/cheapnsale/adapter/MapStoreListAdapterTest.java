package com.xeed.cheapnsale.adapter;

import android.widget.LinearLayout;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.holder.MapStoreListHolder;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MapStoreListAdapterTest {

    private MapStoreListAdapter mapStoreListAdapter;
    private MapStoreListHolder holder;

    @Before
    public void setUp() throws Exception {
        mapStoreListAdapter = new MapStoreListAdapter(RuntimeEnvironment.application, makeMockList());
        holder = mapStoreListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
    }

    @Test
    public void whenMapStoreListCalled_thenShowItem() throws Exception {
        assertThat(mapStoreListAdapter.getItemCount()).isEqualTo(1);

        mapStoreListAdapter.onBindViewHolder(holder, 0);

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