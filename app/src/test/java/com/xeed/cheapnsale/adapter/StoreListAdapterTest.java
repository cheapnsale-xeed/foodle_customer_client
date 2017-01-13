package com.xeed.cheapnsale.adapter;

import android.widget.LinearLayout;

import com.squareup.picasso.RequestCreator;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        StoreListAdapter.StoreListHolder holder = storeListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
        when(holder.picasso.load(anyString())).thenReturn(mock(RequestCreator.class));

        storeListAdapter.onBindViewHolder(holder,0);

        assertThat(holder.nameView.getText()).isEqualTo("mock store");
        assertThat(holder.paymentTextView.getText()).isEqualTo("바로결제");
        assertThat(holder.avgPrepTimeTextView.getText()).isEqualTo("20분");
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
        store.setMainImg("http://www.mock.com/mock.img");

        stores.add(store);
        return stores;
    }
}