package com.xeed.cheapnsale.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.model.Order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MyOrderFragmentTest {

    private MyOrderFragment myOrderFragment;

    @Before
    public void setUp() throws Exception {
        myOrderFragment = new MyOrderFragment();
        SupportFragmentTestUtil.startVisibleFragment(myOrderFragment);
    }

    @Test
    public void whenTabMyOrderTab_thenShowMyNowOrderedList() throws Exception {

        RecyclerView myNowOrderList = (RecyclerView) myOrderFragment.getView().findViewById(R.id.my_order_ready_recycler_view);
        assertThat(myNowOrderList.getVisibility()).isEqualTo(View.VISIBLE);

        when(myOrderFragment.cheapnsaleService.getMyOrder(anyString())).thenReturn(makeMockOrders());
        myOrderFragment.onResume();
        assertThat(myNowOrderList.getAdapter().getItemCount()).isEqualTo(1);

    }

    private ArrayList<Order> makeMockOrders () {
        ArrayList<Order> orders = new ArrayList<>();

        Order order = new Order();
        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setOrderId("1");
        order.setOrderAt("2017.01.25_16:49:00");
        order.setStatus("DONE");
        order.setStoreName("놀부 보쌈");
        order.setPickupTime("2017.01.25_16:59:00");
        order.setStoreId("");
        ArrayList<Order.OrderMenu> menus = new ArrayList<>();

        Order.OrderMenu menu = order.new OrderMenu();
        menu.setMenuId("1");
        menu.setMenuName("족보세트");
        menu.setMenuItemCount(1);
        menu.setMenuCount(1);
        menu.setMenuTotalPrice(33000);
        menu.setMenuItemTotalPrice(33000);
        menu.setMenuType(1);
        menu.setMenuPrice(33000);
        menu.setMenuImg("");

        menus.add(menu);
        order.setMenus(menus);

        orders.add(order);
        return orders;
    }
}
