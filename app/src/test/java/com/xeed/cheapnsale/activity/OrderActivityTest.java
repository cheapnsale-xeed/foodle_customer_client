package com.xeed.cheapnsale.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.TestApplication;
import com.xeed.cheapnsale.service.model.Cart;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Store;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static org.assertj.core.api.Assertions.assertThat;
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
    private EditText orderUserName;
    private EditText orderUserTel;
    private Button resetButton;

    @Before
    public void setUp() throws Exception {
        Cart mockCart = ((TestApplication) RuntimeEnvironment.application).getCart();
        mockCart.setStoreId("1");
        mockCart.addCartItem(new Menu("mock-1", "mockItem-1", 22000, 3, "mockSrc-1"));
        mockCart.addCartItem(new Menu("mock-2", "mockItem-2", 12000, 2, "mockSrc-2"));
        mockCart.addCartItem(new Menu("mock-3", "mockItem-3", 6000, 1, "mockSrc-3"));
        mockCart.addCartItem(new Menu("mock-4", "mockItem-4", 8800, 5, "mockSrc-4"));

        orderActivity = Robolectric.buildActivity(OrderActivity.class).create().get();

        orderTimeNowRadioButton = (RadioButton) orderActivity.findViewById(R.id.radio_now_button_order);
        orderTimeTodayRadioButton = (RadioButton) orderActivity.findViewById(R.id.radio_today_button_order);
        orderPaymentCreditRadioButton = (RadioButton) orderActivity.findViewById(R.id.radio_credit_button_order);
        orderPaymentKakaopayRadioButton = (RadioButton) orderActivity.findViewById(R.id.radio_kakaopay_button_order);
        orderPickUpTime = (TextView) orderActivity.findViewById(R.id.text_pickup_time_order);

        orderUserName = (EditText) orderActivity.findViewById(R.id.edit_user_info_name_order);
        orderUserTel = (EditText) orderActivity.findViewById(R.id.edit_user_info_tel_order);
        resetButton = (Button) orderActivity.findViewById(R.id.button_reselect_time_order);

        DateTime dt = new DateTime(2017, 2, 15, 14, 33);
        DateTimeUtils.setCurrentMillisFixed(dt.getMillis());
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
        ImageView backToOrderImgButton = (ImageView)orderActivity.findViewById(R.id.image_back_button_order);
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

        TextView cancelTextView = (TextView) pickerDialog.getView().findViewById(R.id.text_cancel_button_picker);
        cancelTextView.performClick();

        assertThat(pickerDialog.isShowing()).isFalse();
        assertThat(orderTimeTodayRadioButton.isChecked()).isFalse();
    }

    @Test
    public void whenPickerPopupIsOpened_thenFirstPickerValueCheck() throws Exception {

        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();
        orderTimeTodayRadioButton.performClick();
        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();

        NumberPicker numberPicker = (NumberPicker) pickerDialog.getView().findViewById(R.id.number_picker_order);
        // 33분 + 20분 (준비시간) + 2분 (반올림)
        assertThat(numberPicker.getDisplayedValues()[0]).isEqualTo("14:55");
    }

    @Test
    public void whenPickTimeAndComplete_thenPickupTimeSet() throws Exception {
        RelativeLayout todayOrderLayout = (RelativeLayout) orderActivity.findViewById(R.id.relative_today_detail_order);

        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();
        orderTimeTodayRadioButton.performClick();

        assertThat(todayOrderLayout.getVisibility()).isEqualTo(View.INVISIBLE);

        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        // 33분 + 20분 (준비시간) + 2분 (반올림) + (5분 * 2개 = 10분)
        orderActivity.selectedNumberIndex = 2;

        TextView acceptButton = (TextView) pickerDialog.getView().findViewById(R.id.text_accept_button_picker);
        acceptButton.performClick();

        TextView timeToPickupMin = (TextView) orderActivity.findViewById(R.id.text_dialog_picked_time_order);
        assertThat(timeToPickupMin.getText()).isEqualTo("15:05");

        assertThat(todayOrderLayout.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    public void whenPaymentTypeShow_thenCreditChecked() throws Exception {
        assertThat(orderPaymentCreditRadioButton.isChecked()).isTrue();

        orderPaymentKakaopayRadioButton.performClick();

        assertThat(orderPaymentCreditRadioButton.isChecked()).isFalse();
        assertThat(orderPaymentKakaopayRadioButton.isChecked()).isTrue();
    }

    @Test
    public void whenOrderUserInfoShow_thenNameIsCorrect() throws Exception {
        assertThat(orderUserName.getText().toString()).isEqualTo("이서진");
        assertThat(orderUserTel.getText().toString().equals("01012345678")).isTrue();
    }

    @Test
    public void whenOrderActivityOpen_thenTotalPriceAndOrderDetailGone() throws Exception {
        TextView totalPrice = (TextView) orderActivity.findViewById(R.id.text_total_price_order);
        View barView = orderActivity.findViewById(R.id.view_detail_horizon_bar_order);
        RecyclerView orderDetailView = (RecyclerView) orderActivity.findViewById(R.id.recycler_detail_list_order);

        assertThat(totalPrice.getText().toString()).isEqualTo("140,000");

        assertThat(barView.getVisibility()).isEqualTo(View.GONE);
        assertThat(orderDetailView.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void whenOrderDetailClicked_thenDetailCartShow() throws Exception {
        LinearLayout detailCartView = (LinearLayout) orderActivity.findViewById(R.id.linear_detail_order);
        View barView = orderActivity.findViewById(R.id.view_detail_horizon_bar_order);
        RecyclerView orderDetailView = (RecyclerView) orderActivity.findViewById(R.id.recycler_detail_list_order);

        detailCartView.performClick();

        assertThat(barView.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(orderDetailView.getVisibility()).isEqualTo(View.VISIBLE);

        detailCartView.performClick();

        assertThat(barView.getVisibility()).isEqualTo(View.GONE);
        assertThat(orderDetailView.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void whenPageLoad_thenIsGoneTermLayout() throws Exception {
        LinearLayout pickupTermView = (LinearLayout) orderActivity.findViewById(R.id.linear_order_detail_pickup_term);
        assertThat(pickupTermView.getVisibility()).isEqualTo(View.GONE);
    }


    @Test
    public void whenResetButtonClick_thenChangePickupTime() throws Exception {
        RelativeLayout todayOrderLayout = (RelativeLayout) orderActivity.findViewById(R.id.relative_today_detail_order);

        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();

        resetButton.performClick();

        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        // 33분 + 20분 (준비시간) + 2분 (반올림) + (5분 * 4개 = 20분)
        orderActivity.selectedNumberIndex = 4;

        TextView acceptButton = (TextView) pickerDialog.getView().findViewById(R.id.text_accept_button_picker);
        acceptButton.performClick();

        TextView timeToPickupMin = (TextView) orderActivity.findViewById(R.id.text_dialog_picked_time_order);
        assertThat(timeToPickupMin.getText()).isEqualTo("15:15");
    }

    @Test
    public void whenClickDialogCancelButton_thenKeepPickupInfo() throws Exception {


        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();
        orderTimeTodayRadioButton.setChecked(true);

        int beforeIndex = orderActivity.selectedNumberIndex;

        resetButton.performClick();

        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        assertThat(pickerDialog.isShowing()).isTrue();

        TextView cancelTextView = (TextView) pickerDialog.getView().findViewById(R.id.text_cancel_button_picker);
        cancelTextView.performClick();

        int afterIndex = orderActivity.selectedNumberIndex;

        assertThat(pickerDialog.isShowing()).isFalse();
        assertThat(orderTimeTodayRadioButton.isChecked()).isTrue();
        assertThat(beforeIndex).isEqualTo(afterIndex);
        assertThat(resetButton.getVisibility()).isEqualTo(View.VISIBLE);
    }

    private Store makeMockData() {
        Store store = new Store();
        store.setId("mock 1");
        store.setCategory("mock category");
        store.setName("mock store");
        store.setRegNum("02-1234-1234");
        store.setPaymentType("바로결제");
        store.setAvgPrepTime("20");
        store.setCloseTime("22:00");
        return store;
    }
}