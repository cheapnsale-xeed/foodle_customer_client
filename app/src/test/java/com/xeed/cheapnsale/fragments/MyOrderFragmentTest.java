package com.xeed.cheapnsale.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.MyOrderCurrentAdapter;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
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
    private MyOrderCurrentAdapter myOrderCurrentAdapter;
    private MyOrderCurrentAdapter.MyOrderCurrentHolder myOrderCurrentHolder;

    @Before
    public void setUp() throws Exception {
        myOrderFragment = new MyOrderFragment();
        SupportFragmentTestUtil.startVisibleFragment(myOrderFragment);
        myOrderCurrentAdapter = new MyOrderCurrentAdapter(RuntimeEnvironment.application.getApplicationContext(), makeMockOrders());
        myOrderCurrentHolder = myOrderCurrentAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
    }

    @Test
    public void whenTabMyOrderTab_thenShowMyNowOrderedList() throws Exception {

        RecyclerView myNowOrderList = (RecyclerView) myOrderFragment.getView().findViewById(R.id.recycler_ready_my_order);
        assertThat(myNowOrderList.getVisibility()).isEqualTo(View.VISIBLE);

        when(myOrderFragment.cheapnsaleService.getMyOrder(anyString())).thenReturn(makeMockOrders());
        myOrderFragment.onResume();
        assertThat(myNowOrderList.getAdapter().getItemCount()).isEqualTo(3);

    }

    @Test
    public void whenOrderStatusIsReady_thenShowImageStatusLabel() throws Exception {

        myOrderCurrentAdapter.onBindViewHolder(myOrderCurrentHolder, 0);
        assertThat(myOrderCurrentHolder.textStatusMyOrder.getText()).isEqualTo("접수\n대기");

        myOrderCurrentAdapter.onBindViewHolder(myOrderCurrentHolder, 1);
        assertThat(myOrderCurrentHolder.textStatusMyOrder.getText()).isEqualTo("준비\n중");

        myOrderCurrentAdapter.onBindViewHolder(myOrderCurrentHolder, 2);
        assertThat(myOrderCurrentHolder.textStatusMyOrder.getText()).isEqualTo("준비\n완료");

    }

    private ArrayList<Order> makeMockOrders () {
        ArrayList<Order> orders = new ArrayList<>();
        Order order = new Order();
        ArrayList<Menu> menus = new ArrayList<>();

        Menu menu = new Menu();
        menu.setMenuId("1");
        menu.setMenuName("족보세트");
        menu.setMenuItemCount(1);
        menu.setMenuItemTotalPrice(33000);
        menu.setMenuType(1);
        menu.setMenuPrice(33000);
        menu.setMenuImg("");
        menus.add(menu);

        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setOrderId("2");
        order.setOrderAt("2017.01.31_16:49:00");
        order.setStatus("READY");
        order.setStoreName("Subway");
        order.setPickupTime("2017.01.31_16:59:00");
        order.setStoreId("");
        order.setMenus(menus);
        orders.add(order);

        order = new Order();
        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setOrderId("3");
        order.setOrderAt("2017.01.31_14:49:00");
        order.setStatus("PREPARE");
        order.setStoreName("본죽");
        order.setPickupTime("2017.01.31_14:59:00");
        order.setStoreId("");
        order.setMenus(menus);
        orders.add(order);

        order = new Order();
        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setOrderId("1");
        order.setOrderAt("2017.01.25_16:49:00");
        order.setStatus("DONE");
        order.setStoreName("놀부 보쌈");
        order.setPickupTime("2017.01.25_16:59:00");
        order.setStoreId("");
        order.setMenus(menus);
        orders.add(order);

        return orders;
    }
}
