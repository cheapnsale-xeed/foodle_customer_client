package com.xeed.cheapnsale.adapter;

import android.widget.LinearLayout;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.holder.OrderCartItemListHolder;
import com.xeed.cheapnsale.service.model.Menu;

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
public class OrderCartItemListAdapterTest {

    private ArrayList<Menu> mockCart = new ArrayList<>();
    private OrderCartItemListAdapter orderCartItemListAdapter;
    private OrderCartItemListHolder holder;

    @Before
    public void setUp() throws Exception {
        mockCart.add(new Menu("mock-1", "mockItem-1", 22000, 3, "mockSrc-1"));
        mockCart.add(new Menu("mock-2", "mockItem-2", 12000, 2, "mockSrc-2"));
        mockCart.add(new Menu("mock-3", "mockItem-3", 6000, 1, "mockSrc-3"));
        mockCart.add(new Menu("mock-4", "mockItem-4", 8800, 5, "mockSrc-4"));

        orderCartItemListAdapter = new OrderCartItemListAdapter(mockCart);
        holder = orderCartItemListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
    }

    @Test
    public void whenAdapterIsCreated_thenCartItemInfoIsCorrect() throws Exception {
        orderCartItemListAdapter.onBindViewHolder(holder,0);

        assertThat(holder.textItemNameOrder.getText().toString()).isEqualTo("mockItem-1");
        assertThat(holder.textItemPriceOrder.getText().toString()).isEqualTo("66,000");
        assertThat(holder.textItemCountOrder.getText().toString()).isEqualTo("3");

        orderCartItemListAdapter.onBindViewHolder(holder,1);

        assertThat(holder.textItemNameOrder.getText().toString()).isEqualTo("mockItem-2");
        assertThat(holder.textItemPriceOrder.getText().toString()).isEqualTo("24,000");
        assertThat(holder.textItemCountOrder.getText().toString()).isEqualTo("2");

        orderCartItemListAdapter.onBindViewHolder(holder,2);

        assertThat(holder.textItemNameOrder.getText().toString()).isEqualTo("mockItem-3");
        assertThat(holder.textItemPriceOrder.getText().toString()).isEqualTo("6,000");
        assertThat(holder.textItemCountOrder.getText().toString()).isEqualTo("1");

        orderCartItemListAdapter.onBindViewHolder(holder,3);

        assertThat(holder.textItemNameOrder.getText().toString()).isEqualTo("mockItem-4");
        assertThat(holder.textItemPriceOrder.getText().toString()).isEqualTo("44,000");
        assertThat(holder.textItemCountOrder.getText().toString()).isEqualTo("5");
    }
}