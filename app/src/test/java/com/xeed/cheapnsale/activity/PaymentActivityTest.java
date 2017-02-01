package com.xeed.cheapnsale.activity;

import android.content.Intent;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Order;
import com.xeed.cheapnsale.service.model.Payment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PaymentActivityTest {

    private PaymentActivity paymentActivity;

    @Before
    public void setUp() throws Exception {

        Intent intent = new Intent(RuntimeEnvironment.application, PaymentActivity.class);
        intent.putExtra("payment", makePaymentData());
        intent.putExtra("order", makeOrderData());

        paymentActivity = Robolectric.buildActivity(PaymentActivity.class).withIntent(intent).create().get();

    }

    @Test
    public void whenPaymentIsSuccess_thenGotoMyOrder() throws Exception {
        paymentActivity.androidBridge.paymentHandler("paid","");

        Intent expectedIntent = new Intent(paymentActivity, MainActivity.class);
        Intent actualIntent = shadowOf(paymentActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
        assertThat(paymentActivity.isFinishing()).isTrue();
    }

    @Test
    public void whenPaymentIsFailed_thenShowToastMessage() throws Exception {
        paymentActivity.androidBridge.paymentHandler("failed","fail test");

        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("fail test");
        assertThat(paymentActivity.isFinishing()).isTrue();
    }

    private Payment makePaymentData() {
        Payment payment = new Payment();
        payment.setMerchantUid("test_id");
        payment.setAmount(BigDecimal.valueOf(1004));
        return payment;
    }

    private Order makeOrderData() {
        Order order = new Order();
        ArrayList<Menu> mockMenu = new ArrayList<>();
        Menu menu = new Menu();
        menu.setMenuName("Mock Menu");
        menu.setMenuId("MENU-1");
        menu.setMenuPrice(10000);
        menu.setMenuItemCount(1);
        mockMenu.add(menu);
        order.setMenus(mockMenu);
        return order;
    }


}