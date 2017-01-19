package com.xeed.cheapnsale;

import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class OrderActivityTest {

    private OrderActivity orderActivity;
    private RadioButton orderTimeNowRadioButton;
    private RadioButton orderTimeTodayRadioButton;
    private TextView orderPickUpTime;
    private RadioButton orderPaymentCreditRadioButton;
    private RadioButton orderPaymentKakaopayRadioButton;

    @Before
    public void setUp() throws Exception {
        orderActivity = Robolectric.buildActivity(OrderActivity.class).create().get();

        orderTimeNowRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_time_now_radio_button);
        orderTimeTodayRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_time_today_radio_button);
        orderPaymentCreditRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_payment_credit);
        orderPaymentKakaopayRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_payment_kakaopay);
        orderPickUpTime = (TextView) orderActivity.findViewById(R.id.order_pick_up_time);
    }

    @Test
    public void whenActivityIsStart_thenNowOrderRadioButtonSelected() throws Exception {
        assertThat(orderTimeNowRadioButton.isChecked()).isTrue();
        assertThat(orderTimeTodayRadioButton.isChecked()).isFalse();
    }

    @Test
    public void whenActivityIsStart_thenShowPickupTimeFromDatabase() throws Exception {
        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();
        assertThat(orderPickUpTime.getText()).isEqualTo("20분");
    }

    @Test
    public void whenOrderTodayRadioButtonTab_thenButtonChecked() throws Exception {
        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();

        assertThat(orderTimeTodayRadioButton.isChecked()).isFalse();
        orderTimeTodayRadioButton.setChecked(true);
        assertThat(orderTimeTodayRadioButton.isChecked()).isTrue();
        assertThat(orderTimeNowRadioButton.isChecked()).isFalse();
    }

    @Test
    public void whenClickBackToOrderButton_thenBackToOrderActivity() throws Exception {
        ImageButton backToOrderImgButton = (ImageButton)orderActivity.findViewById(R.id.main_toolbar_back_button);
        backToOrderImgButton.performClick();
        assertThat(orderActivity.isFinishing()).isTrue();
    }

    @Test
    public void whenClickOrderTodayRadioButton_thenShowPickUpTimeDialog() throws Exception {
        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();

        assertThat(orderActivity.pickerDialog.isShowing()).isFalse();

        orderTimeTodayRadioButton.performClick();

        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        assertThat(pickerDialog.isShowing()).isTrue();
    }

    @Test
    public void whenClickCancelButton_thenClosePickUpTimeDialog() throws Exception {
        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();

        orderTimeTodayRadioButton.performClick();

        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        assertThat(pickerDialog.isShowing()).isTrue();

        TextView cancelTextView = (TextView) pickerDialog.getView().findViewById(R.id.order_time_picker_cancel);
        cancelTextView.performClick();

        assertThat(pickerDialog.isShowing()).isFalse();
        assertThat(orderTimeTodayRadioButton.isChecked()).isFalse();
    }

    @Test
    public void whenPickerPopup_thenFirstPickerValueCheck() throws Exception {

        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();
        orderTimeTodayRadioButton.performClick();
        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();

        NumberPicker numberPicker = (NumberPicker) pickerDialog.findViewById(R.id.order_time_picker);
        assertThat(orderPickUpTime.getText().equals(numberPicker.getDisplayedValues()[0].replaceAll(" 후", ""))).isTrue();

    }

    @Test
    public void whenPickTimeAndComplete_thenPickupTimeSet() throws Exception {
        RelativeLayout todayOrderLayout = (RelativeLayout) orderActivity.findViewById(R.id.today_order_layout);

        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();
        orderTimeTodayRadioButton.performClick();

        assertThat(todayOrderLayout.getVisibility()).isEqualTo(View.INVISIBLE);

        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        //30분선택.
        orderActivity.selectedNumberIndex = 2;

        TextView acceptButton = (TextView) pickerDialog.getView().findViewById(R.id.order_time_picker_accept);
        acceptButton.performClick();

        TextView timeToPickupMin = (TextView) orderActivity.findViewById(R.id.time_to_pickup_value);
        assertThat(timeToPickupMin.getText()).isEqualTo("30");

        assertThat(todayOrderLayout.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    public void whenPaymentTypeShow_thenCreditChecked() throws Exception {
        assertThat(orderPaymentCreditRadioButton.isChecked()).isTrue();

        orderPaymentKakaopayRadioButton.performClick();

        assertThat(orderPaymentCreditRadioButton.isChecked()).isFalse();
        assertThat(orderPaymentKakaopayRadioButton.isChecked()).isTrue();
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