package com.xeed.cheapnsale.fragments;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.CartActivity;
import com.xeed.cheapnsale.adapter.MyOrderCurrentAdapter;
import com.xeed.cheapnsale.adapter.MyOrderPastAdapter;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;
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

    private MyOrderPastAdapter myOrderPastAdapter;
    private MyOrderPastAdapter.MyOrderPastHolder myOrderPastHolder;

    private CartActivity cartActivity;

    @Before
    public void setUp() throws Exception {
        myOrderFragment = new MyOrderFragment();
        SupportFragmentTestUtil.startVisibleFragment(myOrderFragment);

        myOrderCurrentAdapter = new MyOrderCurrentAdapter(RuntimeEnvironment.application.getApplicationContext(), makeMockOrders());
        myOrderCurrentHolder = myOrderCurrentAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);

        myOrderPastAdapter = new MyOrderPastAdapter(RuntimeEnvironment.application.getApplicationContext(), makeMockOrders());
        myOrderPastHolder = myOrderPastAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);


    }

    @Test
    public void whenTabMyOrderTab_thenShowMyCurrentOrderedList() throws Exception {

        RecyclerView myNowOrderList = (RecyclerView) myOrderFragment.getView().findViewById(R.id.recycler_ready_my_order);
        assertThat(myNowOrderList.getVisibility()).isEqualTo(View.VISIBLE);

        when(myOrderFragment.cheapnsaleService.getMyOrder(anyString())).thenReturn(makeMockOrders());
        myOrderFragment.onResume();
        assertThat(myNowOrderList.getAdapter().getItemCount()).isEqualTo(2);

        myNowOrderList.getAdapter().onBindViewHolder(myOrderCurrentHolder, 0);
        assertThat(myOrderCurrentHolder.textStatusMyOrder.getText()).isEqualTo("접수\n대기");

        myNowOrderList.getAdapter().onBindViewHolder(myOrderCurrentHolder, 1);
        assertThat(myOrderCurrentHolder.textStatusMyOrder.getText()).isEqualTo("준비\n중");
//
//        myNowOrderList.getAdapter().onBindViewHolder(myOrderCurrentHolder, 2);
//        assertThat(myOrderCurrentHolder.textStatusMyOrder.getText()).isEqualTo("준비\n완료");

    }


    @Test
    public void whenTabMyOrderTab_thenShowMyPastOrderedList() throws Exception {

        RecyclerView myPastOrderList = (RecyclerView) myOrderFragment.getView().findViewById(R.id.recycler_finish_my_order);
        assertThat(myPastOrderList.getVisibility()).isEqualTo(View.VISIBLE);

        when(myOrderFragment.cheapnsaleService.getMyOrder(anyString())).thenReturn(makeMockOrders());
        myOrderFragment.onResume();
        assertThat(myPastOrderList.getAdapter().getItemCount()).isEqualTo(3);

        myPastOrderList.getAdapter().onBindViewHolder(myOrderPastHolder, 0);
        assertThat(myOrderPastHolder.textItemNameMyOrder.getText()).isEqualTo("족보세트 외 1개");
        assertThat(myOrderPastHolder.textStoreNameMyOrder.getText()).isEqualTo("Subway");
        assertThat(myOrderPastHolder.textPickupTimeMyOrder.getText()).isEqualTo("2017.02.01");

        myPastOrderList.getAdapter().onBindViewHolder(myOrderPastHolder, 1);
        assertThat(myOrderPastHolder.textItemNameMyOrder.getText()).isEqualTo("족보세트");
        assertThat(myOrderPastHolder.textStoreNameMyOrder.getText()).isEqualTo("놀부 보쌈");
        assertThat(myOrderPastHolder.textPickupTimeMyOrder.getText()).isEqualTo("2017.01.25");

    }

    @Test
    public void whenPaymentIsSuccess_thenShowFoodleDialog() throws Exception {
        myOrderFragment.getActivity().setIntent(new Intent().putExtra("isPayment", true));
        myOrderFragment.isPayment = true;
        myOrderFragment.onResume();

        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        assertThat(pickerDialog.isShowing()).isTrue();
        assertThat(myOrderFragment.isPayment).isFalse();
    }

    @Test
    public void whenReorderClick_thenShowReorderCartList() throws Exception {
        RecyclerView myPastOrderList = (RecyclerView) myOrderFragment.getView().findViewById(R.id.recycler_finish_my_order);

        when(myOrderFragment.cheapnsaleService.getMyOrder(anyString())).thenReturn(makeMockOrders());
        myOrderFragment.onResume();

        myPastOrderList.getAdapter().onBindViewHolder(myOrderPastHolder, 1);

        myOrderPastHolder.buttonReorder.performClick();
        Intent intent = new Intent(RuntimeEnvironment.application, CartActivity.class);
        intent.putExtra("IS_REORDER", true);
        cartActivity = Robolectric.buildActivity(CartActivity.class).withIntent(intent).create().get();

        TextView textView = (TextView)cartActivity.findViewById(R.id.text_title_cart);

        assertThat(textView.getText()).isEqualTo("재주문");


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
        order.setOrderAt("2020.01.31_16:49:00");
        order.setStatus("READY");
        order.setStoreName("Subway");
        order.setPickupTime("2020.01.31_16:59:00");
        order.setStoreId("");
        order.setMenus(menus);
        orders.add(order);

        order = new Order();
        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setOrderId("3");
        order.setOrderAt("2020.01.31_14:49:00");
        order.setStatus("PREPARE");
        order.setStoreName("본죽");
        order.setPickupTime("2020.01.31_14:59:00");
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

        order = new Order();
        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setOrderId("4");
        order.setOrderAt("2017.01.25_16:49:00");
        order.setStatus("FINISH");
        order.setStoreName("놀부 보쌈");
        order.setPickupTime("2017.01.25_16:59:00");
        order.setStoreId("");
        order.setMenus(menus);
        orders.add(order);

        ArrayList<Menu> menus2 = new ArrayList<>();

        menu = new Menu();
        menu.setMenuId("1");
        menu.setMenuName("족보세트");
        menu.setMenuItemCount(1);
        menu.setMenuItemTotalPrice(33000);
        menu.setMenuType(1);
        menu.setMenuPrice(33000);
        menu.setMenuImg("");
        menus2.add(menu);

        menu = new Menu();
        menu.setMenuId("2");
        menu.setMenuName("보쌈세트");
        menu.setMenuItemCount(2);
        menu.setMenuItemTotalPrice(30000);
        menu.setMenuType(1);
        menu.setMenuPrice(60000);
        menu.setMenuImg("");
        menus2.add(menu);

        order = new Order();
        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setOrderId("1");
        order.setOrderAt("2017.01.25_16:49:00");
        order.setStatus("FINISH");
        order.setStoreName("Subway");
        order.setPickupTime("2017.02.01_16:59:00");
        order.setStoreId("");
        order.setMenus(menus2);
        orders.add(order);

        return orders;
    }
}
