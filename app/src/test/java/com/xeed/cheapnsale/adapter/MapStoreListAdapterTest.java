package com.xeed.cheapnsale.adapter;

import android.view.View;
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

    @Test
    public void whenAdapterIsCreated_thenHolderInformationIsCorrectly() throws Exception {
        mapStoreListAdapter.onBindViewHolder(holder,0);

        assertThat(holder.textStoreNameMap.getText()).isEqualTo("mock store");
        assertThat(holder.textAvgPrepTimeMap.getText()).isEqualTo("20분");
        assertThat(holder.textDistanceMap.getText()).isEqualTo("456m");
        assertThat(holder.textArrivalTimeMap.getText()).isEqualTo("도보 7분");
    }

    @Test
    public void whenDistanceIsLessThan1500m_thenWalkTimeIsIndicated() throws Exception {
        mapStoreListAdapter.onBindViewHolder(holder,0);

        assertThat(holder.textArrivalTimeMap.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(holder.viewVerticalBarMap.getVisibility()).isEqualTo(View.VISIBLE);

        ArrayList<Store> stores = makeMockList();
        stores.get(0).setDistanceToStore(2000);

        mapStoreListAdapter.updateData(stores);
        mapStoreListAdapter.onBindViewHolder(holder,0);

        assertThat(holder.textArrivalTimeMap.getVisibility()).isEqualTo(View.GONE);
        assertThat(holder.viewVerticalBarMap.getVisibility()).isEqualTo(View.GONE);
    }

    private ArrayList<Store> makeMockList(){
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