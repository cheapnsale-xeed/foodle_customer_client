package com.xeed.cheapnsale;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.vo.Cart;
import com.xeed.cheapnsale.vo.CartItem;

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

    @Before
    public void setUp() throws Exception {
        Cart mockCart = ((TestApplication) RuntimeEnvironment.application).getCart();

        mockCart.addCartItem(new CartItem("mock-1", "mockItem-1", 22000, 3, "mockSrc-1"));
        mockCart.addCartItem(new CartItem("mock-2", "mockItem-2", 12000, 2, "mockSrc-2"));
        mockCart.addCartItem(new CartItem("mock-3", "mockItem-3", 6000, 1, "mockSrc-3"));
        mockCart.addCartItem(new CartItem("mock-4", "mockItem-4", 8800, 5, "mockSrc-4"));

        orderActivity = Robolectric.buildActivity(OrderActivity.class).create().get();

        orderTimeNowRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_time_now_radio_button);
        orderTimeTodayRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_time_today_radio_button);
        orderPaymentCreditRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_payment_credit);
        orderPaymentKakaopayRadioButton = (RadioButton) orderActivity.findViewById(R.id.order_payment_kakaopay);
        orderPickUpTime = (TextView) orderActivity.findViewById(R.id.order_pick_up_time);

        orderUserName = (EditText) orderActivity.findViewById(R.id.order_user_info_name);
        orderUserTel = (EditText) orderActivity.findViewById(R.id.order_user_info_tel);
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
    public void whenPickerPopupIsOpened_thenFirstPickerValueCheck() throws Exception {

        when(orderActivity.cheapnsaleService.getStore(anyString())).thenReturn(makeMockData());
        orderActivity.onResume();
        orderTimeTodayRadioButton.performClick();
        MaterialDialog pickerDialog = (MaterialDialog) ShadowDialog.getLatestDialog();

        NumberPicker numberPicker = (NumberPicker) pickerDialog.getView().findViewById(R.id.order_time_picker);
        assertThat(numberPicker.getDisplayedValues()[0]).isEqualTo("20 분 후");
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

    @Test
    public void whenOrderUserInfoShow_thenNameIsCorrect() throws Exception {
        assertThat(orderUserName.getText().toString()).isEqualTo("이서진");
        assertThat(orderUserTel.getText().toString().equals("01012345678")).isTrue();
    }

    @Test
    public void whenOrderActivityOpen_thenTotalPriceAndOrderDetailGone() throws Exception {
        TextView totalPrice = (TextView) orderActivity.findViewById(R.id.order_detail_total_price);
        View barView = orderActivity.findViewById(R.id.order_cart_bar);
        RecyclerView orderDetailView = (RecyclerView) orderActivity.findViewById(R.id.order_cart_item_list_recycler_view);

        assertThat(totalPrice.getText().toString()).isEqualTo("140,000");

        assertThat(barView.getVisibility()).isEqualTo(View.GONE);
        assertThat(orderDetailView.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void whenOrderDetailClicked_thenDetailCartShow() throws Exception {
        LinearLayout detailCartView = (LinearLayout) orderActivity.findViewById(R.id.order_detail_title);
        View barView = orderActivity.findViewById(R.id.order_cart_bar);
        RecyclerView orderDetailView = (RecyclerView) orderActivity.findViewById(R.id.order_cart_item_list_recycler_view);

        detailCartView.performClick();

        assertThat(barView.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(orderDetailView.getVisibility()).isEqualTo(View.VISIBLE);

        detailCartView.performClick();

        assertThat(barView.getVisibility()).isEqualTo(View.GONE);
        assertThat(orderDetailView.getVisibility()).isEqualTo(View.GONE);
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