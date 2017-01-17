package com.xeed.cheapnsale;

import android.widget.RadioButton;
import android.widget.TextView;

import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class OrderActivityTest {

    private OrderActivity orderActivity;

    @Before
    public void setUp() throws Exception {
        orderActivity = Robolectric.buildActivity(OrderActivity.class).create().get();
    }

    @Test
    public void whenActivityIsStart_thenNowOrderRadioButtonSelected() throws Exception {
        RadioButton orderTimeNowRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_time_now_radio_button);
        RadioButton orderTimeTodayRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_time_today_radio_button);

        assertThat(orderTimeNowRadioButton.isChecked()).isTrue();
        assertThat(orderTimeTodayRadioButton.isChecked()).isFalse();
    }

    @Test
    public void whenActivityIsStart_thenShowPickupTimeFromDatabase() throws Exception {
        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());

        orderActivity.onResume();

        TextView orderPickUpTime = (TextView) orderActivity.findViewById(R.id.order_pick_up_time);
        assertThat(orderPickUpTime.getText()).isEqualTo("20분");
    }

    @Test
    public void whenOrderTodayRadioButtonTab_thenButtonChecked() throws Exception {
        RadioButton orderTodayRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_time_today_radio_button);
        assertThat(orderTodayRadioButton.isChecked()).isFalse();

        orderTodayRadioButton.setChecked(true);
        assertThat(orderTodayRadioButton.isChecked()).isTrue();

        RadioButton orderNowRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_time_now_radio_button);
        assertThat(orderNowRadioButton.isChecked()).isFalse();
    }

    private Store makeMockData() {
        Store store = new Store();
        store.setId("mock 1");
        store.setCategory("mock category");
        store.setName("mock store");
        store.setRegNum("02-1234-1234");
        store.setPaymentType("바로결제");
        store.setAvgPrepTime("20");
        return store;
    }
}