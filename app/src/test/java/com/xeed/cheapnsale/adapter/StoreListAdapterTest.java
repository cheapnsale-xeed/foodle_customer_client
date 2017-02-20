package com.xeed.cheapnsale.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.picasso.RequestCreator;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.holder.StoreListHolder;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StoreListAdapterTest {

    StoreListAdapter storeListAdapter;

    @Before
    public void setUp() throws Exception {
        storeListAdapter = new StoreListAdapter(RuntimeEnvironment.application, makeMockList());
    }

    @Test
    public void whenAdapterIsCreated_thenHolderInformationIsCorrectly() throws Exception {
        StoreListHolder holder = storeListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
        when(holder.picasso.load(anyString())).thenReturn(mock(RequestCreator.class));

        storeListAdapter.onBindViewHolder(holder,0);

        assertThat(holder.textNameStore.getText()).isEqualTo("mock store");
        assertThat(holder.textPrepTimeStore.getText()).isEqualTo("20분 걸려요");
        assertThat(holder.textDistanceStore.getText()).isEqualTo("456m");
        assertThat(holder.textArrivalTimeStore.getText()).isEqualTo("7분");

        storeListAdapter.onBindViewHolder(holder,1);

        assertThat(holder.textNameStore.getText()).isEqualTo("mock store");
        assertThat(holder.textPrepTimeStore.getText()).isEqualTo("20분 걸려요");
        assertThat(holder.textDistanceStore.getText()).isEqualTo("1.5km");
        assertThat(holder.textArrivalTimeStore.getText()).isEqualTo("24분");
    }

    @Test
    public void whenClickItemView_thenStartActivityIsStoreDetailActivity() throws Exception {
        StoreListHolder holder = storeListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
        when(holder.picasso.load(anyString())).thenReturn(mock(RequestCreator.class));

        storeListAdapter.onBindViewHolder(holder,0);

        holder.itemView.performClick();

        Intent actualIntent = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        Store actualStore = (Store) actualIntent.getSerializableExtra("store");

        assertThat(actualStore.getName()).isEqualTo("mock store");
        assertThat(actualStore.getPaymentType()).isEqualTo("바로결제");
    }

    @Test
    public void whenDistanceIsLessThan1500m_thenWalkTimeIsIndicated() throws Exception {
        StoreListHolder holder = storeListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
        when(holder.picasso.load(anyString())).thenReturn(mock(RequestCreator.class));

        storeListAdapter.onBindViewHolder(holder,0);

        assertThat(holder.textArrivalTimeStore.getVisibility()).isEqualTo(View.VISIBLE);

        ArrayList<Store> stores = makeMockList();
        stores.get(0).setDistanceToStore(2000);

        storeListAdapter.updateData(stores);
        storeListAdapter.onBindViewHolder(holder,0);

        assertThat(holder.textArrivalTimeStore.getVisibility()).isEqualTo(View.GONE);
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

        store = new Store();
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
        store.setDistanceToStore(1456);
        stores.add(store);

        return stores;
    }
}