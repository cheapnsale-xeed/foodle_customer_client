package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
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

    @Mock
    private ViewPager mockViewPager;
    private TabLayout tabLayout;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        tabLayout = (TabLayout) mainActivity.findViewById(R.id.tab_main);
    }

    @Test
    public void whenActivityIsStarted_thenShowCheapnsaleTitle() throws Exception {
        TextView cheapnsaleTitleText = (TextView) mainActivity.findViewById(R.id.text_title_main);
        assertThat(cheapnsaleTitleText.getText()).isEqualTo("Foodle");
    }

    @Test
    public void whenActivityIsStarted_thenShowFirstFragmentTitle() throws Exception {
        assertThat(tabLayout.getSelectedTabPosition()).isEqualTo(0);
        assertThat(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText()).isEqualTo("전체보기");
    }

    @Test
    public void whenTabChange_thenFragmentTitleChange() throws Exception {
        mainActivity.pagerMain.setCurrentItem(1);
        assertThat(tabLayout.getSelectedTabPosition()).isEqualTo(1);
        when(mainActivity.cheapnsaleService.getMyCurrentOrder(anyString())).thenReturn(makeMockMyOrder());
        mainActivity.onResume();
        assertThat(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText()).isEqualTo("내 주문(2)");


        mainActivity.pagerMain.setCurrentItem(0);
        assertThat(tabLayout.getSelectedTabPosition()).isEqualTo(0);
        assertThat(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText()).isEqualTo("전체보기");
    }

    @Test
    public void whenMapButtonTab_thenGoMapActivity() throws Exception {
        ImageView mapButton = (ImageView) mainActivity.findViewById(R.id.image_map_button_map);
        mapButton.performClick();

        Intent expectedIntent = new Intent(mainActivity, MapActivity.class);
        Intent actualIntent = shadowOf(mainActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenPaymentIsSuccess_thenShowMyOrderFragment() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, MainActivity.class);
        intent.putExtra("isPayment", true);
        mainActivity = Robolectric.buildActivity(MainActivity.class).withIntent(intent).create().get();
        
        assertThat(mainActivity.pagerMain.getCurrentItem()).isEqualTo(1);
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