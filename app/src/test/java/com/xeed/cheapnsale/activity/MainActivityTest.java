package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.fragments.MainFragment;
import com.xeed.cheapnsale.service.model.Order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private ImageView imageView;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void whenMainActivityIsCreated_thenShowMainFragment() throws Exception {
        assertThat(mainActivity.mainViewPager.getCurrentItem()).isEqualTo(1);
    }

    private ArrayList<Order> makeMockMyOrder() {

        ArrayList<Order> orders = new ArrayList<>();

        Order order = new Order();
        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setStoreId("1");
        order.setOrderId("1");
        order.setStoreName("놀부 보쌈");
        order.setStatus("DONE");
        order.setOrderAt("2020.01.22_17:19:00");
        order.setPickupTime("2020.01.22_17:19:00");
        orders.add(order);

        order = new Order();

        order.setEmail("cheapnsale.xeed@gmail.com");
        order.setStoreId("2");
        order.setOrderId("2");
        order.setStoreName("Subway");
        order.setStatus("READY");
        order.setOrderAt("2020.01.30_17:19:00");
        order.setPickupTime("2020.01.22_17:19:00");

        orders.add(order);

        return orders;
    }
}